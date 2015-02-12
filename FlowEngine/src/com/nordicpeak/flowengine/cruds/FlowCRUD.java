package com.nordicpeak.flowengine.cruds;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.Group;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.enums.CRUDAction;
import se.unlogic.hierarchy.core.enums.EventTarget;
import se.unlogic.hierarchy.core.events.CRUDEvent;
import se.unlogic.hierarchy.core.exceptions.AccessDeniedException;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.AccessUtils;
import se.unlogic.hierarchy.core.utils.AdvancedIntegerBasedCRUD;
import se.unlogic.standardutils.dao.CRUDDAO;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.serialization.SerializationUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationErrorType;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;

import com.nordicpeak.flowengine.FlowAdminModule;
import com.nordicpeak.flowengine.beans.Category;
import com.nordicpeak.flowengine.beans.DefaultStandardStatusMapping;
import com.nordicpeak.flowengine.beans.DefaultStatusMapping;
import com.nordicpeak.flowengine.beans.EvaluatorDescriptor;
import com.nordicpeak.flowengine.beans.Flow;
import com.nordicpeak.flowengine.beans.FlowAction;
import com.nordicpeak.flowengine.beans.FlowFamily;
import com.nordicpeak.flowengine.beans.FlowType;
import com.nordicpeak.flowengine.beans.QueryDescriptor;
import com.nordicpeak.flowengine.beans.StandardStatus;
import com.nordicpeak.flowengine.beans.Status;
import com.nordicpeak.flowengine.beans.Step;

public class FlowCRUD extends AdvancedIntegerBasedCRUD<Flow, FlowAdminModule> {

	public FlowCRUD(CRUDDAO<Flow, Integer> crudDAO, FlowAdminModule callback) {

		super(Flow.class, crudDAO, new AnnotatedRequestPopulator<Flow>(Flow.class), "Flow", "flow", "", callback);

	}

	@Override
	public Flow getBean(Integer beanID, String getMode) throws SQLException, AccessDeniedException {

		if (getMode != null && (getMode == FlowCRUD.SHOW || getMode == FlowCRUD.DELETE)) {

			return callback.getCachedFlow(beanID);

		} else {

			Flow flow = callback.getCachedFlow(beanID);

			if (flow == null) {

				return null;
			}

			Blob icon = flow.getIcon();

			flow = SerializationUtils.cloneSerializable(flow);

			flow.setIcon(icon);

			return flow;
		}
	}

	@Override
	protected void appendAddFormData(Document doc, Element addTypeElement, User user, HttpServletRequest req, URIParser uriParser) throws Exception {

		callback.appendUserFlowTypes(doc, addTypeElement, user);
	}

	@Override
	protected ForegroundModuleResponse beanAdded(Flow bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(Flow.class, new CRUDEvent<Flow>(CRUDAction.ADD, bean), EventTarget.ALL);

		callback.redirectToMethod(req, res, "/showflow/" + bean.getFlowID());

		return null;
	}

	@Override
	protected ForegroundModuleResponse beanUpdated(Flow bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(Flow.class, new CRUDEvent<Flow>(CRUDAction.UPDATE, bean), EventTarget.ALL);

		callback.redirectToMethod(req, res, "/showflow/" + bean.getFlowID());

		return null;
	}

	@Override
	protected ForegroundModuleResponse beanDeleted(Flow bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(Flow.class, new CRUDEvent<Flow>(CRUDAction.DELETE, bean), EventTarget.ALL);

		if (req.getAttribute("flowFamilyDeleted") != null) {

			return super.beanDeleted(bean, req, res, user, uriParser);

		} else {

			Flow flow = callback.getLatestFlowVersion(bean.getFlowFamily());

			callback.redirectToMethod(req, res, "/showflow/" + flow.getFlowID());

			return null;
		}
	}

