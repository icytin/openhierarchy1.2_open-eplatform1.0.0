package demo.oeplatform.org.queries.CalculationBasisQuery3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.annotations.FCKContent;
import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.collections.CollectionUtils;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.populators.StringPopulator;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLParser;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.standardutils.xml.XMLValidationUtils;
import se.unlogic.webutils.annotations.URLRewrite;

import com.nordicpeak.flowengine.annotations.TextTagReplace;
import com.nordicpeak.flowengine.queries.basequery.BaseQuery;

@Table(name = "calculationbasis_queries")
@XMLElement
public class CalculationBasisQuery extends BaseQuery {

	private static final long serialVersionUID = -842191226937409416L;

	public static final Field TEXT_FIELDS_RELATION = ReflectionUtils.getField(CalculationBasisQuery.class, "fields");

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
	@WebPopulate(required=true)
	@XMLElement
	private FieldLayout layout;

	@DAOManaged(dontUpdateIfNull=true)
	@OneToMany(autoUpdate=true, autoAdd=true)
	@XMLElement(fixCase=true)
	private List<CalculationBasis> fields;

	@DAOManaged(dontUpdateIfNull=true)
	@OneToMany(autoUpdate=true)
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

	public List<CalculationBasis> getFields() {

		return fields;
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

	public void setFields(List<CalculationBasis> alternatives) {

		this.fields = alternatives;
	}

	public String getHelpText() {

		return helpText;
	}

	public void setHelpText(String helpText) {

		this.helpText = helpText;
	}

	@Override
	public String toString() {

		if(this.queryDescriptor != null){

			return queryDescriptor.getName() + " (queryID: " + queryID + ")";
		}

		return "CalculationBasisQuery (queryID: " + queryID + ")";
	}

	public FieldLayout getLayout() {

		return layout;
	}

	public void setLayout(FieldLayout layout) {

		this.layout = layout;
	}

	@Override
	public String getXSDTypeName() {

		return "CalculationBasisQuery" + queryID;
	}

	@Override
	public void toXSD(Document doc) {

		if(CollectionUtils.isEmpty(fields)){

			return;
		}

		Element complexTypeElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:complexType");
		complexTypeElement.setAttribute("name", getXSDTypeName());

		Element complexContentElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:complexContent");
		complexTypeElement.appendChild(complexContentElement);

		Element extensionElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:extension");
		extensionElement.setAttribute("base", "Query");
		complexContentElement.appendChild(extensionElement);

		Element sequenceElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:sequence");
		extensionElement.appendChild(sequenceElement);

		Element nameElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:element");
		nameElement.setAttribute("name", "Name");
		nameElement.setAttribute("type", "xs:string");
		nameElement.setAttribute("minOccurs", "1");
		nameElement.setAttribute("maxOccurs", "1");
		nameElement.setAttribute("fixed", queryDescriptor.getName());
		sequenceElement.appendChild(nameElement);

		ArrayList<String> fieldElementNames = new ArrayList<String>(fields.size());
		
		for(CalculationBasis calculationBasis : fields){

			sequenceElement.appendChild(doc.createComment(calculationBasis.getLabel()));

			String elementName = generateElementName(calculationBasis, fieldElementNames);
			
			Element fieldElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:element");
			fieldElement.setAttribute("name", elementName);
			fieldElement.setAttribute("type", "xs:string");
			fieldElement.setAttribute("minOccurs", calculationBasis.isRequired() ? "1" : "0");
			fieldElement.setAttribute("maxOccurs", "1");

			sequenceElement.appendChild(fieldElement);
		}

		doc.getDocumentElement().appendChild(complexTypeElement);
	}

	private String generateElementName(CalculationBasis calculationBasis, ArrayList<String> fieldElementNames) {

		String elementName = XMLUtils.toValidElementName(calculationBasis.getLabel());
		
		if(fieldElementNames.contains(elementName)){
			
			int counter = 1;
			
			while(fieldElementNames.contains(elementName + counter)){
				
				counter++;
			}
			
			elementName += counter;
		}
		
		fieldElementNames.add(elementName);
		
		return elementName;
	}
	
	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		
		description = XMLValidationUtils.validateParameter("description", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		helpText = XMLValidationUtils.validateParameter("helpText", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		
		layout = XMLValidationUtils.validateParameter("layout", xmlParser, true, CalculationBasisCRUD.LAYOUT_POPULATOR, errors);
		
		List<XMLParser> xmlParsers = xmlParser.getNodes("Fields/CalculationBasis");
		
		if(xmlParsers != null) {
			
			fields = new ArrayList<CalculationBasis>();
			
			for(XMLParser parser : xmlParsers) {
				
				CalculationBasis calculationBasis = new CalculationBasis();
				
				calculationBasis.populate(parser);
				
				calculationBasis.setQuery(this);
				
				fields.add(calculationBasis);
				
			}
			
		} else {
			
			errors.add(new ValidationError("NoCalculationBasissFound"));
			
		}
		
		if(!errors.isEmpty()){

			throw new ValidationException(errors);
		}
		
	}
	
}
