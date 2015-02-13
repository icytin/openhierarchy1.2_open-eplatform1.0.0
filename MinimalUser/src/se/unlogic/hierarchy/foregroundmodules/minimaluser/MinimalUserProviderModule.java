package se.unlogic.hierarchy.foregroundmodules.minimaluser;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import se.unlogic.emailutils.populators.EmailPopulator;
import se.unlogic.hierarchy.core.annotations.EnumDropDownSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.enums.UserField;
import se.unlogic.hierarchy.core.interfaces.UserFormCallback;
import se.unlogic.hierarchy.core.populators.GroupQueryPopulator;
import se.unlogic.hierarchy.core.populators.GroupTypePopulator;
import se.unlogic.hierarchy.core.populators.UserQueryPopulator;
import se.unlogic.hierarchy.core.populators.UserTypePopulator;
import se.unlogic.hierarchy.core.utils.GenericFormCRUD;
import se.unlogic.hierarchy.core.utils.SimpleViewFragmentTransformer;
import se.unlogic.hierarchy.foregroundmodules.userproviders.AnnotatedMutableUserFormProviderModule;
import se.unlogic.standardutils.collections.CollectionUtils;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.SimpleAnnotatedDAOFactory;
import se.unlogic.standardutils.db.tableversionhandler.TableVersionHandler;
import se.unlogic.standardutils.db.tableversionhandler.UpgradeResult;
import se.unlogic.standardutils.db.tableversionhandler.XMLDBScriptProvider;
import se.unlogic.standardutils.enums.Order;
import se.unlogic.standardutils.populators.BeanStringPopulator;
import se.unlogic.standardutils.populators.QueryParameterPopulator;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;
import se.unlogic.webutils.populators.annotated.RequestMapping;


public class MinimalUserProviderModule extends AnnotatedMutableUserFormProviderModule<MinimalUser> {

	@ModuleSetting
	@EnumDropDownSettingDescriptor(name="Username field", description="Controls the behaivor of the username field in add and update user forms.", required=true)
	protected FieldMode usernameFieldMode = FieldMode.DISABLED;

	@ModuleSetting
	@EnumDropDownSettingDescriptor(name="Password field", description="Controls the behaivor of the password field in add and update user forms.", required=true)
	protected FieldMode passwordFieldMode = FieldMode.DISABLED;

	@ModuleSetting
	@EnumDropDownSettingDescriptor(name="Email field", description="Controls the behaivor of the email field in add and update user forms.", required=true)
	protected FieldMode emailFieldMode = FieldMode.DISABLED;

	public MinimalUserProviderModule() {

		super(MinimalUser.class);
	}

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		//New automatic table version handling
		UpgradeResult upgradeResult = TableVersionHandler.upgradeDBTables(dataSource, MinimalUserProviderModule.class.getName(), new XMLDBScriptProvider(MinimalUserProviderModule.class.getResourceAsStream("dbscripts/DB script.xml")));

		if(upgradeResult.isUpgrade()){

			log.info(upgradeResult.toString());
		}

		SimpleAnnotatedDAOFactory daoFactory = new SimpleAnnotatedDAOFactory(dataSource);

