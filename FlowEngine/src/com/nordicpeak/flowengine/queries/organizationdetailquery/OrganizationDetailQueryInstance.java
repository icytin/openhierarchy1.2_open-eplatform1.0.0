package com.nordicpeak.flowengine.queries.organizationdetailquery;

import java.lang.reflect.Field;

import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.queries.basequery.BaseQueryInstance;

@Table(name = "organization_detail_query_instances")
@XMLElement
public class OrganizationDetailQueryInstance extends BaseQueryInstance {

	private static final long serialVersionUID = -2166602898244004279L;

	public static Field QUERY_RELATION = ReflectionUtils.getField(OrganizationDetailQueryInstance.class, "query");

	@DAOManaged
	@Key
	@XMLElement
	private Integer queryInstanceID;

	@DAOManaged(columnName = "queryID")
	@ManyToOne
	@XMLElement
	private OrganizationDetailQuery query;

	@DAOManaged
	@XMLElement
	private String name;

	@DAOManaged
	@XMLElement
	private String organizationNumber;
	
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
	private String firstname;
	
	@DAOManaged
	@XMLElement
	private String lastname;
	
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
	private Integer organizationID;
	
	@DAOManaged
	@XMLElement
	private boolean persistOrganization;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public String getAddress() {
		return address;
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

	public Integer getQueryInstanceID() {

		return queryInstanceID;
	}

	public void setQueryInstanceID(Integer queryInstanceID) {

		this.queryInstanceID = queryInstanceID;
	}

	public OrganizationDetailQuery getQuery() {

		return query;
	}

	public void setQuery(OrganizationDetailQuery query) {

		this.query = query;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public boolean isPersistOrganization() {
		return persistOrganization;
	}

	public void setPersistOrganization(boolean persistOrganization) {
		this.persistOrganization = persistOrganization;
	}

	public boolean isPopulated() {

		if(StringUtils.isEmpty(name) && StringUtils.isEmpty(organizationNumber) && StringUtils.isEmpty(address) && StringUtils.isEmpty(zipCode) && StringUtils.isEmpty(postalAddress) &&StringUtils.isEmpty(phone) && StringUtils.isEmpty(email) && StringUtils.isEmpty(mobilePhone)) {

			return false;
		}

		return true;
	}

	@Override
	public void reset() {

		this.name = null;
		this.organizationNumber = null;
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

		return "OrganizationDetailQueryInstance (queryInstanceID=" + queryInstanceID + ")";
	}

	@Override
	public String getStringValue() {

		return null;
	}

}
