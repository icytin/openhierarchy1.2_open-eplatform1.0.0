package com.nordicpeak.saml;

import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;


public class SPEntityDescriptor {

	private EntityDescriptor entityDescriptor;
	private SPSSODescriptor spSSODescriptor;

	public SPEntityDescriptor(EntityDescriptor entityDescriptor, String protocol) {

		this.entityDescriptor = entityDescriptor;
		this.spSSODescriptor = entityDescriptor.getSPSSODescriptor(protocol);
	}

	public String getEntityID() {

		return entityDescriptor.getEntityID();
	}

	public AssertionConsumerService getDefaultAssertionConsumerService() {

		AssertionConsumerService service = spSSODescriptor.getDefaultAssertionConsumerService();

		if (service != null){
			return service;
		}

		if (spSSODescriptor.getAssertionConsumerServices().isEmpty()){
			throw new RuntimeException("No AssertionConsumerServices found in SP metadata");
		}

		return spSSODescriptor.getAssertionConsumerServices().get(0);
	}
}
