package com.nordicpeak.flowengine.queries.organizationdetailquery;

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

@Table(name = "organization_detail_queries")
@XMLElement
public class OrganizationDetailQuery extends BaseQuery {

	private static final long serialVersionUID = 2716884146368159522L;

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
	@WebPopulate
	@XMLElement
	private boolean allowSMS;
	
	@DAOManaged
	@WebPopulate
	@XMLElement
	private boolean allowEmail;
	
	@DAOManaged
	@WebPopulate
	@XMLElement
	private boolean allowPhone;
	
	@DAOManaged
	@OneToMany
	@XMLElement
	private List<OrganizationDetailQueryInstance> instances;

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

	public List<OrganizationDetailQueryInstance> getInstances() {

		return instances;
	}

	public void setInstances(List<OrganizationDetailQueryInstance> instances) {

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

	public boolean isAllowSMS() {
		return allowSMS;
	}

	public void setAllowSMS(boolean allowSMS) {
		this.allowSMS = allowSMS;
	}

	public boolean isAllowEmail() {
		return allowEmail;
	}

	public void setAllowEmail(boolean allowEmail) {
		this.allowEmail = allowEmail;
	}

	public boolean isAllowPhone() {
		return allowPhone;
	}

	public void setAllowPhone(boolean allowPhone) {
		this.allowPhone = allowPhone;
	}

	@Override
	public String toString() {

		if (this.queryDescriptor != null) {

			return queryDescriptor.getName() + " (queryID: " + queryID + ")";
		}

		return "OrganizationDetailQuery (queryID: " + queryID + ")";
	}

	@Override
	public String getXSDTypeName() {

		return "OrganizationDetailQuery" + queryID;
	}

	@Override
	public void toXSD(Document doc) {

		
	}
	
	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		
		description = XMLValidationUtils.validateParameter("description", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		helpText = XMLValidationUtils.validateParameter("helpText", xmlParser, false, 1, 65535, StringPopulator.getPopulator(), errors);
		
		allowLetter = xmlParser.getPrimitiveBoolean("allowLetter");
		allowSMS = xmlParser.getPrimitiveBoolean("allowSMS");
		allowEmail = xmlParser.getPrimitiveBoolean("allowEmail");
		allowPhone = xmlParser.getPrimitiveBoolean("allowPhone");
		
		if(!errors.isEmpty()){

			throw new ValidationException(errors);
		}
		
	}
	
}
