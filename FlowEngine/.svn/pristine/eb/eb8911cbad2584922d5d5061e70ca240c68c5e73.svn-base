package com.nordicpeak.flowengine.signingproviders;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import se.unlogic.hierarchy.core.annotations.InstanceManagerDependency;
import se.unlogic.hierarchy.core.beans.SimpleViewFragment;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.interfaces.ViewFragment;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.dao.RelationQuery;

import com.nordicpeak.flowengine.beans.FlowInstanceEvent;
import com.nordicpeak.flowengine.dao.FlowEngineDAOFactory;
import com.nordicpeak.flowengine.exceptions.flow.FlowDefaultStatusNotFound;
import com.nordicpeak.flowengine.exceptions.flowinstancemanager.FlowInstanceManagerClosedException;
import com.nordicpeak.flowengine.exceptions.queryinstance.UnableToSaveQueryInstanceException;
import com.nordicpeak.flowengine.interfaces.PDFProvider;
import com.nordicpeak.flowengine.interfaces.SigningCallback;
import com.nordicpeak.flowengine.interfaces.SigningProvider;
import com.nordicpeak.flowengine.managers.MutableFlowInstanceManager;


public class DummySigningProvider extends AnnotatedForegroundModule implements SigningProvider {

	public static final RelationQuery EVENT_ATTRIBUTE_RELATION_QUERY = new RelationQuery(FlowInstanceEvent.ATTRIBUTES_RELATION);

	@InstanceManagerDependency
	private PDFProvider pdfProvider;
	
	private FlowEngineDAOFactory daoFactory;

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		daoFactory = new FlowEngineDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());
	}
	
	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {

		super.init(moduleDescriptor, sectionInterface, dataSource);
		
		if(!systemInterface.getInstanceHandler().addInstance(SigningProvider.class, this)){

			throw new RuntimeException("Unable to register module " + this.moduleDescriptor + " in global instance handler using key " + SigningProvider.class.getSimpleName() + ", another instance is already registered using this key.");
		}		
	}

	@Override
	public void unload() throws Exception {

		if(this.equals(systemInterface.getInstanceHandler().getInstance(SigningProvider.class))){

			systemInterface.getInstanceHandler().removeInstance(SigningProvider.class);
		}		
		
		super.unload();
	}	
	
	@Override
	public ViewFragment sign(HttpServletRequest req, HttpServletResponse res, User user, MutableFlowInstanceManager instanceManager, SigningCallback signingCallback, boolean savedDuringCurrentRequest) throws IOException, FlowInstanceManagerClosedException, UnableToSaveQueryInstanceException, SQLException, FlowDefaultStatusNotFound {

		if(req.getParameter("sign") != null && !savedDuringCurrentRequest){
			
			log.info("User " + user + " signed flow instance " + instanceManager);
			
			FlowInstanceEvent event = signingCallback.signingConfirmed(instanceManager, user);
			
			event.getAttributeHandler().setAttribute("signingProvider", this.getClass().getName());
			
			daoFactory.getFlowInstanceEventDAO().update(event, EVENT_ATTRIBUTE_RELATION_QUERY);
			
			if(pdfProvider != null){
				
				try {
					if(pdfProvider.saveTemporaryPDF(instanceManager.getFlowInstanceID(), event)){
						
						log.info("Temporary PDF for flow instance " + instanceManager + " requested by user " + user + " saved for event " + event);
						
					}else{
						
						log.warn("Unable to find temporary PDF for flow instance " + instanceManager + " submitted by user " + user);
					}
					
				} catch (Exception e) {

					log.error("Error saving temporary PDF for flow instance " + instanceManager + " submitted by user " + user, e);
				}
			}			
			
			signingCallback.signingComplete(instanceManager, event, req);
			
			res.sendRedirect(signingCallback.getSignSuccessURL(instanceManager, req));
			
			return null;			
			
		}else if(req.getParameter("fail") != null){
			
			log.info("Signing of flow instance " + instanceManager + " by user " + user + " failed.");
			
			signingCallback.abortSigning(instanceManager);
			
			if(pdfProvider != null){
				
				try {
					pdfProvider.deleteTemporaryPDF(instanceManager.getFlowInstanceID());
					
				} catch (Exception e) {

					log.error("Error deleteing temporary PDF for flow instance " + instanceManager + " submitted by user " + user, e);
				}
			}				
			
			res.sendRedirect(signingCallback.getSignFailURL(instanceManager, req));
			
			return null;			
		}
		
		if(pdfProvider != null && (savedDuringCurrentRequest || !pdfProvider.hasTemporaryPDF(instanceManager.getFlowInstanceID()))){
			
			try {
				pdfProvider.createTemporaryPDF(instanceManager, true, signingCallback.getSiteProfile(), user);
				
			} catch (Exception e) {

				log.error("Error generating temporary PDF for flow instance " + instanceManager + " submitted by user " + user, e);
			}
		}
		
		log.info("User " + user + " requested sign form for flow instance " + instanceManager);
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<div>");
		stringBuilder.append("<h1>Dummy signer</h1>");
		stringBuilder.append("<p><a href=\"" + signingCallback.getSigningURL(instanceManager, req) + "&sign=1\">Click here to sign flow instance " + instanceManager.getFlowInstance().getFlow().getName() + " #" + instanceManager.getFlowInstanceID() + "</a></p>");
		stringBuilder.append("<p><a href=\"" + signingCallback.getSigningURL(instanceManager, req) + "&fail=1\">Click here to simulate a failed signing</a></p>");
		stringBuilder.append("</div>");
		
		return new SimpleViewFragment(stringBuilder.toString());
	}
}
