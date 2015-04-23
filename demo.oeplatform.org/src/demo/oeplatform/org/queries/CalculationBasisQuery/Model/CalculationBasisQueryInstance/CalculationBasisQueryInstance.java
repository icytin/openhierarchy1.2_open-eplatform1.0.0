package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQueryInstance;

import java.lang.reflect.Field;

import se.unlogic.hierarchy.core.beans.MutableUser;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.AttributeHandler;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.queries.basequery.BaseQueryInstance;

import demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisQuery.CalculationBasisQuery;

@Table(name = "calculation_basis_query_instances")
@XMLElement
public class CalculationBasisQueryInstance extends BaseQueryInstance {

	private static final long serialVersionUID = -7761759005604863873L;

	public static Field QUERY_RELATION = ReflectionUtils.getField(CalculationBasisQueryInstance.class, "query");

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged(columnName = "queryID")
	@ManyToOne
	@XMLElement
	private CalculationBasisQuery query;

	@DAOManaged
	@XMLElement
	private String firstname;

	@DAOManaged
	@XMLElement
	private String lastname;

	@DAOManaged
	@XMLElement
	private String address;

	@DAOManaged
	@XMLElement
	private String zipCode;

	@DAOManaged
	@XMLElement
	private String postalAddress;

	@DAOManaged
	@XMLElement
	private String phone;

	@DAOManaged
	@XMLElement
	private String email;

	@DAOManaged
	@XMLElement
	private String mobilePhone;

	@DAOManaged
	@XMLElement
	private boolean contactByLetter;

	@DAOManaged
	@XMLElement
	private boolean contactBySMS;

	@DAOManaged
	@XMLElement
	private boolean contactByEmail;

	@DAOManaged
	@XMLElement
	private boolean contactByPhone;

	@DAOManaged
	@XMLElement
	private boolean persistUserProfile;

	@XMLElement
	private boolean isMutableUser;

	public String getAddress() {

		return address;
	}

	public String getFirstname() {

		return firstname;
	}

	public void setFirstname(String firstname) {

		this.firstname = firstname;
	}

	public String getLastname() {

		return lastname;
	}

	public void setLastname(String lastname) {

		this.lastname = lastname;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getZipCode() {

		return zipCode;
	}

	public void setZipCode(String zipCode) {

		this.zipCode = zipCode;
	}

	public String getPostalAddress() {

		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {

		this.postalAddress = postalAddress;
	}

	public String getPhone() {

		return phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getMobilePhone() {

		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {

		this.mobilePhone = mobilePhone;
	}

	public boolean isContactByLetter() {

		return contactByLetter;
	}

	public void setContactByLetter(boolean contactByLetter) {

		this.contactByLetter = contactByLetter;
	}

	public boolean isContactBySMS() {

		return contactBySMS;
	}

	public void setContactBySMS(boolean contactBySMS) {

		this.contactBySMS = contactBySMS;
	}

	public boolean isContactByEmail() {

		return contactByEmail;
	}

	public void setContactByEmail(boolean contactByEmail) {

		this.contactByEmail = contactByEmail;
	}

	public boolean isContactByPhone() {

		return contactByPhone;
	}

	public void setContactByPhone(boolean contactByPhone) {

		this.contactByPhone = contactByPhone;
	}

	public boolean isPersistUserProfile() {

		return persistUserProfile;
	}

	public void setPersistUserProfile(boolean persistUserProfile) {

		this.persistUserProfile = persistUserProfile;
	}

	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}

	public void setQueryInstanceID(Integer queryInstanceID) {

		this.queryInstanceID = queryInstanceID;
	}

	public CalculationBasisQuery getQuery() {

		return query;
	}

	public void setQuery(CalculationBasisQuery query) {

		this.query = query;
	}

	public boolean isMutableUser() {

		return isMutableUser;
	}

	public boolean isPopulated() {

		if (StringUtils.isEmpty(firstname) && StringUtils.isEmpty(lastname) && StringUtils.isEmpty(address) && StringUtils.isEmpty(zipCode) && StringUtils.isEmpty(postalAddress) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(email) && StringUtils.isEmpty(mobilePhone)) {

			return false;
		}

		return true;
	}

	@Override
	public void reset() {

		this.firstname = null;
		this.lastname = null;
		this.address = null;
		this.zipCode = null;
		this.postalAddress = null;
		this.phone = null;
		this.email = null;
		this.mobilePhone = null;
		super.reset();
	}

	@Override
	public String toString() {

		return "CalculationBasisQueryInstance (queryInstanceID=" + queryInstanceID + ")";
	}

	@Override
	public String getStringValue() {

		return null;
	}

	public void initialize(User user) {

		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();

		AttributeHandler attributeHandler = user.getAttributeHandler();

		if (attributeHandler != null) {

			this.address = attributeHandler.getString("address");
			this.zipCode = attributeHandler.getString("zipCode");
			this.postalAddress = attributeHandler.getString("postalAddress");
			this.mobilePhone = attributeHandler.getString("mobilePhone");
			this.phone = attributeHandler.getString("phone");

			this.contactByEmail = attributeHandler.getPrimitiveBoolean("contactByEmail");
			this.contactByLetter = attributeHandler.getPrimitiveBoolean("contactByLetter");
			this.contactByPhone = attributeHandler.getPrimitiveBoolean("contactByPhone");
			this.contactBySMS = attributeHandler.getPrimitiveBoolean("contactBySMS");
		}

		this.isMutableUser = user instanceof MutableUser;

	}

}
