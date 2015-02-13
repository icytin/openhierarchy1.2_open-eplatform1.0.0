package com.nordicpeak.flowengine;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.random.RandomUtils;
import se.unlogic.webutils.http.URIParser;

import com.nordicpeak.flowengine.beans.FlowInstance;
import com.nordicpeak.flowengine.beans.QueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.FlowInstanceAccessController;
import com.nordicpeak.flowengine.interfaces.ImmutableFlowInstance;
import com.nordicpeak.flowengine.managers.FlowInstanceManager;
import com.nordicpeak.flowengine.managers.MutableFlowInstanceManager;


public class FlowInstanceDeleterModule extends BaseFlowModule {

	private int randomNumber = RandomUtils.getRandomInt(10000, 10000000);
	
	@Override
	public synchronized ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Throwable {

		Integer submittedID = NumberUtils.toInt(req.getParameter("code"));
		
		if(submittedID == null || submittedID != randomNumber){
			
			return new SimpleForegroundModuleResponse(getHTML("Flow instance deleter (use with caution!)","Submit the code " + randomNumber + " to delete all flow instances."));
		}
	
		int deletedCount = 0;
		
		log.info("User " + user + " deleting all flow instances...");
		
		TransactionHandler transactionHandler = null;
		
		try{
			transactionHandler = new TransactionHandler(dataSource);
			
			List<FlowInstance> flowInstances = this.daoFactory.getFlowInstanceDAO().getAll((HighLevelQuery<FlowInstance>)null, transactionHandler);

			if(flowInstances != null){
				
				for(FlowInstance flowInstance : flowInstances){
					
					HighLevelQuery<QueryInstanceDescriptor> descriptorQuery = new HighLevelQuery<QueryInstanceDescriptor>(queryInstanceDescriptorFlowInstanceIDParamFactory.getParameter(flowInstance.getFlowInstanceID()));
					
					descriptorQuery.addRelation(QueryInstanceDescriptor.QUERY_DESCRIPTOR_RELATION);
					
					List<QueryInstanceDescriptor> queryInstanceDescriptors = daoFactory.getQueryInstanceDescriptorDAO().getAll(descriptorQuery, transactionHandler);
					
					if(queryInstanceDescriptors != null){
						
						for(QueryInstanceDescriptor queryInstanceDescriptor : queryInstanceDescriptors){
							
							log.info("Deleting " + queryInstanceDescriptor);
							this.queryHandler.deleteQueryInstance(queryInstanceDescriptor, transactionHandler);
						}
					}
					
					log.info("Deteing " + flowInstance);
					daoFactory.getFlowInstanceDAO().delete(flowInstance, transactionHandler);
				}
				
				deletedCount = flowInstances.size();
			}
			
			transactionHandler.commit();
			
		}finally{
			
			randomNumber = RandomUtils.getRandomInt(10000, 10000000);
			
			TransactionHandler.autoClose(transactionHandler);
		}
		
		return new SimpleForegroundModuleResponse(getHTML("Flow instances deleted",deletedCount + " flow instances deleted."));
	}

	private String getHTML(String title, String message) {

		return "<div class=\"contentitem\"><h1>" + title + "</h1><p>" + message + "</p></div>";
	}

	@Override
	protected void redirectToSubmitMethod(MutableFlowInstanceManager instanceManager, HttpServletRequest req, HttpServletResponse res) throws IOException {}

	@Override
	protected void onFlowInstanceClosedRedirect(FlowInstanceManager flowInstanceManager, HttpServletRequest req, HttpServletResponse res) throws IOException {}
	
	@Override
	protected String getBaseUpdateURL(HttpServletRequest req, URIParser uriParser, User user, ImmutableFlowInstance flowInstance, FlowInstanceAccessController accessController) {

		return null;
	}

	@Override
	public String getSignFailURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return null;
	}

	@Override
	public String getSignSuccessURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return null;
	}

	@Override
	public String getSigningURL(MutableFlowInstanceManager instanceManager, HttpServletRequest req) {

		return null;
	}
}
