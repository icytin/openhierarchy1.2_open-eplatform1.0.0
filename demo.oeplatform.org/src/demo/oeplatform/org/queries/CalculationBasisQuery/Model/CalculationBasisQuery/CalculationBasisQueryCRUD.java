package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQuery;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.standardutils.dao.AnnotatedDAOWrapper;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.webutils.http.BeanRequestPopulator;
import se.unlogic.webutils.http.URIParser;

import com.nordicpeak.flowengine.beans.QueryDescriptor;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryCRUD;

import demo.oeplatform.org.queries.CalculationBasisQuery.Controllers.CalculationBasisQueryProviderModule;

public class CalculationBasisQueryCRUD extends BaseQueryCRUD<CalculationBasisQuery, CalculationBasisQueryProviderModule> {

	protected AnnotatedDAOWrapper<CalculationBasisQuery, Integer> queryDAO;
	
	public CalculationBasisQueryCRUD(AnnotatedDAOWrapper<CalculationBasisQuery, Integer> queryDAO, BeanRequestPopulator<CalculationBasisQuery> populator, String typeElementName, String typeLogName, String listMethodAlias, CalculationBasisQueryProviderModule callback) {
		
		super(CalculationBasisQuery.class, queryDAO, populator, typeElementName, typeLogName, listMethodAlias, callback);
		
		this.queryDAO = queryDAO;
	}

	@Override
	protected CalculationBasisQuery populateFromUpdateRequest(CalculationBasisQuery bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, Exception {
		
		CalculationBasisQuery query = super.populateFromUpdateRequest(bean, req, user, uriParser);
		
		List<ValidationError> validationErrors = new ArrayList<ValidationError>();
		
		//Insert Server side validation here
		if(!query.isAllowLetter()) {
			validationErrors.add(new ValidationError("NoContactChannelChoosen"));//Parameter will match Validation error selection in view
		}
		
		//Use ?? to populate the bean
		this.populateQueryDescriptor((QueryDescriptor) query.getQueryDescriptor(), req, validationErrors);
		
		if(!validationErrors.isEmpty()) {
			throw new ValidationException(validationErrors);
		}

		return query;
	}

}
