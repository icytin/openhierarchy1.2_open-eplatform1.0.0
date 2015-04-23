package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQuery;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import se.unlogic.hierarchy.core.annotations.FCKContent;
import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.populators.StringPopulator;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLParser;
import se.unlogic.standardutils.xml.XMLValidationUtils;
import se.unlogic.webutils.annotations.URLRewrite;

import com.nordicpeak.flowengine.annotations.TextTagReplace;
import com.nordicpeak.flowengine.queries.basequery.BaseQuery;

import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQueryInstance.CalculationBasisQueryInstance;

@Table(name = "calculation_basis_queries")
@XMLElement
public class CalculationBasisQuery extends BaseQuery {

	private static final long serialVersionUID = -842191226937409416L;

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryID;

	@FCKContent
	@TextTagReplace
	@URLRewrite
	@DAOManaged
	@WebPopulate(maxLength = 65535)
	@XMLElement(cdata=true)
	private String description;

	@FCKContent
	@TextTagReplace
	@URLRewrite
	@DAOManaged
	@WebPopulate(maxLength = 65535)
	@XMLElement
	private String helpText;

	@DAOManaged
	@WebPopulate
	@XMLElement
	private boolean allowLetter;
	
	@DAOManaged
	@OneToMany
	@XMLElement
	private List<CalculationBasisQueryInstance> instances;

	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	@Override
	public Integer getQueryID() {

		return queryID;
	}

	public String getDescription() {

		return description;
	}

	public List<CalculationBasisQueryInstance> getInstances() {

		return instances;
	}

	public void setInstances(List<CalculationBasisQueryInstance> instances) {

		this.instances = instances;
	}

	public void setQueryID(int queryID) {

		this.queryID = queryID;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getHelpText() {

		return helpText;
	}

	public void setHelpText(String helpText) {

		this.helpText = helpText;
	}

	public boolean isAllowLetter() {
		return allowLetter;
	}

	public void setAllowLetter(boolean allowLetter) {
		this.allowLetter = allowLetter;
	}

	@Override
	public String toString() {

		if (this.queryDescriptor != null) {

			return queryDescriptor.getName() + " (queryID: " + queryID + ")";
		}

		return "CalculationBasisQuery (queryID: " + queryID + ")";
	}

	@Override
	public String getXSDTypeName() {

		return "CalculationBasisQuery" + queryID;
	}

	@Override
	public void toXSD(Document doc) {

		
	}

	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		
		//Populate bean from xml here, pass errors as validationexception
		//Possibly add validation on object-level, such as "shoe size is not a valid number", but rather handle this at form validation in CRUD->populateFromUpdateRequest
		//default
		description = XMLValidationUtils.validateParameter("description", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		helpText = XMLValidationUtils.validateParameter("helpText", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		//custom
		allowLetter = xmlParser.getPrimitiveBoolean("allowLetter");
		
		if(!errors.isEmpty())
			throw new ValidationException(errors);
	}
}
