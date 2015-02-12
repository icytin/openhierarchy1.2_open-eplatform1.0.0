package se.unlogic.hierarchy.foregroundmodules.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.LoginProvider;
import se.unlogic.hierarchy.foregroundmodules.SimpleForegroundModule;
import se.unlogic.webutils.http.URIParser;


public class LoginTriggerModule extends SimpleForegroundModule {

	@Override
	public ForegroundModuleResponse processRequest(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception, Throwable {

		LoginProvider loginProvider = systemInterface.getLoginHandler().getSupportedLoginProvider(req, uriParser);

		if(loginProvider != null){

			log.info("Triggering login for user " + user + " using login provider " + loginProvider);
			loginProvider.handleRequest(req, res, uriParser, false);
			return null;
		}

		log.info("No suitable login provider found for user " + user + " requesting from " + req.getRemoteAddr());
		throw new URINotFoundException(uriParser);
	}
}
