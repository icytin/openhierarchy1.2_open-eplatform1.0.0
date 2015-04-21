package demo.oeplatform.org.queries.textfieldquery2;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.enums.CRUDAction;
import se.unlogic.hierarchy.core.enums.EventTarget;
import se.unlogic.hierarchy.core.events.CRUDEvent;
import se.unlogic.hierarchy.core.exceptions.AccessDeniedException;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.IntegerBasedCRUD;
import se.unlogic.standardutils.dao.AnnotatedDAOWrapper;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.querys.ObjectQuery;
import se.unlogic.standardutils.populators.EnumPopulator;
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.BeanRequestPopulator;
import se.unlogic.webutils.http.URIParser;

import com.nordicpeak.flowengine.beans.QueryDescriptor;

public class TextField2CRUD extends IntegerBasedCRUD<TextField2, TextField2QueryProviderModule> {

	protected AnnotatedDAOWrapper<TextField2, Integer> textFieldDAO;

	protected static EnumPopulator<FieldLayout> LAYOUT_POPULATOR = new EnumPopulator<FieldLayout>(FieldLayout.class);
	
	public TextField2CRUD(AnnotatedDAOWrapper<TextField2, Integer> textFieldDAO, BeanRequestPopulator<TextField2> populator, String typeElementName, String typeLogName, String listMethodAlias, TextField2QueryProviderModule callback) {

		super(textFieldDAO, populator, typeElementName, typeLogName, listMethodAlias, callback);

		this.textFieldDAO = textFieldDAO;
	}

	@Override
	protected ForegroundModuleResponse beanAdded(TextField2 bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}

	@Override
	protected ForegroundModuleResponse beanUpdated(TextField2 bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}


	@Override
	protected ForegroundModuleResponse beanDeleted(TextField2 bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}

	@Override
	public TextField2 getBean(Integer beanID) throws SQLException, AccessDeniedException {

		HighLevelQuery<TextField2> query = new HighLevelQuery<TextField2>(TextField2.TEXT_FIELD_QUERY_RELATION);

		query.addParameter(textFieldDAO.getParameterFactory().getParameter(beanID));

		TextField2 textField = textFieldDAO.getAnnotatedDAO().get(query);

		if(textField != null && textField.getQuery() != null) {

			textField.getQuery().init(callback.getFlowAdminModule().getQueryDescriptor(textField.getQuery().getQueryID()), null);

		}

		return textField;

	}

	@Override
	protected void appendAddFormData(Document doc, Element addTypeElement, User user, HttpServletRequest req, URIParser uriParser) throws Exception {

		this.appendFormatValidators(doc, addTypeElement);

	}

	@Override
	protected void appendUpdateFormData(TextField2 bean, Document doc, Element updateTypeElement, User user, HttpServletRequest req, URIParser uriParser) throws Exception {

		this.appendFormatValidators(doc, updateTypeElement);

	}

	protected void appendFormatValidators(Document doc, Element element) {

		XMLUtils.append(doc, element, callback.getFormatValidators());

	}

	@Override
	protected TextField2 populateFromAddRequest(HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, Exception {

		TextField2 textField = super.populateFromAddRequest(req, user, uriParser);

		textField.setQuery((TextField2Query) req.getAttribute("TextField2Query"));

		textField.setSortIndex(this.getCurrentMaxSortIndex(textField.getQuery()) + 1);

		return textField;
	}

	@Override
	protected void checkUpdateAccess(TextField2 bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkUpdateQueryAccess(user, bean.getQuery());

	}

	@Override
	protected void checkDeleteAccess(TextField2 bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkUpdateQueryAccess(user, bean.getQuery());

	}

	private int getCurrentMaxSortIndex(TextField2Query textFieldQuery) throws SQLException {

		ObjectQuery<Integer> query = new ObjectQuery<Integer>(textFieldDAO.getAnnotatedDAO().getDataSource(), true, "SELECT MAX(sortIndex) FROM " + textFieldDAO.getAnnotatedDAO().getTableName() + " WHERE queryID = ?", IntegerPopulator.getPopulator());

		query.setInt(1, textFieldQuery.getQueryID());

		Integer sortIndex = query.executeQuery();

		if(sortIndex == null){

			return 0;
		}

		return sortIndex;
	}

}
