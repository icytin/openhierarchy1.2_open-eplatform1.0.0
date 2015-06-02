package demo.oeplatform.org.queries.CalculationBasisQuery.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.emailutils.framework.EmailUtils;
import se.unlogic.emailutils.populators.EmailPopulator;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.beans.MutableUser;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.UnableToUpdateUserException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.MutableAttributeHandler;
import se.unlogic.hierarchy.core.utils.FCKUtils;
import se.unlogic.hierarchy.core.validationerrors.TooLongContentValidationError;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryParameterFactory;
import se.unlogic.standardutils.dao.SimpleAnnotatedDAOFactory;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.db.tableversionhandler.TableVersionHandler;
import se.unlogic.standardutils.db.tableversionhandler.UpgradeResult;
import se.unlogic.standardutils.db.tableversionhandler.XMLDBScriptProvider;
import se.unlogic.standardutils.json.JsonObject;
import se.unlogic.standardutils.populators.StringPopulator;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationErrorType;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLGenerator;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.HTTPUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;
import se.unlogic.webutils.url.URLRewriter;
import se.unlogic.webutils.validation.ValidationUtils;
import com.nordicpeak.flowengine.enums.QueryState;
import com.nordicpeak.flowengine.interfaces.ImmutableQueryDescriptor;
import com.nordicpeak.flowengine.interfaces.ImmutableQueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.InstanceMetadata;
import com.nordicpeak.flowengine.interfaces.MutableQueryDescriptor;
import com.nordicpeak.flowengine.interfaces.MutableQueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.Query;
import com.nordicpeak.flowengine.interfaces.QueryInstance;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryCRUDCallback;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryProviderModule;
import com.nordicpeak.flowengine.utils.JTidyUtils;
import com.nordicpeak.flowengine.utils.TextTagReplacer;

import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQuery.CalculationBasisQuery;
import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQuery.CalculationBasisQueryCRUD;
import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQueryInstance.CalculationBasisQueryInstance;
import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter.CalculationBasisParameter;
import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter.CalculationBasisParameterDAO;

public class CalculationBasisQueryProviderModule extends BaseQueryProviderModule<CalculationBasisQueryInstance> implements BaseQueryCRUDCallback {

	private static final EmailPopulator EMAIL_POPULATOR = new EmailPopulator();
	private static final String SCRIPT_PATH = "../Model/DB script.xml";

	private AnnotatedDAO<CalculationBasisQuery> queryDAO;
	private AnnotatedDAO<CalculationBasisQueryInstance> queryInstanceDAO;

	private CalculationBasisQueryCRUD queryCRUD;

	private QueryParameterFactory<CalculationBasisQuery, Integer> queryIDParamFactory;
	private QueryParameterFactory<CalculationBasisQueryInstance, Integer> queryInstanceIDParamFactory;

	
	private CalculationBasisParameterDAO calculationBasisParameterDAO;
	
	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		// Automatic table version handling
		UpgradeResult upgradeResult = TableVersionHandler.upgradeDBTables(dataSource, CalculationBasisQueryProviderModule.class.getName(), new XMLDBScriptProvider(this.getClass().getResourceAsStream(SCRIPT_PATH)));

		if (upgradeResult.isUpgrade()) {

			log.info(upgradeResult.toString());
		}

		SimpleAnnotatedDAOFactory daoFactory = new SimpleAnnotatedDAOFactory(dataSource);

		queryDAO = daoFactory.getDAO(CalculationBasisQuery.class);
		queryInstanceDAO = daoFactory.getDAO(CalculationBasisQueryInstance.class);

		queryCRUD = new CalculationBasisQueryCRUD(queryDAO.getWrapper(Integer.class), new AnnotatedRequestPopulator<CalculationBasisQuery>(CalculationBasisQuery.class), "CalculationBasisQuery", "query", null, this);

		queryIDParamFactory = queryDAO.getParamFactory("queryID", Integer.class);
		queryInstanceIDParamFactory = queryInstanceDAO.getParamFactory("queryInstanceID", Integer.class);
		
