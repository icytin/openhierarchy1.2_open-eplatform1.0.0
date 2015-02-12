package com.nordicpeak.flowengine;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.openhierarchy.foregroundmodules.siteprofile.interfaces.SiteProfile;

import com.nordicpeak.flowengine.beans.FlowInstanceEvent;
import com.nordicpeak.flowengine.enums.EventType;
import com.nordicpeak.flowengine.exceptions.flow.FlowDefaultStatusNotFound;
import com.nordicpeak.flowengine.exceptions.flowinstancemanager.FlowInstanceManagerClosedException;
import com.nordicpeak.flowengine.exceptions.queryinstance.UnableToSaveQueryInstanceException;
import com.nordicpeak.flowengine.interfaces.SigningCallback;
import com.nordicpeak.flowengine.managers.MutableFlowInstanceManager;


public class BaseFlowModuleSigningCallback implements SigningCallback {

	private final BaseFlowModule baseFlowModule;
	private final String actionID;
	private final EventType eventType;
	private final SiteProfile siteProfile;
	
	public BaseFlowModuleSigningCallback(BaseFlowModule baseFlowModule, String actionID, EventType eventType, SiteProfile siteProfile) {

		super();
		this.baseFlowModule = baseFlowModule;
		this.actionID = actionID;
		this.eventType = eventType;
		this.siteProfile = siteProfile;
	}

	@Override
	public FlowInstanceEvent signingConfirmed(MutableFlowInstanceManager instanceManager, User user) throws FlowInstanceManagerClosedException, UnableToSaveQueryInstanceException, SQLException, FlowDefaultStatusNotFound {

		return baseFlowModule.save(instanceManager, user, null, actionID, eventType);
	}

	@Override
	public void signingComplete(MutableFlowInstanceManager instanceManager, FlowInstanceEvent event, HttpServletRequest req) {

		baseFlowModule.signingComplete(instanceManager, event, req, siteProfile, actionID);
	}
	
	@Override
	public void abortSigning(MutableFlowInstanceManager instanceManager) {

		baseFlowModule.abortSigning(instanceManager);
	}

	@Override
	public String getSignFailURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return baseFlowModule.getSignFailURL(instanceManager, req);
	}

	@Override
	public String getSignSuccessURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return baseFlowModule.getSignSuccessURL(instanceManager, req);
	}

	@Override
	public String getSigningURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return baseFlowModule.getSigningURL(instanceManager, req);
	}

	public String getActionID() {
		
		return actionID;
	}

	
	public EventType getEventType() {
	
		return eventType;
	}

	
	public SiteProfile getSiteProfile() {
	
		return siteProfile;
	}	
}
