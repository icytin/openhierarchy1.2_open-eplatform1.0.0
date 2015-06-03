package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisFormula;

import se.unlogic.standardutils.json.JsonLeaf;
import se.unlogic.standardutils.json.JsonNode;
import se.unlogic.standardutils.json.JsonObject;

public class CalculationBasisFormula {

	private int formulaId;
	private int queryId;
	private String name;
	private String formula;
	private String description;

	public CalculationBasisFormula(int queryId, String name,String formula, String description){
		this.queryId = queryId;
		this.name = name;
		this.description =description;
		this.formula = formula;
	}
	
	public JsonNode toJson() {
		JsonObject json = new JsonObject();

		json.putField("formulaId", new JsonLeaf(Integer.toString(formulaId)));
		json.putField("queryID", new JsonLeaf(Integer.toString(queryId)));
		json.putField("name", new JsonLeaf(name));
		json.putField("formula", new JsonLeaf(formula));
		json.putField("description", new JsonLeaf(description));
		
		return json;
	}
	
	public int getFormulaId() {
		return formulaId;
	}
	public void setFormulaId(int formulaId) {
		this.formulaId = formulaId;
	}
	public int getQueryId() {
		return queryId;
	}
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
