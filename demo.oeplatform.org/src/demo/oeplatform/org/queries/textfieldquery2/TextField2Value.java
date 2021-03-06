package demo.oeplatform.org.queries.textfieldquery2;

import java.io.Serializable;
import java.lang.reflect.Field;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;

@Table(name = "text_field_query_instance_values")
@XMLElement
public class TextField2Value extends GeneratedElementable implements Serializable {

	private static final long serialVersionUID = 6354162344651352603L;

	public static final Field TEXT_FIELD_RELATION = ReflectionUtils.getField(TextField2Value.class, "textField");

	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer textFieldValueID;

	@DAOManaged(columnName = "queryInstanceID")
	@ManyToOne
	private TextField2QueryInstance instance;

	@DAOManaged(columnName = "textFieldID")
	@ManyToOne
	@XMLElement
	private TextField2 textField;

	@DAOManaged
	@XMLElement
	private String value;

	public TextField2Value() {};

	public TextField2Value(TextField2 textField, String value) {

		super();
		this.textField = textField;
		this.value = value;
	}

	public Integer getTextFieldValueID() {

		return textFieldValueID;
	}

	public void setTextFieldValueID(Integer textFieldValueID) {

		this.textFieldValueID = textFieldValueID;
	}

	public TextField2QueryInstance getInstance() {

		return instance;
	}

	public void setInstance(TextField2QueryInstance instance) {

		this.instance = instance;
	}

	public TextField2 getTextField() {

		return textField;
	}

	public void setTextField(TextField2 textField) {

		this.textField = textField;
	}

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}
}
