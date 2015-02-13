package com.nordicpeak.flowengine.interfaces;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.ModuleConfigurationException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.webutils.http.URIParser;

import com.nordicpeak.flowengine.managers.MutableFlowInstanceManager;


public interface FlowProcessCallback {

	ForegroundModuleResponse list(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ValidationError validationError) throws ModuleConfigurationException, SQLException;

	String getSubmitActionID();

	String getSaveActionID();

	public void appendFormData(Document doc, Element baseElement, MutableFlowInstanceManager instanceManager, User user);

	//TODO add methods for breadcrumbs
}
