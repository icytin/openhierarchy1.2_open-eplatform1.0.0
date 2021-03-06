package demo.oeplatform.org.queries.textfieldquery2;

import java.lang.reflect.Field;
import java.util.List;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.queries.basequery.BaseQueryInstance;

@Table(name = "text_field_query_instances")
@XMLElement
public class TextField2QueryInstance extends BaseQueryInstance {

	private static final long serialVersionUID = -7761759005604863873L;

	public static Field VALUES_RELATION = ReflectionUtils.getField(TextField2QueryInstance.class, "values");
	public static Field QUERY_RELATION = ReflectionUtils.getField(TextField2QueryInstance.class, "query");

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged(columnName="queryID")
	@ManyToOne
	@XMLElement
	private TextField2Query query;

	@DAOManaged
	@OneToMany
	@XMLElement(fixCase=true)
	private List<TextField2Value> values;

	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}


	public void setQueryInstanceID(Integer queryInstanceID) {

		this.queryInstanceID = queryInstanceID;
	}


	public TextField2Query getQuery() {

		return query;
	}


	public void setQuery(TextField2Query query) {

		this.query = query;
	}


	@Override
	public String toString() {

		return "TextFieldQueryQueryInstance (queryInstanceID=" + queryInstanceID + ")";
	}

	@Override
	public void reset() {

		this.values = null;
		super.reset();
	}


	public List<TextField2Value> getValues() {

		return values;
	}


	public void setValues(List<TextField2Value> values) {

		this.values = values;
	}


	@Override
	public String getStringValue() {

		if(values == null){

			return null;

		}

		StringBuilder stringBuilder = new StringBuilder();

		for(TextField2Value value : values){

			if(stringBuilder.length() != 0){

				stringBuilder.append(", ");
			}

			stringBuilder.append(value.getTextField().getLabel() + ":" + value.getValue());
		}

		return stringBuilder.toString();
	}


	public String getFieldValue(String label) {

		if(this.values != null){
			
			for(TextField2Value textFieldValue : this.values){
				
				if(textFieldValue.getTextField().getLabel().equals(label)){
					
					return textFieldValue.getValue();
				}
			}
		}
		
		return null;
	}
}
