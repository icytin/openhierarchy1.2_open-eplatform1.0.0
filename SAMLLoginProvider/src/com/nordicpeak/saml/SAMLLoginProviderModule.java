package com.nordicpeak.saml;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.validation.ValidationException;

import se.unlogic.hierarchy.core.annotations.CheckboxSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.HTMLEditorSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextAreaSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.beans.MutableUser;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.ModuleConfigurationException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.LoginProvider;
import se.unlogic.hierarchy.core.interfaces.MutableSettingHandler;
import se.unlogic.hierarchy.core.utils.ForegroundModuleTracker;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.collections.CollectionUtils;
import se.unlogic.standardutils.io.FileUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.validation.NonNegativeStringIntegerValidator;
import se.unlogic.standardutils.validation.PositiveStringIntegerValidator;
import se.unlogic.webutils.http.ResponseUtils;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.url.URLRewriter;

public class SAMLLoginProviderModule extends AnnotatedForegroundModule implements LoginProvider {

	public static final String SAML_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";

	public static final String HTTP_REDIRECT_BINDING = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
	public static final String HTTP_POST_BINDING = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";

	public static final String[] SUPPORTED_LOGIN_BINDINGS = new String[] { HTTP_REDIRECT_BINDING, "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST" };

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "User session timeout", description = "Session timeout for normal users (in minutes)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected int userSessionTimeout = 60;