		this.calculationBasisParameterDAO = new CalculationBasisParameterDAO(dataSource);
	}

	@Override
	public void populate(CalculationBasisQueryInstance queryInstance, HttpServletRequest req, User user, boolean allowPartialPopulation) throws ValidationException {

		StringPopulator stringPopulator = StringPopulator.getPopulator();

		List<ValidationError> errors = new ArrayList<ValidationError>();

		Integer queryID = queryInstance.getQuery().getQueryID();

		boolean contactByLetter = req.getParameter("q" + queryID + "_contactByLetter") != null;
		boolean contactBySMS = req.getParameter("q" + queryID + "_contactBySMS") != null;
		boolean contactByEmail = req.getParameter("q" + queryID + "_contactByEmail") != null;
		boolean contactByPhone = req.getParameter("q" + queryID + "_contactByPhone") != null;
		boolean persistUserProfile = req.getParameter("q" + queryID + "_persistUserProfile") != null;

		String firstname;
		String lastname;

		if (user != null) {
			firstname = user.getFirstname();
			lastname = user.getLastname();
		} else {
			firstname = ValidationUtils.validateParameter("q" + queryID + "_firstname", req, true, stringPopulator, errors);
			lastname = ValidationUtils.validateParameter("q" + queryID + "_lastname", req, true, stringPopulator, errors);
		}

		String address = ValidationUtils.validateParameter("q" + queryID + "_address", req, contactByLetter, stringPopulator, errors);
		String zipCode = ValidationUtils.validateParameter("q" + queryID + "_zipcode", req, contactByLetter, stringPopulator, errors);
		String postalAddress = ValidationUtils.validateParameter("q" + queryID + "_postaladdress", req, contactByLetter, stringPopulator, errors);
		String mobilePhone = ValidationUtils.validateParameter("q" + queryID + "_mobilephone", req, contactBySMS, stringPopulator, errors);
		String email = ValidationUtils.validateParameter("q" + queryID + "_email", req, contactByEmail, EMAIL_POPULATOR, errors);
		String phone = ValidationUtils.validateParameter("q" + queryID + "_phone", req, contactByPhone, stringPopulator, errors);

		if (!errors.isEmpty()) {

			if (!allowPartialPopulation && queryInstance.getQueryInstanceDescriptor().getQueryState() == QueryState.VISIBLE_REQUIRED) {

				throw new ValidationException(errors);
			}

			queryInstance.setContactByLetter(contactByLetter);
			queryInstance.setContactBySMS(contactBySMS);
			queryInstance.setContactByEmail(contactByEmail);
			queryInstance.setContactByPhone(contactByPhone);

			queryInstance.setFirstname(firstname);
			queryInstance.setLastname(lastname);
			queryInstance.setAddress(address);
			queryInstance.setZipCode(zipCode);
			queryInstance.setPostalAddress(postalAddress);
			queryInstance.setMobilePhone(mobilePhone);
			queryInstance.setEmail(email);
			queryInstance.setPhone(phone);
			queryInstance.setPersistUserProfile(persistUserProfile);

			queryInstance.getQueryInstanceDescriptor().setPopulated(queryInstance.isPopulated());
			return;

		}

		if (queryInstance.getQueryInstanceDescriptor().getQueryState() == QueryState.VISIBLE_REQUIRED && !contactByLetter && !contactBySMS && !contactByEmail && !contactByPhone) {
			errors.add(new ValidationError("NoContactChannelChoosen"));
		}

		if (StringUtils.isEmpty(address) && (!StringUtils.isEmpty(zipCode) || !StringUtils.isEmpty(postalAddress))) {
			errors.add(new ValidationError("q" + queryID + "_address", ValidationErrorType.RequiredField));
		}

		if (StringUtils.isEmpty(zipCode) && (!StringUtils.isEmpty(address) || !StringUtils.isEmpty(postalAddress))) {
			errors.add(new ValidationError("q" + queryID + "_zipcode", ValidationErrorType.RequiredField));
		}

		if (StringUtils.isEmpty(postalAddress) && (!StringUtils.isEmpty(address) || !StringUtils.isEmpty(zipCode))) {
			errors.add(new ValidationError("q" + queryID + "_postaladdress", ValidationErrorType.RequiredField));
		}

		this.validateFieldLength("q" + queryID + "_firstname", firstname, 255, errors);
		this.validateFieldLength("q" + queryID + "_lastname", lastname, 255, errors);
		this.validateFieldLength("q" + queryID + "_address", address, 255, errors);
		this.validateFieldLength("q" + queryID + "_zipcode", zipCode, 10, errors);
		this.validateFieldLength("q" + queryID + "_postaladdress", postalAddress, 255, errors);
		this.validateFieldLength("q" + queryID + "_mobilephone", mobilePhone, 255, errors);
		this.validateFieldLength("q" + queryID + "_email", email, 255, errors);
		this.validateFieldLength("q" + queryID + "_phone", phone, 255, errors);

		if (!StringUtils.isEmpty(email)) {
			if (!EmailUtils.isValidEmailAddress(email)) {
				errors.add(new ValidationError("q" + queryID + "_email", ValidationErrorType.InvalidFormat));
			}
		}

		if (!errors.isEmpty()) {

			throw new ValidationException(errors);
		}

		queryInstance.setContactByLetter(contactByLetter);
		queryInstance.setContactBySMS(contactBySMS);
		queryInstance.setContactByEmail(contactByEmail);
		queryInstance.setContactByPhone(contactByPhone);

		queryInstance.setFirstname(firstname);
		queryInstance.setLastname(lastname);
		queryInstance.setAddress(address);
		queryInstance.setZipCode(zipCode);
		queryInstance.setPostalAddress(postalAddress);
		queryInstance.setMobilePhone(mobilePhone);
		queryInstance.setEmail(email);
		queryInstance.setPhone(phone);
		queryInstance.setPersistUserProfile(persistUserProfile);
		queryInstance.getQueryInstanceDescriptor().setPopulated(queryInstance.isPopulated());

		if (user != null && user instanceof MutableUser && persistUserProfile) {

			MutableUser mutableUser = (MutableUser) user;

			if (email != null) {

				User emailMatch = systemInterface.getUserHandler().getUserByEmail(email, false, false);

				if (emailMatch != null && !emailMatch.equals(mutableUser)) {

					errors.add(new ValidationError("EmailAlreadyTaken"));

				} else {

					mutableUser.setEmail(email);

				}
			}

			MutableAttributeHandler attributeHandler = mutableUser.getAttributeHandler();

			if (attributeHandler != null) {

				setAttributeValue("address", address, attributeHandler);
				setAttributeValue("zipCode", zipCode, attributeHandler);
				setAttributeValue("postalAddress", postalAddress, attributeHandler);
				setAttributeValue("mobilePhone", mobilePhone, attributeHandler);
				setAttributeValue("phone", phone, attributeHandler);
				setAttributeValue("contactByEmail", contactByEmail, attributeHandler);
				setAttributeValue("contactByLetter", contactByLetter, attributeHandler);
				setAttributeValue("contactByPhone", contactByPhone, attributeHandler);
				setAttributeValue("contactBySMS", contactBySMS, attributeHandler);

			}

			if (!errors.isEmpty()) {

				throw new ValidationException(errors);

			} else {

				try {

					log.info("User " + user + " updating user profile");

					req.getSession(true).setAttribute("user", user);

					this.systemInterface.getUserHandler().updateUser(mutableUser, false, false, attributeHandler != null);

				} catch (UnableToUpdateUserException e) {

					throw new ValidationException(new ValidationError("UnableToUpdateUser"));

				}

			}

		}

	}

	public void save(CalculationBasisQueryInstance queryInstance, TransactionHandler transactionHandler) throws Throwable {

		if (queryInstance.getQueryInstanceID() == null || !queryInstance.getQueryInstanceID().equals(queryInstance.getQueryInstanceDescriptor().getQueryInstanceID())) {

			queryInstance.setQueryInstanceID(queryInstance.getQueryInstanceDescriptor().getQueryInstanceID());

			this.queryInstanceDAO.add(queryInstance, transactionHandler, null);

		} else {

			this.queryInstanceDAO.update(queryInstance, transactionHandler, null);
		}
	}
	//Web methods
	@WebPublic(alias = "config")
	public ForegroundModuleResponse configureQuery(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return this.queryCRUD.update(req, res, user, uriParser);
	}
	
	//Ajax methods
	@WebPublic
	  public ForegroundModuleResponse AddParameter(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws SQLException, IOException
	  {
		//TODO se till att rätt parametrar skickas och ta hand om nullvärden eller "" eftersom inte alla parseint alltis ska köras.
	    String name = req.getParameter("name");
	    int queryId = Integer.parseInt(req.getParameter("queryId"));
	    String refQuery = req.getParameter("refQuery");
	    String[] refQueryIds = refQuery.split(";");
	    int refQueryId = Integer.parseInt(refQueryIds[0]);
	    int refSubQueryId = Integer.parseInt(refQueryIds[1]);
	    String value = req.getParameter("value");
	    String description = req.getParameter("description");

	    CalculationBasisParameter parameter = new CalculationBasisParameter(name, queryId, refQueryId, refSubQueryId, value, description);
	    this.calculationBasisParameterDAO.add(parameter);
	    
	    JsonObject jsonObject = new JsonObject();
	    jsonObject.putField("success", "1");
	    StringBuilder stringBuilder = new StringBuilder();
	    jsonObject.toJson(stringBuilder);

		HTTPUtils.sendReponse(stringBuilder.toString(), "application/json", res);

		return null;
	}
	
	@WebPublic
	  public ForegroundModuleResponse DeleteParameter(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws SQLException, IOException
	  {
		
	    String indata = uriParser.get(0);//Get uriparameter use with get requests
	    String id = req.getParameter("id");
	    
	    // TODO: Remove

	    JsonObject jsonObject = new JsonObject();
	    jsonObject.putField("returnData", "test");
	    
	    
	    StringBuilder stringBuilder = new StringBuilder();
	    
	    jsonObject.toJson(stringBuilder);

		HTTPUtils.sendReponse(stringBuilder.toString(), "application/json", res);

		return null;
	}
	
	@WebPublic
	  public ForegroundModuleResponse GetRefQueries(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws SQLException, IOException
	  {
		//Get current calculationbasisquery id
		String queryId = req.getParameter("queryId");
		
		//Get current step
		//Get current step sortIndex
		
		//Get current flow
		
		//Get all steps with current flow id and sortIndex <= current step sortIndex
		
		//Get all querydescriptors for all steps (for now only with queryTypeId = textfieldquery)
		
		//Get distinct all queries with queryId from querydescriptors
		
		//If single value query extract {name:query.querydescriptor.name,value:queryId}
		//If multi value query get subqueries connected to that query (ex text_fields where queryId=...)
		//Foreach subquery extract {name:subquery.label,value:queryId;subQueryId}
		
		
		//Fetch all textfields with textfieldqueries connected to 
		
		//Hardcoded example
	    JsonObject list = new JsonObject();
	    list.putField("Nettoinkomst", "2150;1616");
	    list.putField("Bostadstillägg/bidrag", "2150;1617");
	    list.putField("Övrig inkomst", "2150;1618");
	    list.putField("Välj äldreboende", "2149");

	    
	    
	    StringBuilder stringBuilder = new StringBuilder();
	    list.toJson(stringBuilder);

		HTTPUtils.sendReponse(stringBuilder.toString(), "application/json", res);

		return null;
	}
	//Default code
	@Override
	public Query createQuery(MutableQueryDescriptor descriptor, TransactionHandler transactionHandler) throws SQLException {

		CalculationBasisQuery query = new CalculationBasisQuery();

		query.setQueryID(descriptor.getQueryID());

		this.queryDAO.add(query, transactionHandler, null);

		query.init(descriptor, getFullAlias() + "/config/" + descriptor.getQueryID());

		return query;
	}

	@Override
	public Query importQuery(MutableQueryDescriptor descriptor, TransactionHandler transactionHandler) throws Throwable {

		CalculationBasisQuery query = new CalculationBasisQuery();
		
		query.setQueryID(descriptor.getQueryID());
		
		query.populate(descriptor.getImportParser().getNode(XMLGenerator.getElementName(query.getClass())));
		
		this.queryDAO.add(query, transactionHandler, null);
		
		return query;
	}
	
	@Override
	public Query getQuery(MutableQueryDescriptor descriptor) throws SQLException {

		CalculationBasisQuery query = this.getQuery(descriptor.getQueryID());

		if (query == null) {

			return null;
		}

		query.init(descriptor, getFullAlias() + "/config/" + descriptor.getQueryID());

		return query;
	}

	@Override
	public Query getQuery(MutableQueryDescriptor descriptor, TransactionHandler transactionHandler) throws Throwable {

		CalculationBasisQuery query = this.getQuery(descriptor.getQueryID(), transactionHandler);

		if (query == null) {

			return null;
		}

		query.init(descriptor, getFullAlias() + "/config/" + descriptor.getQueryID());

		return query;
	}

	@Override
	public QueryInstance getQueryInstance(MutableQueryInstanceDescriptor descriptor, String instanceManagerID, HttpServletRequest req, User user, InstanceMetadata instanceMetadata) throws SQLException {

		CalculationBasisQueryInstance queryInstance;

		// Check if we should create a new instance or get an existing one
		if (descriptor.getQueryInstanceID() == null) {

			queryInstance = new CalculationBasisQueryInstance();

			if (user != null) {

				queryInstance.initialize(user);
			}

		} else {

			queryInstance = getQueryInstance(descriptor.getQueryInstanceID());

			if (queryInstance == null) {

				return null;
			}
		}

		queryInstance.setQuery(getQuery(descriptor.getQueryDescriptor().getQueryID()));

		if (queryInstance.getQuery() == null) {

			return null;
		}

		FCKUtils.setAbsoluteFileUrls(queryInstance.getQuery(), RequestUtils.getFullContextPathURL(req) + ckConnectorModuleAlias);
		
		URLRewriter.setAbsoluteLinkUrls(queryInstance.getQuery(), req, true);

		TextTagReplacer.replaceTextTags(queryInstance.getQuery(), instanceMetadata.getSiteProfile());

		queryInstance.set(descriptor);

		return queryInstance;
	}

	private CalculationBasisQuery getQuery(Integer queryID) throws SQLException {

		HighLevelQuery<CalculationBasisQuery> query = new HighLevelQuery<CalculationBasisQuery>();

		query.addParameter(queryIDParamFactory.getParameter(queryID));

		return queryDAO.get(query);
	}

	private CalculationBasisQuery getQuery(Integer queryID, TransactionHandler transactionHandler) throws SQLException {

		HighLevelQuery<CalculationBasisQuery> query = new HighLevelQuery<CalculationBasisQuery>();

		query.addParameter(queryIDParamFactory.getParameter(queryID));

		return queryDAO.get(query, transactionHandler);
	}

	private CalculationBasisQueryInstance getQueryInstance(Integer queryInstanceID) throws SQLException {

		HighLevelQuery<CalculationBasisQueryInstance> query = new HighLevelQuery<CalculationBasisQueryInstance>(CalculationBasisQueryInstance.QUERY_RELATION);

		query.addParameter(queryInstanceIDParamFactory.getParameter(queryInstanceID));

		return queryInstanceDAO.get(query);
	}

	private void setAttributeValue(String name, Object value, MutableAttributeHandler attributeHandler) {

		if (value != null) {

			attributeHandler.setAttribute(name, value);

		} else {

			attributeHandler.removeAttribute(name);

		}

	}

	private void validateFieldLength(String fieldName, String field, Integer maxLength, List<ValidationError> errors) {

		if (field != null && field.length() > maxLength) {

			errors.add(new TooLongContentValidationError(fieldName, field.length(), maxLength));
		}

	}

	@Override
	public boolean deleteQuery(ImmutableQueryDescriptor descriptor, TransactionHandler transactionHandler) throws SQLException {

		CalculationBasisQuery query = getQuery(descriptor.getQueryID());

		if (query == null) {

			return false;
		}

		this.queryDAO.delete(query, transactionHandler);

		return true;
	}

	@Override
	public boolean deleteQueryInstance(ImmutableQueryInstanceDescriptor descriptor, TransactionHandler transactionHandler) throws Throwable {

		CalculationBasisQueryInstance queryInstance = this.getQueryInstance(descriptor.getQueryInstanceID());

		if (queryInstance == null) {

			return false;
		}

		this.queryInstanceDAO.delete(queryInstance, transactionHandler);

		return true;
	}

	@Override
	public Document createDocument(HttpServletRequest req, User user) {

		Document doc = super.createDocument(req, user);

		if (user != null) {
			doc.getDocumentElement().appendChild(user.toXML(doc));
		}

		return doc;
	}

	@Override
	public String getTitlePrefix() {

		return this.moduleDescriptor.getName();
	}

	@Override
	public void copyQuery(MutableQueryDescriptor sourceQueryDescriptor, MutableQueryDescriptor copyQueryDescriptor, TransactionHandler transactionHandler) throws SQLException {

		CalculationBasisQuery query = getQuery(sourceQueryDescriptor.getQueryID(), transactionHandler);

		query.setQueryID(copyQueryDescriptor.getQueryID());

		queryDAO.add(query, transactionHandler, null);
	}

	@Override
	protected void appendPDFData(Document doc, Element showQueryValuesElement, CalculationBasisQueryInstance queryInstance) {

		super.appendPDFData(doc, showQueryValuesElement, queryInstance);

		if (queryInstance.getQuery().getDescription() != null) {

			XMLUtils.appendNewCDATAElement(doc, showQueryValuesElement, "Description", JTidyUtils.getXHTML(queryInstance.getQuery().getDescription()));
			XMLUtils.appendNewCDATAElement(doc, showQueryValuesElement, "isHTMLDescription", queryInstance.getQuery().getDescription().contains("<") && queryInstance.getQuery().getDescription().contains(">"));
		}
	}
}
