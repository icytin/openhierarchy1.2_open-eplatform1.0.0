package demo.oeplatform.org.queries.CalculationBasisQuery2b.Controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import se.unlogic.hierarchy.core.annotations.XSLVariable;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.validation.ValidationException;

import com.nordicpeak.flowengine.interfaces.ImmutableQueryDescriptor;
import com.nordicpeak.flowengine.interfaces.ImmutableQueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.InstanceMetadata;
import com.nordicpeak.flowengine.interfaces.MutableQueryDescriptor;
import com.nordicpeak.flowengine.interfaces.MutableQueryInstanceDescriptor;
import com.nordicpeak.flowengine.interfaces.Query;
import com.nordicpeak.flowengine.interfaces.QueryInstance;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryCRUDCallback;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryProviderModule;

import demo.oeplatform.org.queries.CalculationBasisQuery2b.Datalayer.Entities.CalculationBasisQueryInstance.CalculationBasisQueryInstance;

public class CalculationBasisQueryProviderModule extends
		BaseQueryProviderModule<CalculationBasisQueryInstance> implements
		BaseQueryCRUDCallback {

	@XSLVariable(prefix = "java.")
	protected String fieldLayoutNewLine = "This variable should be set by your stylesheet";

	@XSLVariable(prefix = "java.")
	protected String fieldLayoutFloat = "This variable should be set by your stylesheet";
	@Override
	public String getTitlePrefix() {
		return this.moduleDescriptor.getName();
	}

	@Override
	public Query createQuery(MutableQueryDescriptor descriptor,
			TransactionHandler transactionHandler) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query getQuery(MutableQueryDescriptor descriptor) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query getQuery(MutableQueryDescriptor descriptor,
			TransactionHandler transactionHandler) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryInstance getQueryInstance(
			MutableQueryInstanceDescriptor descriptor,
			String instanceManagerID, HttpServletRequest req, User user,
			InstanceMetadata instanceMetadata) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteQuery(ImmutableQueryDescriptor descriptor,
			TransactionHandler transactionHandler) throws Throwable {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteQueryInstance(
			ImmutableQueryInstanceDescriptor descriptor,
			TransactionHandler transactionHandler) throws Throwable {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void copyQuery(MutableQueryDescriptor sourceQueryDescriptor,
			MutableQueryDescriptor copyQueryDescriptor,
			TransactionHandler transactionHandler) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(CalculationBasisQueryInstance queryInstance,
			TransactionHandler transactionHandler) throws Throwable {
		// TODO Auto-generated method stub

	}

	@Override
	public void populate(CalculationBasisQueryInstance queryInstance,
			HttpServletRequest req, User user, boolean allowPartialPopulation)
			throws ValidationException {
		// TODO Auto-generated method stub

	}
}