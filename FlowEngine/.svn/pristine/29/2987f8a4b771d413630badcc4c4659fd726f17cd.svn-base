package com.nordicpeak.flowengine.queries.basequery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.beans.PDFQueryResponse;
import com.nordicpeak.flowengine.beans.QueryResponse;
import com.nordicpeak.flowengine.interfaces.MutableQueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.QueryHandler;
import com.nordicpeak.flowengine.interfaces.QueryInstance;
import com.nordicpeak.flowengine.interfaces.QueryRequestProcessor;



public abstract class BaseQueryInstance extends GeneratedElementable implements QueryInstance{

	private static final long serialVersionUID = -4177738017399293462L;

	@XMLElement
	protected MutableQueryInstanceDescriptor queryInstanceDescriptor;

	public void set(MutableQueryInstanceDescriptor mutableQueryInstanceDescriptor) {

		this.queryInstanceDescriptor = mutableQueryInstanceDescriptor;
	}

	@Override
	public MutableQueryInstanceDescriptor getQueryInstanceDescriptor() {

		return queryInstanceDescriptor;
	}

	@Override
	public void populate(HttpServletRequest req, User user, boolean allowPartialPopulation, QueryHandler queryHandler) throws ValidationException {

		BaseQueryUtils.getGenericQueryInstanceProvider(this.getClass(), queryHandler, queryInstanceDescriptor.getQueryDescriptor().getQueryTypeID()).populate(this, req, user, allowPartialPopulation);
	}

	@Override
	public void save(TransactionHandler transactionHandler, QueryHandler queryHandler) throws Throwable {

		BaseQueryUtils.getGenericQueryInstanceProvider(this.getClass(), queryHandler, queryInstanceDescriptor.getQueryDescriptor().getQueryTypeID()).save(this, transactionHandler);
	}

	@Override
	public QueryResponse getShowHTML(HttpServletRequest req, User user, QueryHandler queryHandler, String updateURL, String queryRequestURL) throws Throwable {

		return BaseQueryUtils.getGenericQueryInstanceProvider(this.getClass(), queryHandler, queryInstanceDescriptor.getQueryDescriptor().getQueryTypeID()).getShowHTML(this, req, user, updateURL, queryRequestURL);
	}

	@Override
	public QueryResponse getFormHTML(HttpServletRequest req, User user, List<ValidationError> validationErrors, QueryHandler queryHandler, boolean enableAjaxPosting, String queryRequestURL) throws Throwable {

		return BaseQueryUtils.getGenericQueryInstanceProvider(this.getClass(), queryHandler, queryInstanceDescriptor.getQueryDescriptor().getQueryTypeID()).getFormHTML(this, req, user, validationErrors, enableAjaxPosting, queryRequestURL);
	}

	public void close(QueryHandler queryHandler){}

	@Override
	public void reset() {

		if(this.queryInstanceDescriptor != null){
			this.queryInstanceDescriptor.setPopulated(false);
		}
	}

	@Override
	public QueryRequestProcessor getQueryRequestProcessor(HttpServletRequest req, User user, QueryHandler queryHandler) throws Exception {

		return null;
	}

	@Override
	public PDFQueryResponse getPDFContent(QueryHandler queryHandler) throws Throwable {

		return BaseQueryUtils.getGenericQueryInstanceProvider(this.getClass(), queryHandler, queryInstanceDescriptor.getQueryDescriptor().getQueryTypeID()).getPDFContent(this);
	}
}
