package se.unlogic.hierarchy.foregroundmodules.minimaluser;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.UserFormCallback;
import se.unlogic.hierarchy.core.utils.ViewFragmentTransformer;
import se.unlogic.hierarchy.foregroundmodules.userproviders.cruds.UserFormCRUD;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.BeanRequestPopulator;
import se.unlogic.webutils.http.URIParser;


public class MinimalUserFormCRUD extends UserFormCRUD<MinimalUser, MinimalUserProviderModule> {

	public MinimalUserFormCRUD(BeanRequestPopulator<MinimalUser> populator, MinimalUserProviderModule moduleCallback, ViewFragmentTransformer viewTransformer) {

		super(populator, "User", "minimal user", moduleCallback, viewTransformer);
	}

	@Override
	protected void appendFormData(Document doc, Element targetElement, User user, HttpServletRequest req, URIParser uriParser, UserFormCallback formCallback) {

		super.appendFormData(doc, targetElement, user, req, uriParser, formCallback);

		appendFieldModes(doc, targetElement);
	}

	@Override
	protected void appendShowFormData(MinimalUser bean, Document doc, Element showTypeElement, User user, HttpServletRequest req, URIParser uriParser, UserFormCallback formCallback) throws SQLException, IOException, Exception {

		super.appendShowFormData(bean, doc, showTypeElement, user, req, uriParser, formCallback);

		appendFieldModes(doc, showTypeElement);
	}

	protected void appendFieldModes(Document doc, Element targetElement) {

		XMLUtils.appendNewElement(doc, targetElement, "UsernameFieldMode", moduleCallback.getUsernameFieldMode());
		XMLUtils.appendNewElement(doc, targetElement, "PasswordFieldMode", moduleCallback.getPasswordFieldMode());
		XMLUtils.appendNewElement(doc, targetElement, "EmailFieldMode", moduleCallback.getEmailFieldMode());
	}
}
