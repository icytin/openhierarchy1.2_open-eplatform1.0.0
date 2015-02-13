package com.nordicpeak.saml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import se.unlogic.standardutils.xml.XMLUtils;

public class SAMLUtils {

	public static XMLObject unmarshallElement(File file) throws SAXException, IOException, ParserConfigurationException, UnmarshallingException {

		Element samlElement = XMLUtils.parseXMLFile(file, false, true).getDocumentElement();

		Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);

		if (unmarshaller == null) {

			throw new RuntimeException("Unable to find unmarshaller for element " + samlElement.getNamespaceURI() + ":" + samlElement.getLocalName() + " in file " + file);
		}

		return unmarshaller.unmarshall(samlElement);
	}

	public static XMLObject unmarshallElement(String string) throws SAXException, IOException, ParserConfigurationException, UnmarshallingException {

		Element samlElement = XMLUtils.parseXML(string, "UTF-8", false, true).getDocumentElement();

		Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);

		if (unmarshaller == null) {

			throw new RuntimeException("Unable to find unmarshaller for element " + samlElement.getNamespaceURI() + ":" + samlElement.getLocalName() + " in XML " + string);
		}

		return unmarshaller.unmarshall(samlElement);
	}

	@SuppressWarnings("unchecked")
	public static <T extends XMLObject> T buildXMLObject(Class<T> type) {

		QName qname = getElementQName(type);
		XMLObjectBuilder<T> builder = Configuration.getBuilderFactory().getBuilder(qname);

		if (builder == null) {
			throw new RuntimeException("No builder found for object " + qname.getLocalPart());
		}

		return builder.buildObject(qname.getNamespaceURI(), qname.getLocalPart(), qname.getPrefix());
	}

	private static <T> QName getElementQName(Class<T> type) {

		try {
			Field typeField;

			try {
				typeField = type.getDeclaredField("DEFAULT_ELEMENT_NAME");
			} catch (NoSuchFieldException ex) {
				typeField = type.getDeclaredField("ELEMENT_NAME");
			}

			QName objectQName = (QName) typeField.get(null);

			return objectQName;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Issuer createIssuer(String value) {

		Issuer issuer = buildXMLObject(Issuer.class);
		issuer.setValue(value);

		return issuer;
	}

	public static DateTime getSubjectExpiry(Assertion assertion) {

		if (assertion.getSubject() == null) {

			return null;
		}

		if (assertion.getSubject().getSubjectConfirmations() == null || assertion.getSubject().getSubjectConfirmations().isEmpty()) {

			return null;
		}

		for (SubjectConfirmation subjectConfirmation : assertion.getSubject().getSubjectConfirmations()) {

			SubjectConfirmationData confirmationData = subjectConfirmation.getSubjectConfirmationData();

			if (confirmationData != null && confirmationData.getNotOnOrAfter() != null) {

				return confirmationData.getNotOnOrAfter();
			}
		}

		return null;
	}

	public static boolean checkRecipient(String assertionConsumerURL, Assertion assertion) {
		
		if (assertion.getSubject() == null || assertion.getSubject().getSubjectConfirmations() == null) {

			return false;
		}
		
		for (SubjectConfirmation subjectConfirmation : assertion.getSubject().getSubjectConfirmations()) {
			
			if (!"urn:oasis:names:tc:SAML:2.0:cm:bearer".equals(subjectConfirmation.getMethod())) {
			
				continue;
			}

			SubjectConfirmationData subjectConfirmationData = subjectConfirmation.getSubjectConfirmationData();
			
			if (subjectConfirmationData == null) {
				
				continue;
			}

			if (assertionConsumerURL.equals(subjectConfirmationData.getRecipient())) {
				
				return true;
			}
		}
		
		return false;
	}
}
