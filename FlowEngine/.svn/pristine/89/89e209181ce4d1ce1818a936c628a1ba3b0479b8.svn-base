package com.nordicpeak.flowengine.beans;

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

import com.nordicpeak.flowengine.UserFlowInstanceModule;
import com.nordicpeak.flowengine.interfaces.FlowProcessCallback;
import com.nordicpeak.flowengine.managers.MutableFlowInstanceManager;

public class UserFlowInstanceBrowserProcessCallback implements FlowProcessCallback {

	private UserFlowInstanceModule userFlowInstanceModule;

	private String saveActionID;

	private String submitActionID;

	public UserFlowInstanceBrowserProcessCallback(String saveActionID, String submitActionID, UserFlowInstanceModule userFlowInstanceModule) {

		this.saveActionID = saveActionID;
		this.submitActionID = submitActionID;
		this.userFlowInstanceModule = userFlowInstanceModule;
	}

	public ForegroundModuleResponse list(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ValidationError flowInstanceManagerClosedValidationError) throws ModuleConfigurationException, SQLException {

		return userFlowInstanceModule.list(req, res, user, uriParser, flowInstanceManagerClosedValidationError);
	}

	public String getSubmitActionID() {

		return submitActionID;
	}

	public String getSaveActionID() {

		return saveActionID;
	}

	@Override
	public void appendFormData(Document doc, Element baseElement, MutableFlowInstanceManager instanceManager, User user) {}

}
