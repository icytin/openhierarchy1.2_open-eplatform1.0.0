package demo.oeplatform.org.queries.CalculationBasisQuery3;

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

public class CalculationBasisCRUD extends IntegerBasedCRUD<CalculationBasis, CalculationBasisQueryProviderModule> {

	protected AnnotatedDAOWrapper<CalculationBasis, Integer> calculationBasisDAO;

	protected static EnumPopulator<FieldLayout> LAYOUT_POPULATOR = new EnumPopulator<FieldLayout>(FieldLayout.class);
	
	public CalculationBasisCRUD(AnnotatedDAOWrapper<CalculationBasis, Integer> calculationBasisDAO, BeanRequestPopulator<CalculationBasis> populator, String typeElementName, String typeLogName, String listMethodAlias, CalculationBasisQueryProviderModule callback) {

		super(calculationBasisDAO, populator, typeElementName, typeLogName, listMethodAlias, callback);

		this.calculationBasisDAO = calculationBasisDAO;
	}

	@Override
	protected ForegroundModuleResponse beanAdded(CalculationBasis bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}

	@Override
	protected ForegroundModuleResponse beanUpdated(CalculationBasis bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}


	@Override
	protected ForegroundModuleResponse beanDeleted(CalculationBasis bean, HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		callback.getEventHandler().sendEvent(QueryDescriptor.class, new CRUDEvent<QueryDescriptor>(CRUDAction.UPDATE, (QueryDescriptor) bean.getQuery().getQueryDescriptor()), EventTarget.ALL);

		callback.redirectToQueryConfig(bean.getQuery(), req, res);

		return null;
	}

	@Override
	public CalculationBasis getBean(Integer beanID) throws SQLException, AccessDeniedException {

		HighLevelQuery<CalculationBasis> query = new HighLevelQuery<CalculationBasis>(CalculationBasis.TEXT_FIELD_QUERY_RELATION);

		query.addParameter(calculationBasisDAO.getParameterFactory().getParameter(beanID));

		CalculationBasis calculationBasis = calculationBasisDAO.getAnnotatedDAO().get(query);

		if(calculationBasis != null && calculationBasis.getQuery() != null) {

			calculationBasis.getQuery().init(callback.getFlowAdminModule().getQueryDescriptor(calculationBasis.getQuery().getQueryID()), null);

		}

		return calculationBasis;

	}

	@Override
	protected void appendAddFormData(Document doc, Element addTypeElement, User user, HttpServletRequest req, URIParser uriParser) throws Exception {

		this.appendFormatValidators(doc, addTypeElement);

	}

	@Override
	protected void appendUpdateFormData(CalculationBasis bean, Document doc, Element updateTypeElement, User user, HttpServletRequest req, URIParser uriParser) throws Exception {

		this.appendFormatValidators(doc, updateTypeElement);

	}

	protected void appendFormatValidators(Document doc, Element element) {

		XMLUtils.append(doc, element, callback.getFormatValidators());

	}

	@Override
	protected CalculationBasis populateFromAddRequest(HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, Exception {

		CalculationBasis calculationBasis = super.populateFromAddRequest(req, user, uriParser);

		calculationBasis.setQuery((CalculationBasisQuery) req.getAttribute("CalculationBasisQuery"));

		calculationBasis.setSortIndex(this.getCurrentMaxSortIndex(calculationBasis.getQuery()) + 1);

		return calculationBasis;
	}

	@Override
	protected void checkUpdateAccess(CalculationBasis bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkUpdateQueryAccess(user, bean.getQuery());

	}

	@Override
	protected void checkDeleteAccess(CalculationBasis bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkUpdateQueryAccess(user, bean.getQuery());

	}

	private int getCurrentMaxSortIndex(CalculationBasisQuery calculationBasisQuery) throws SQLException {

		ObjectQuery<Integer> query = new ObjectQuery<Integer>(calculationBasisDAO.getAnnotatedDAO().getDataSource(), true, "SELECT MAX(sortIndex) FROM " + calculationBasisDAO.getAnnotatedDAO().getTableName() + " WHERE queryID = ?", IntegerPopulator.getPopulator());

		query.setInt(1, calculationBasisQuery.getQueryID());

		Integer sortIndex = query.executeQuery();

		if(sortIndex == null){

			return 0;
		}

		return sortIndex;
	}

}
