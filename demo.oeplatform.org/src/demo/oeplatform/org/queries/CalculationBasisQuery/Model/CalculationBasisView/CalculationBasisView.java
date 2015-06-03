package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisView;

import se.unlogic.standardutils.json.JsonLeaf;
import se.unlogic.standardutils.json.JsonNode;
import se.unlogic.standardutils.json.JsonObject;

public class CalculationBasisView {

	private int viewId;
	private int queryId;
	private String html;
	
	public CalculationBasisView(int queryId,String html){
		this.queryId = queryId;
		this.html=html;
	}

	public JsonNode toJson() {
		JsonObject json = new JsonObject();

		json.putField("viewId", new JsonLeaf(Integer.toString(viewId)));
		json.putField("queryID", new JsonLeaf(Integer.toString(queryId)));
		json.putField("html", new JsonLeaf(html));

		return json;
	}
	
	
	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

	public int getQueryId() {
		return queryId;
	}

	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	
}
