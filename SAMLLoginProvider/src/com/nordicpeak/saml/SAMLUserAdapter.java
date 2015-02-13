package com.nordicpeak.saml;

import org.opensaml.saml2.core.Assertion;

import se.unlogic.hierarchy.core.beans.User;


public interface SAMLUserAdapter {

	public User getUser(Assertion assertion);
}