	@Override
	protected void checkAddAccess(User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		if (!callback.hasFlowTypeAccess(user)) {

			throw new AccessDeniedException("Add flow access denied, user does not have access to any flow types.");
		}
	}

	@Override
	protected void checkUpdateAccess(Flow bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		checkAccess(user, bean);
	}

	@Override
	protected void checkDeleteAccess(Flow bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		checkAccess(user, bean);

		if ((bean.isPublished() && bean.isEnabled()) || bean.getFlowInstanceCount() > 0) {

			throw new AccessDeniedException("Delete flow access denied since the requested flow is published or has flow instances connected to it.");
		}
	}

	@Override
	protected void checkShowAccess(Flow bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		checkAccess(user, bean);
	}

	private void checkAccess(User user, Flow bean) throws AccessDeniedException {

		if (!AccessUtils.checkAccess(user, bean.getFlowType())) {

			throw new AccessDeniedException("User does not have access to flow type " + bean.getFlowType());
		}
	}

	@Override
	protected void validateAddPopulation(Flow bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, SQLException, Exception {

		Integer flowtypeID = NumberUtils.toInt(req.getParameter("flowTypeID"));

		FlowType flowType = null;

		if (flowtypeID == null) {

			throw new ValidationException(new ValidationError("flowTypeID", ValidationErrorType.RequiredField));

		} else if ((flowType = callback.getCachedFlowType(flowtypeID)) == null) {

			throw new ValidationException(new ValidationError("SelectedFlowTypeNotFound"));

		} else if (!AccessUtils.checkAccess(user, flowType)) {

			throw new ValidationException(new ValidationError("FlowTypeAccessDenied"));
		}

		bean.setFlowType(flowType);

		validateFlowCategory(bean, req);

		bean.setVersion(1);

		FlowFamily flowFamily = new FlowFamily();
		flowFamily.setVersionCount(1);

		bean.setFlowFamily(flowFamily);

		if(bean.getExternalLink() != null) {

			bean.setUsePreview(false);
			bean.setRequireAuthentication(false);
			bean.setRequireSigning(false);
			bean.setSubmittedMessage(null);

			return;
		}

		if (req.getParameter("addstandardstatuses") != null) {

			List<StandardStatus> standardStatuses = callback.getDAOFactory().getStandardStatusDAO().getAll(new HighLevelQuery<StandardStatus>(StandardStatus.DEFAULT_STANDARD_STATUS_MAPPINGS_RELATION));

			if (standardStatuses != null) {

				List<Status> statuses = new ArrayList<Status>(standardStatuses.size());

				for (StandardStatus standardStatus : standardStatuses) {

					Status status = new Status(standardStatus);

					if (standardStatus.getDefaultStandardStatusMappings() != null) {

						List<DefaultStatusMapping> statusMappings = new ArrayList<DefaultStatusMapping>(standardStatus.getDefaultStandardStatusMappings().size());

						for (DefaultStandardStatusMapping defaultStandardStatusMapping : standardStatus.getDefaultStandardStatusMappings()) {

							DefaultStatusMapping statusMapping = new DefaultStatusMapping();

							statusMapping.setActionID(defaultStandardStatusMapping.getActionID());
							statusMapping.setFlow(bean);

							statusMappings.add(statusMapping);
						}

						status.setDefaultStatusMappings(statusMappings);
					}

					statuses.add(status);
				}

				bean.setStatuses(statuses);
			}

		}
	}

	@Override
	protected void validateUpdatePopulation(Flow bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, SQLException, Exception {

		validateFlowCategory(bean, req);

		if (bean.isInternal() && bean.isEnabled() && bean.isPublished()) {

			List<FlowAction> requiredFlowActions = callback.getFlowActions(false);

			if (requiredFlowActions != null) {

				if (bean.getDefaultFlowStateMappings() != null) {

					for (FlowAction flowAction : requiredFlowActions) {

						boolean hasAction = false;

						for (DefaultStatusMapping mapping : bean.getDefaultFlowStateMappings()) {

							if (flowAction.getActionID().equals(mapping.getActionID())) {

								hasAction = true;

							}

						}

						if (!hasAction) {

							throw new ValidationException(new ValidationError("MissingDefaultStatusMapping"));

						}

					}

				} else {

					throw new ValidationException(new ValidationError("MissingDefaultStatusMapping"));
				}

			}

		}

	}

	private void validateFlowCategory(Flow flow, HttpServletRequest req) throws ValidationException {

		Integer categoryID = NumberUtils.toInt(req.getParameter("categoryID"));

		if (categoryID != null) {

			List<Category> categories = flow.getFlowType().getCategories();

			if (categories != null) {

				Category choosenCategory = null;

				for (Category category : categories) {

					if (category.getCategoryID().equals(categoryID)) {
						choosenCategory = category;
					}

				}

				if (choosenCategory == null) {
					throw new ValidationException(new ValidationError("CategoryNotFound"));
				}

				flow.setCategory(choosenCategory);

			}

		} else {

			flow.setCategory(null);

		}

	}

	@Override
	protected void appendShowFormData(Flow bean, Document doc, Element showTypeElement, User user, HttpServletRequest req, HttpServletResponse res, URIParser uriParser) throws SQLException, IOException, Exception {

		XMLUtils.append(doc, showTypeElement, "FlowVersions", callback.getFlowVersions(bean.getFlowFamily()));

		XMLUtils.append(doc, showTypeElement, "EvaluatorTypes", callback.getEvaluationHandler().getAvailableEvaluatorTypes());

		XMLUtils.append(doc, showTypeElement, "QueryTypes", callback.getQueryHandler().getAvailableQueryTypes());

		if (bean.getFlowFamily().getAllowedGroupIDs() != null) {

			List<Group> groups = callback.getGroupHandler().getGroups(bean.getFlowFamily().getAllowedGroupIDs(), false);

			XMLUtils.append(doc, showTypeElement, "AllowedGroups", groups);
		}

		if (bean.getFlowFamily().getAllowedUserIDs() != null) {

			List<User> users = callback.getUserHandler().getUsers(bean.getFlowFamily().getAllowedUserIDs(), false, false);

			XMLUtils.append(doc, showTypeElement, "AllowedUsers", users);
		}

	}

	@Override
	public ForegroundModuleResponse list(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ValidationError validationError) throws Exception {

		return callback.list(req, res, user, uriParser, validationError);
	}

	@Override
	protected void deleteBean(Flow bean, HttpServletRequest req, User user, URIParser uriParser) throws Exception {

		TransactionHandler transactionHandler = null;

		try {
			transactionHandler = callback.getDAOFactory().getFlowDAO().createTransaction();

			callback.getDAOFactory().getFlowDAO().delete(bean, transactionHandler);

			if (bean.getSteps() != null) {

				for (Step step : bean.getSteps()) {

					if (step.getQueryDescriptors() != null) {

						for (QueryDescriptor queryDescriptor : step.getQueryDescriptors()) {

							if (queryDescriptor.getEvaluatorDescriptors() != null) {

								for (EvaluatorDescriptor evaluatorDescriptor : queryDescriptor.getEvaluatorDescriptors()) {

									callback.getEvaluationHandler().deleteEvaluator(evaluatorDescriptor, transactionHandler);
								}
							}

							callback.getQueryHandler().deleteQuery(queryDescriptor, transactionHandler);
						}
					}
				}
			}

			//Check if the flow family has any more flow, else delete the family too
			if (!callback.hasFlows(bean.getFlowFamily(), transactionHandler)) {

				callback.getDAOFactory().getFlowFamilyDAO().delete(bean.getFlowFamily(), transactionHandler);

				req.setAttribute("flowFamilyDeleted", true);
			}

			transactionHandler.commit();

		} finally {

			TransactionHandler.autoClose(transactionHandler);
		}
	}
}
