package com.nordicpeak.flowengine.queries.textareaquery;

import java.lang.reflect.Field;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.queries.basequery.BaseQueryInstance;

@Table(name = "text_area_query_instances")
@XMLElement
public class TextAreaQueryInstance extends BaseQueryInstance {

	private static final long serialVersionUID = -7761759005604863873L;

	public static Field QUERY_RELATION = ReflectionUtils.getField(TextAreaQueryInstance.class, "query");

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged(columnName = "queryID")
	@ManyToOne
	@XMLElement
	private TextAreaQuery query;

	@DAOManaged
	@XMLElement
	private String value;

	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}

	public void setQueryInstanceID(Integer queryInstanceID) {

		this.queryInstanceID = queryInstanceID;
	}

	public TextAreaQuery getQuery() {

		return query;
	}

	public void setQuery(TextAreaQuery query) {

		this.query = query;
	}

	@Override
	public void reset() {

		this.value = null;
		super.reset();
	}

	public void copyQueryValues() {

	}

	@Override
	public String toString() {

		return "TextAreaQueryInstance (queryInstanceID=" + queryInstanceID + ")";
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	@Override
	public String getStringValue() {

		return value;
	}
}
