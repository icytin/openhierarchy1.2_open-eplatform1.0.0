package com.nordicpeak.flowengine.beans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OrderBy;
import se.unlogic.standardutils.dao.annotations.SimplifiedRelation;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.populators.PositiveStringIntegerPopulator;
import se.unlogic.standardutils.populators.StringPopulator;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLParser;
import se.unlogic.standardutils.xml.XMLParserPopulateable;
import se.unlogic.standardutils.xml.XMLValidationUtils;

import com.nordicpeak.flowengine.interfaces.MutableEvaluatorDescriptor;

@Table(name = "flowengine_evaluator_descriptors")
@XMLElement
public class EvaluatorDescriptor extends GeneratedElementable implements MutableEvaluatorDescriptor, XMLParserPopulateable {

	private static final long serialVersionUID = 7587379873522196243L;

	public static final Field QUERY_DESCRIPTOR_RELATION = ReflectionUtils.getField(EvaluatorDescriptor.class,"queryDescriptor");

	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer evaluatorID;

	@DAOManaged
	@WebPopulate(required = true, maxLength = 255)
	@XMLElement
	private String name;

	@DAOManaged
	@OrderBy
	@XMLElement
	private Integer sortIndex;

	@DAOManaged
	@XMLElement
	private String evaluatorTypeID;

	@DAOManaged
	@WebPopulate
	@XMLElement
	private boolean enabled;

	@DAOManaged(columnName = "queryID")
	@ManyToOne
	@XMLElement
	private QueryDescriptor queryDescriptor;

	@DAOManaged
	@OneToMany(autoAdd = true, autoGet = true, autoUpdate = true)
	@SimplifiedRelation(table = "flowengine_evaluators_target_queries", remoteValueColumnName = "queryID")
	@WebPopulate(paramName="queryID")
	@XMLElement(fixCase=true,childName="queryID")
	private List<Integer> targetQueryIDs;

	private XMLParser importParser;
	
	public XMLParser getImportParser() {
	
		return importParser;
	}
	
	@Override
	public String getName() {

		return name;
	}

	@Override
	public void setName(String name) {

		this.name = name;
	}

	public Integer getSortIndex() {

		return sortIndex;
	}

	public void setSortIndex(Integer index) {

		this.sortIndex = index;
	}

	@Override
	public String toString() {

		return name + " (ID: " + evaluatorID + ")";
	}

	public Integer getEvaluatorID() {

		return evaluatorID;
	}

	public void setEvaluatorID(Integer evaluatorID) {

		this.evaluatorID = evaluatorID;
	}

	public String getEvaluatorTypeID() {

		return evaluatorTypeID;
	}

	public void setEvaluatorTypeID(String evaluatorTypeID) {

		this.evaluatorTypeID = evaluatorTypeID;
	}

	public boolean isEnabled() {

		return enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

	public QueryDescriptor getQueryDescriptor() {

		return queryDescriptor;
	}

	public void setQueryDescriptor(QueryDescriptor queryDescriptor) {

		this.queryDescriptor = queryDescriptor;
	}

	public List<Integer> getTargetQueryIDs() {

		return targetQueryIDs;
	}

	public void setTargetQueryIDs(List<Integer> targetQueryIDs) {

		this.targetQueryIDs = targetQueryIDs;
	}

	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		List<ValidationError> errors = new ArrayList<ValidationError>();

		this.name = XMLValidationUtils.validateParameter("name", xmlParser, true, 1, 255, StringPopulator.getPopulator(), errors);
		this.sortIndex = XMLValidationUtils.validateParameter("sortIndex", xmlParser, true, PositiveStringIntegerPopulator.getPopulator(), errors);
		this.evaluatorTypeID = XMLValidationUtils.validateParameter("evaluatorTypeID", xmlParser, true, 1, 255, StringPopulator.getPopulator(), errors);

		this.enabled = xmlParser.getPrimitiveBoolean("enabled");

		this.targetQueryIDs = xmlParser.getIntegers("TargetQueryIDs/queryID");

		if(!errors.isEmpty()){

			throw new ValidationException(errors);
		}
		
		this.importParser = xmlParser;
	}

}
