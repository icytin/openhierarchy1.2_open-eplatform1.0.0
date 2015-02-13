package com.nordicpeak.saml;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.signature.X509Data;

public class IdPEntityDescriptor {

	private EntityDescriptor entityDescriptor;
	private IDPSSODescriptor idpSSODescriptor;
	private Collection<X509Certificate> certificates = new ArrayList<X509Certificate>();
	private Collection<X509Certificate> validCertificates = new HashSet<X509Certificate>();

	public IdPEntityDescriptor(EntityDescriptor entityDescriptor, String protocol) throws CertificateException {

		this.entityDescriptor = entityDescriptor;
		idpSSODescriptor = entityDescriptor.getIDPSSODescriptor(protocol);

		org.opensaml.xml.signature.X509Certificate certNode = getCertificateNode();

		if(certNode != null){

			X509Certificate cert = SecurityHelper.buildJavaX509Cert(certNode.getValue());
			certificates.add(cert);
			validCertificates.add(cert);
		}
	}

	private org.opensaml.xml.signature.X509Certificate getCertificateNode() {

		if(idpSSODescriptor != null && idpSSODescriptor.getKeyDescriptors().size() > 0){

			KeyDescriptor keyDescriptor = idpSSODescriptor.getKeyDescriptors().get(0);

			if(keyDescriptor.getKeyInfo().getX509Datas().size() > 0){

				X509Data x509Data = keyDescriptor.getKeyInfo().getX509Datas().get(0);

				if(x509Data.getX509Certificates().size() > 0){

					return x509Data.getX509Certificates().get(0);
				}
			}
		}

		return null;

		//throw new IllegalStateException("IdP Metadata does not contain any certificate");
	}

	public String getEntityID() {

		return entityDescriptor.getEntityID();
	}

	public Endpoint getSSOEndpoint(String[] supportedBindings) {

		for (String binding : supportedBindings) {

			for (SingleSignOnService service : idpSSODescriptor.getSingleSignOnServices()) {

				if (service.getBinding().equalsIgnoreCase(binding)) {
					return service;
				}
			}
		}

		return null;
	}
}
