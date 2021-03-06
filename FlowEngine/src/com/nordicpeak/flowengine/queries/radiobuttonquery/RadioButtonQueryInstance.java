package com.nordicpeak.flowengine.queries.radiobuttonquery;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.interfaces.ImmutableAlternative;
import com.nordicpeak.flowengine.queries.basequery.BaseQueryInstance;
import com.nordicpeak.flowengine.queries.fixedalternativesquery.FixedAlternativesQueryInstance;


@Table(name = "radio_button_query_instances")
@XMLElement
public class RadioButtonQueryInstance extends BaseQueryInstance implements FixedAlternativesQueryInstance{

	private static final long serialVersionUID = -7761759005604863873L;

	public static Field ALTERNATIVE_RELATION = ReflectionUtils.getField(RadioButtonQueryInstance.class, "alternative");
	public static Field QUERY_RELATION = ReflectionUtils.getField(RadioButtonQueryInstance.class, "query");

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged(columnName="queryID")
	@ManyToOne
	@XMLElement
	private RadioButtonQuery query;

	@DAOManaged(columnName="alternativeID")
	@ManyToOne
	@XMLElement
	private RadioButtonAlternative alternative;

	@DAOManaged
	@XMLElement
	private String freeTextAlternativeValue;

	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}


	public void setQueryInstanceID(Integer queryInstanceID) {

		this.queryInstanceID = queryInstanceID;
	}


	public RadioButtonQuery getQuery() {

		return query;
	}


	public void setQuery(RadioButtonQuery query) {

		this.query = query;
	}

	@Override
	public void reset() {

		this.alternative = null;
		super.reset();
	}

	public void copyQueryValues() {}

	@Override
	public String toString() {

		return "RadioButtonQueryInstance (queryInstanceID=" + queryInstanceID + ")";
	}

	public RadioButtonAlternative getAlternative() {

		return alternative;
	}


	public void setAlternative(RadioButtonAlternative alternative) {

		this.alternative = alternative;
	}

	public String getFreeTextAlternativeValue() {

		return freeTextAlternativeValue;
	}

	public void setFreeTextAlternativeValue(String freeTextAlternativeValue) {

		this.freeTextAlternativeValue = freeTextAlternativeValue;
	}

	@Override
	public List<? extends ImmutableAlternative> getAlternatives() {

		if(alternative == null){

			return null;
		}

		return Collections.singletonList(alternative);
	}

	@Override
	public String getStringValue() {

		if(alternative != null){

			return alternative.getName();

		}else if(freeTextAlternativeValue != null){

			return freeTextAlternativeValue;
		}

		return null;
	}
}