		this.userDAO = new AnnotatedMinimalUserDAO(dataSource, userClass, daoFactory, getQueryParameterPopulators(), getBeanStringPopulators(), getUsergroupTablename(), getGroupsRelation(), getUserAttributesTableName(), getAttributesRelation());
		daoFactory.addDAO(userClass, userDAO);
	}

	public boolean isProviderFor(User user) {

		if (userClass.equals(user.getClass()) && ((MinimalUser) user).getProviderID() != null && ((MinimalUser) user).getProviderID().equals(this.moduleDescriptor.getModuleID())) {

			return true;
		}

		return false;
	}

	@Override
	protected String getUsergroupTablename() {

		return "minimal_user_groups";
	}

	@Override
	protected String getUserAttributesTableName() {

		return AnnotatedDAO.getTableName(MinimalUserAttribute.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<BeanStringPopulator<?>> getBeanStringPopulators() {

		return CollectionUtils.getList((BeanStringPopulator<?>) new UserTypePopulator(systemInterface.getUserHandler(), false, false), (BeanStringPopulator<?>) new GroupTypePopulator(systemInterface.getGroupHandler(), true));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<QueryParameterPopulator<?>> getQueryParameterPopulators() {

		return CollectionUtils.getList((QueryParameterPopulator<?>) UserQueryPopulator.POPULATOR, (QueryParameterPopulator<?>) GroupQueryPopulator.POPULATOR);
	}

	@Override
	protected Field getGroupsRelation() {

		return MinimalUser.GROUPS_RELATION;
	}

	@Override
	protected Field getAttributesRelation() {

		return MinimalUser.ATTRIBUTES_RELATION;
	}

	@Override
	public MinimalUser getUserByUsername(String username, boolean groups, boolean attributes) throws SQLException {

		return getUserByAttribute("username", username, groups, attributes);
	}

	@Override
	public MinimalUser getUserByUsernamePassword(String username, String password, boolean groups, boolean attributes) throws SQLException {

		MinimalUser user = getUserByUsername(username, groups, attributes);

		if(user != null && user.getPassword() != null){

			String hashedPassword = getHashedPassword(password);

			if(hashedPassword.equals(user.getPassword())){

				return user;
			}
		}

		return null;
	}

	@Override
	public MinimalUser getUserByEmail(String email, boolean groups, boolean attributes) throws SQLException {

		return getUserByAttribute("email", email, groups, attributes);
	}

	@Override
	public MinimalUser getUserByEmailPassword(String email, String password, boolean groups, boolean attributes) throws SQLException {

		MinimalUser user = getUserByEmail(email, groups, attributes);

		if(user != null && user.getPassword() != null){

			String hashedPassword = getHashedPassword(password);

			if(hashedPassword.equals(user.getPassword())){

				return user;
			}
		}

		return null;
	}

	@Override
	protected MinimalUser setupUser(MinimalUser user, boolean setupAttributes) {

		if (user != null) {

			user.setProviderID(this.moduleDescriptor.getModuleID());
			user.setFormProvider(true);
		}

		return user;
	}

	@Override
	protected List<MinimalUser> setupUsers(List<MinimalUser> users, boolean setupAttributes) {

		if (users != null) {

			for (MinimalUser user : users) {

				user.setProviderID(this.moduleDescriptor.getModuleID());
				user.setFormProvider(true);
			}
		}

		return users;
	}


	@Override
	protected GenericFormCRUD<MinimalUser, User, ?, UserFormCallback> createUserFormCRUD() {

		SimpleViewFragmentTransformer fragmentTransformer;

		try{
			fragmentTransformer = new SimpleViewFragmentTransformer(formStyleSheet, systemInterface.getEncoding(), this.getClass(), moduleDescriptor, sectionInterface);

		}catch(Exception e){

			log.error("Unable to parse XSL stylesheet for user forms in module " + this.moduleDescriptor,e);
			return null;
		}

		fragmentTransformer.setDebugXML(this.includeDebugData);

		AnnotatedRequestPopulator<MinimalUser> populator = new AnnotatedRequestPopulator<MinimalUser>(MinimalUser.class, new EmailPopulator());

		Iterator<RequestMapping> iterator = populator.getRequestMappings().iterator();

		while(iterator.hasNext()){

			RequestMapping mapping = iterator.next();

			String paramName = mapping.getParamName();

			if(paramName.equals("email")){

				setFieldMode(mapping, iterator, emailFieldMode);

			}else if(paramName.equals("username")){

				setFieldMode(mapping, iterator, usernameFieldMode);

			}else if(paramName.equals("password")){

				if(passwordFieldMode == FieldMode.REQUIRED){

					//Workaround to allow updating of users without changing password
					setFieldMode(mapping, iterator, FieldMode.OPTIONAL);

				}else{

					setFieldMode(mapping, iterator, passwordFieldMode);
				}
			}
		}

		return new MinimalUserFormCRUD(populator, this, fragmentTransformer);
	}

	private void setFieldMode(RequestMapping mapping, Iterator<RequestMapping> iterator, FieldMode fieldMode) {

		if(fieldMode == FieldMode.DISABLED){

			iterator.remove();

		}else if(fieldMode == FieldMode.OPTIONAL){

			mapping.setRequired(false);

		}else if(fieldMode == FieldMode.REQUIRED){

			mapping.setRequired(true);

		}else{

			throw new RuntimeException("Unkown enum value " + fieldMode);
		}
	}

	public FieldMode getUsernameFieldMode() {

		return usernameFieldMode;
	}


	public FieldMode getPasswordFieldMode() {

		return passwordFieldMode;
	}


	public FieldMode getEmailFieldMode() {

		return emailFieldMode;
	}

	@Override
	protected String getDefaultFormStyleSheet() {

		return "MinimalUserProviderForm.en.xsl";
	}

	@Override
	public boolean requirePasswordOnAdd() {

		//This module has separate handling of this field

		return false;
	}

	@Override
	public List<Character> getUserFirstLetterIndex(UserField filteringField) throws SQLException {

		if(filteringField == UserField.EMAIL || filteringField == UserField.USERNAME){

			return userDAO.getAttributeFirstLetterIndex(filteringField.toString().toLowerCase());
		}

		return super.getUserFirstLetterIndex(filteringField);
	}

	@Override
	public List<? extends User> getUsers(UserField sortingField, Order order, boolean groups, boolean attributes) throws SQLException {

		if(sortingField == UserField.EMAIL || sortingField == UserField.USERNAME){

			List<MinimalUser> users = this.userDAO.getUsersByAttribute(sortingField.toString().toLowerCase(), groups, attributes);

			if(users != null){

				Collections.sort(users, sortingField.getComparator(order));
			}

			return users;
		}

		return super.getUsers(sortingField, order, groups, attributes);
	}

	@Override
	public List<? extends User> getUsers(UserField filteringField, char startsWith, Order order, boolean groups, boolean attributes) throws SQLException {

		if(filteringField == UserField.EMAIL || filteringField == UserField.USERNAME){

			List<MinimalUser> users = this.userDAO.getUsersByAttributeWildcard(filteringField.toString().toLowerCase(), Character.toString(startsWith), groups, attributes);

			if(users != null){

				Collections.sort(users, filteringField.getComparator(order));
			}

			return users;
		}

		return super.getUsers(filteringField, startsWith, order, groups, attributes);
	}
}
