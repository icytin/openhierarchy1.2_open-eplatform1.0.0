package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter;

public class CalculationBasisParameter {
	private int parameterID;
	private int queryID;
	private String name;
	private String value;
	private int refQueryID;
	private int refSubQueryID;
	private String description;
	
	public CalculationBasisParameter(String name, int queryId, int refQueryId, int refSubQueryId, String value, String description)
	{
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
	public int getRefQueryID() {
		return refQueryID;
	}
	public void setRefQueryID(int refQueryID) {
		this.refQueryID = refQueryID;
	}
	public int getRefSubQueryID() {
		return refSubQueryID;
	}
	public void setRefSubQueryID(int refSubQueryID) {
		this.refSubQueryID = refSubQueryID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