	@ModuleSetting
	@CheckboxSettingDescriptor(name = "Add to login handler", description = "Controls if this module should add itself to the login handler as a login provider")
	protected boolean addToLoginHandler = true;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Login provider priority", description = "The priority of the login provider from this module (lower value means higher priority)", required = true, formatValidator = NonNegativeStringIntegerValidator.class)
	protected int priority = 100;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "IDP metadata", description = "Path to the XML file containing the metadata for the identity provider", required = true)
	private String idpMetadataFilePath;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "SP metadata", description = "Path to the XML file containing the metadata for the service provider", required = true)
	private String spMetadataFilePath;

	@ModuleSetting
	@CheckboxSettingDescriptor(name = "Log XML", description = "Enables debug mode which logs the content of SAML requests and responses as INFO logging")
	protected boolean logXML = false;

	@ModuleSetting(allowsNull = true)
	@TextFieldSettingDescriptor(name = "Default redirect alias", description = "The full alias that users should be redirected to after login unless a redirect paramater is present in the URL. If this value is not set and no redirect paramater is present users will be redirected to the root of the context path.", required = false)
	protected String defaultRedirectAlias;

	@ModuleSetting(allowsNull = true)
	@TextAreaSettingDescriptor(name = "Target domains", description = "The domains this module should be enabled for. If no domains are specified then the module is enabled for all domains.")
	protected List<String> targetDomains;

	@ModuleSetting(allowsNull = true)
	@TextFieldSettingDescriptor(name = "SAMLUserAdapter ID", description = "The module ID of the foregroundmodule acting as user adapter for this module", required = true, formatValidator = NonNegativeStringIntegerValidator.class)
	protected Integer userAdapterModuleID;

	@ModuleSetting
	@HTMLEditorSettingDescriptor(name = "Login failed message", description = "The message that is displayed for the users when a login fails.", required = true)
	protected String loginFailedMessage = "<h1>Login failed</h1><p>Login failed, try again or contact the system administrator.</p>";

	@ModuleSetting
	@HTMLEditorSettingDescriptor(name = "Account disabled message", description = "The message that is displayed for the users when a account is disabled.", required = true)
	protected String accountDisabledMessage = "<h1>Account disabled</h1><p>Your account is dsiabled, contact the system administrator for more information.</p>";

	@ModuleSetting
	@CheckboxSettingDescriptor(name="Require session index", description="Controls if a valid session index is required")
	protected boolean requireSessionIndex = true;
	
	protected boolean configured;

	@SuppressWarnings("unused")
	private IdPEntityDescriptor idPEntityDescriptor;

	private SPEntityDescriptor spEntityDescriptor;

	private Endpoint idpEndpoint;

	private RedirectURLEncoder urlEncoder;

	private ForegroundModuleTracker<SAMLUserAdapter> userAdapterTracker;

	@Override
	protected void parseSettings(MutableSettingHandler mutableSettingHandler) throws Exception {

		super.parseSettings(mutableSettingHandler);

		if (userAdapterModuleID == null) {

			log.warn("No SAMLUserAdapter module ID specified, refusing to parse configuration.");
			configured = false;
			return;
		}

		if (idpMetadataFilePath == null || spMetadataFilePath == null) {

			log.warn("No IdP or SP metadata path set, refusing to parse configuration.");
			configured = false;
			return;
		}

		//Bootstrap OpenSAML (odd design approach by OpenSAML)
		DefaultBootstrap.bootstrap();

		//Parse IdP metadata
		IdPEntityDescriptor idPEntityDescriptor = parseIdPMeta(idpMetadataFilePath);

		//Parse SP metadata
		SPEntityDescriptor spEntityDescriptor = parseSPMetaData(spMetadataFilePath);

		if (idPEntityDescriptor != null && spEntityDescriptor != null) {

			Endpoint ssoEndpoint = idPEntityDescriptor.getSSOEndpoint(SUPPORTED_LOGIN_BINDINGS);

			if (ssoEndpoint != null) {

				this.idPEntityDescriptor = idPEntityDescriptor;
				this.spEntityDescriptor = spEntityDescriptor;
				this.idpEndpoint = ssoEndpoint;
				this.urlEncoder = new RedirectURLEncoder();
				this.userAdapterTracker = new ForegroundModuleTracker<SAMLUserAdapter>(SAMLUserAdapter.class, userAdapterModuleID, sectionInterface.getSystemInterface(), sectionInterface.getSystemInterface().getRootSection(), true, true);
				configured = true;

				if (addToLoginHandler) {

					this.sectionInterface.getSystemInterface().getLoginHandler().addProvider(this);

				}else{

					this.sectionInterface.getSystemInterface().getLoginHandler().removeProvider(this);
				}

				return;
			}

			log.error("No supported SSO binding found in IdP metadata");
		}

		configured = false;
		this.idPEntityDescriptor = null;
		this.spEntityDescriptor = null;
		this.idpEndpoint = null;
		this.urlEncoder = null;

		if(this.userAdapterTracker != null){

			userAdapterTracker.shutdown();
		}

		this.sectionInterface.getSystemInterface().getLoginHandler().removeProvider(this);
	}

	@Override
	public void unload() throws Exception {

		this.sectionInterface.getSystemInterface().getLoginHandler().removeProvider(this);

		if(this.userAdapterTracker != null){

			userAdapterTracker.shutdown();
		}

		super.unload();
	}

	protected IdPEntityDescriptor parseIdPMeta(String filePath) {

		File idpFile = new File(filePath);

		if (!FileUtils.isReadable(idpFile)) {

			log.error("IdP file " + idpFile + " is not readable");
			return null;
		}

		XMLObject idpXMLObject;

		try {
			idpXMLObject = SAMLUtils.unmarshallElement(idpFile);

		} catch (Exception e) {

			log.error("Error parsing and unmarshalling IdP file " + idpFile, e);
			return null;
		}

		try {
			return new IdPEntityDescriptor((EntityDescriptor) idpXMLObject, SAML_PROTOCOL);

		} catch (Exception e) {

			log.error("Error parsing certificates in IdP file " + idpFile, e);
			return null;
		}
	}

	private SPEntityDescriptor parseSPMetaData(String filePath) {

		File spFile = new File(filePath);

		if (!FileUtils.isReadable(spFile)) {

			log.error("SP file " + spFile + " is not readable");
			return null;
		}

		XMLObject spXMLObject;

		try {
			spXMLObject = SAMLUtils.unmarshallElement(spFile);

		} catch (Exception e) {

			log.error("Error parsing and unmarshalling SP file " + spFile, e);
			return null;
		}

		return new SPEntityDescriptor((EntityDescriptor) spXMLObject, SAML_PROTOCOL);
	}

	@Override
	public ForegroundModuleResponse processRequest(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Throwable {

		if (!configured) {

			throw new ModuleConfigurationException("Check IdP/SP configuration");
		}

		return super.processRequest(req, res, user, uriParser);
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception, Throwable {

		if (!supportsRequest(req, uriParser)) {

			log.info("Unsupported domain " + req.getServerName() + ", redirecting user " + user + " to context path root.");
			res.sendRedirect(req.getContextPath() + "/");
			return null;
		}

		AuthnRequest authnRequest = SAMLUtils.buildXMLObject(AuthnRequest.class);

		authnRequest.setIssuer(SAMLUtils.createIssuer(spEntityDescriptor.getEntityID()));

		String requestID = UUID.randomUUID().toString();

		authnRequest.setID(requestID);
		authnRequest.setForceAuthn(Boolean.FALSE);
		authnRequest.setIssueInstant(new DateTime(DateTimeZone.UTC));

		//TODO check this value at the same time as the configuration
		authnRequest.setProtocolBinding(spEntityDescriptor.getDefaultAssertionConsumerService().getBinding());
		authnRequest.setDestination(this.idpEndpoint.getLocation());
		authnRequest.setAssertionConsumerServiceURL(spEntityDescriptor.getDefaultAssertionConsumerService().getLocation());

		authnRequest.validate(true);

		//Set this to true to force re-authentication as the IdP even if the user already has a session there
		authnRequest.setForceAuthn(false);

		//Use this to check if the user already has a session at the IdP without prompting
		authnRequest.setIsPassive(false);

		//Use get redirectParam and sent it as a relay state for now, later on this should be saved in the session instead.
		String redirectParam = req.getParameter("redirect");

		if (redirectParam != null && !redirectParam.startsWith("/")) {

			redirectParam = null;
		}

		if (idpEndpoint.getBinding().equals(HTTP_REDIRECT_BINDING)) {

			sendBindingRedirect(req, res, authnRequest, redirectParam);

		} else {

			throw new RuntimeException("HTTP Post bindings are not supported at the moment");
		}

		return null;
	}

	private void sendBindingRedirect(HttpServletRequest req, HttpServletResponse res, AuthnRequest authnRequest, String relayState) throws MessageEncodingException, IOException {

		String redirectURL = urlEncoder.buildRedirectURL(null, relayState, authnRequest, logXML);

		res.sendRedirect(redirectURL);
	}

	@WebPublic(alias = "postconsumer")
	public ForegroundModuleResponse postConsumer(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws UnsupportedEncodingException {

		if (!supportsRequest(req, uriParser)) {

			log.info("Unsupported domain " + req.getServerName() + ", redirecting user " + user + " to context path root.");
			ResponseUtils.sendRedirect(res, req.getContextPath() + "/");
			return null;
		}

		if (!req.getMethod().equalsIgnoreCase("POST")) {

			log.info(req.getMethod() + " request instead of POST request submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		String samlResponse = req.getParameter("SAMLResponse");

		if (samlResponse == null) {

			log.info("No SAML response submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		String xml = new String(Base64.decode(samlResponse), "UTF-8");

		if (logXML && log.isInfoEnabled()) {
			log.info("Decoded SAML response: " + xml);
		}

		XMLObject xmlObject;

		try {
			xmlObject = SAMLUtils.unmarshallElement(xml);
		} catch (Exception e) {

			log.warn("Unable to umarshall response submitted by user " + user + " accessing from " + req.getRemoteAddr(), e);
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (!(xmlObject instanceof Response)) {

			log.warn("Response containing " + xmlObject.getClass() + " instead of " + Response.class + " submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		Response response = (Response) xmlObject;

		Assertion assertion;

		if (!CollectionUtils.isEmpty(response.getEncryptedAssertions())) {

			log.warn("Received response containing unsupported encrypted assertations from user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);

		} else if (!CollectionUtils.isEmpty(response.getAssertions())) {

			assertion = response.getAssertions().get(0);

		} else {

			log.warn("No assertions found in response submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		try {
			assertion.validate(false);
		} catch (ValidationException e) {

			log.warn("Unable to validate assertion submitted by user " + user + " accessing from " + req.getRemoteAddr(), e);
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (!SAMLVersion.VERSION_20.equals(assertion.getVersion())) {

			log.warn("Assertion with version " + assertion.getVersion() + " submitted by " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (assertion.getID() == null) {
			log.warn("Assertion without ID submitted by " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		DateTime expiryTimestamp = SAMLUtils.getSubjectExpiry(assertion);

		if (expiryTimestamp == null || !expiryTimestamp.isAfterNow()) {

			log.warn("Assertion with SubjectConfirmationData that expired " + expiryTimestamp + " submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (CollectionUtils.getSize(assertion.getAuthnStatements()) != 1) {

			log.warn("Assertion containing " + CollectionUtils.getSize(assertion.getAuthnStatements()) + " AuthnStatements submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (requireSessionIndex && assertion.getAuthnStatements().get(0).getSessionIndex() == null) {

			log.warn("Assertion without a AuthnStatement@SessionIndex submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (CollectionUtils.getSize(assertion.getAttributeStatements()) != 1) {

			log.warn("Assertion containing " + CollectionUtils.getSize(assertion.getAuthnStatements()) + " AttributeStatements submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (CollectionUtils.getSize(assertion.getAuthzDecisionStatements()) != 0) {

			log.warn("Assertion containing AuthzDecisionStatement submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (!SAMLUtils.checkRecipient(uriParser.getRequestURL(), assertion)) {

			log.warn("Unable to validate recipient in assertion submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		AuthnStatement authnStatement = assertion.getAuthnStatements().get(0);

		if (authnStatement.getSessionNotOnOrAfter() != null && !authnStatement.getSessionNotOnOrAfter().isAfterNow()) {

			log.warn("Assertion with AuthnStatement session that expired " + authnStatement.getSessionNotOnOrAfter() + " submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		SAMLUserAdapter samlUserAdapter = this.userAdapterTracker.getInstance();

		if (samlUserAdapter == null) {

			log.error("Unable to find specified SAMLUserAdapter with foreground module ID " + this.userAdapterModuleID);
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		User loginUser = samlUserAdapter.getUser(assertion);

		if (loginUser == null) {

			log.warn("SAMLUserAdapter returned null for assertion submitted by user " + user + " accessing from " + req.getRemoteAddr());
			return showLoginFailedMessage(req, res, user, uriParser);
		}

		if (!loginUser.isEnabled()) {
			log.warn("Login refused for user " + loginUser + " (account disabled) accessing from address " + req.getRemoteHost());
			return showAccountDisabledMessage(req, res, loginUser, uriParser);
		}

		// Set last login timestamp
		this.setLastLogin(loginUser);

		// Add user to session
		req.getSession(true).setAttribute("user", loginUser);

		// Set session timeout
		req.getSession(true).setMaxInactiveInterval(this.userSessionTimeout * 60);

		log.info("User " + loginUser + " logged in from address " + req.getRemoteHost());

		sendRedirect(req.getParameter("RelayState"), req, res, uriParser, loginUser);

		return null;
	}

	protected void setLastLogin(User user) {

		user.setCurrentLogin(new Timestamp(System.currentTimeMillis()));

		if (user instanceof MutableUser) {

			MutableUser mutableUser = (MutableUser) user;

			//TODO there must be a smarter way of solving this
			Timestamp lastLogin = user.getLastLogin();

			mutableUser.setLastLogin(user.getCurrentLogin());

			try {
				systemInterface.getUserHandler().updateUser(mutableUser, false, false, false);

			} catch (Exception e) {

				log.error("Unable to update last login for user " + user, e);
			}

			mutableUser.setLastLogin(lastLogin);
		}
	}

	private ForegroundModuleResponse showLoginFailedMessage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) {

		return new SimpleForegroundModuleResponse(getContentItemDiv(loginFailedMessage, req), getDefaultBreadcrumb());
	}

	private ForegroundModuleResponse showAccountDisabledMessage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) {

		return new SimpleForegroundModuleResponse(getContentItemDiv(accountDisabledMessage, req), getDefaultBreadcrumb());
	}

	public String getContentItemDiv(String content, HttpServletRequest req){

		return "<div class=\"contentitem\">" + URLRewriter.setAbsoluteLinkUrls(content, req) + "</div>";
	}

	@Override
	public int getPriority() {

		return priority;
	}

	@Override
	public boolean supportsRequest(HttpServletRequest req, URIParser uriParser) {

		if (this.targetDomains == null || targetDomains.contains(req.getServerName())) {

			return true;
		}

		return false;
	}

	@Override
	public void handleRequest(HttpServletRequest req, HttpServletResponse res, URIParser uriParser, boolean redirectBack) throws Throwable {

		if (redirectBack && !StringUtils.isEmpty(uriParser.getFormattedURI())) {

			String redirect = uriParser.getFormattedURI();

			if (!StringUtils.isEmpty(req.getQueryString())) {

				redirect += "?" + req.getQueryString();
			}

			res.sendRedirect(this.getModuleURI(req) + "?redirect=" + URLEncoder.encode(redirect, "ISO-8859-1"));

		} else {

			redirectToDefaultMethod(req, res);
		}
	}

	@Override
	public boolean loginUser(HttpServletRequest req, URIParser uriParser, User user) throws Exception {

		return false;
	}

	public void sendRedirect(String relayState, HttpServletRequest req, HttpServletResponse res, URIParser uriParser, User user) {

		if (relayState != null && relayState.startsWith("/")) {

			ResponseUtils.sendRedirect(res, req.getContextPath() + relayState);

		} else if (defaultRedirectAlias != null) {

			ResponseUtils.sendRedirect(res, req.getContextPath() + defaultRedirectAlias);

		} else {

			if (StringUtils.isEmpty(req.getContextPath())) {

				ResponseUtils.sendRedirect(res, "/");

			} else {

				ResponseUtils.sendRedirect(res, req.getContextPath());
			}
		}
	}


	public List<String> getTargetDomains() {

		return targetDomains;
	}


	public String getSpMetadataFilePath() {

		return spMetadataFilePath;
	}
}
