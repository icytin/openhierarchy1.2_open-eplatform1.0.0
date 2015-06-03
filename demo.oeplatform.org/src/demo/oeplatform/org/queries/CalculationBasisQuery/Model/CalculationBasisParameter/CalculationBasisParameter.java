package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter;

import se.unlogic.standardutils.json.JsonLeaf;
import se.unlogic.standardutils.json.JsonNode;
import se.unlogic.standardutils.json.JsonObject;

public class CalculationBasisParameter {
	private int parameterID;
	private int queryID;
	private String name;
	private String value;
	private Integer refQueryID;
	private Integer refSubQueryID;
	private String description;

	public CalculationBasisParameter(String name, int queryId,
			Integer refQueryId, Integer refSubQueryId, String value,
			String description) {
		this.name = name;
		this.queryID = queryId;
		this.refQueryID = refQueryId;
		this.refSubQueryID = refSubQueryId;
		this.value = value;
		this.description = description;
	}

	public int getParameterID() {
		return parameterID;
	}

	public void setParameterID(int parameterID) {
		this.parameterID = parameterID;
	}

	public int getQueryID() {
		return queryID;
	}

	public void setQueryID(int queryID) {
		this.queryID = queryID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getRefQueryID() {
		return refQueryID;
	}

	public void setRefQueryID(Integer refQueryID) {
		this.refQueryID = refQueryID;
	}

	public Integer getRefSubQueryID() {
		return refSubQueryID;
	}

	public void setRefSubQueryID(Integer refSubQueryID) {
		this.refSubQueryID = refSubQueryID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JsonNode toJson() {
		JsonObject json = new JsonObject();
		//TODO vad blir null?
		json.putField("parameterID", new JsonLeaf(Integer.toString(getParameterID())));
		json.putField("queryID", new JsonLeaf(Integer.toString(getQueryID())));
		json.putField("name", new JsonLeaf(getName()));
		json.putField("value", new JsonLeaf(getValue()));
		
		if (getRefQueryID()!=null)
			json.putField("refQueryID", new JsonLeaf(Integer.toString(getRefQueryID())));
		else
			json.putField("refQueryID", new JsonLeaf(null));
		
		if (getRefSubQueryID()!=null)
			json.putField("refSubQueryID", new JsonLeaf(Integer.toString(getRefSubQueryID())));
		else
			json.putField("refSubQueryID", new JsonLeaf(null));
		
		json.putField("description", new JsonLeaf(getDescription()));
		return json;
	}
}
