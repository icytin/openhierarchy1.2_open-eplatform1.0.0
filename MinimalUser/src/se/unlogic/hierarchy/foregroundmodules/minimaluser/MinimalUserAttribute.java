package se.unlogic.hierarchy.foregroundmodules.minimaluser;

import se.unlogic.hierarchy.core.beans.SimpleAttribute;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.ManyToOne;
import se.unlogic.standardutils.dao.annotations.Table;

@Table(name = "minimal_user_attributes")
public class MinimalUserAttribute extends SimpleAttribute {

	private static final long serialVersionUID = 1310945763120402890L;
	
	@DAOManaged(columnName="userID")
	@Key
	@ManyToOne
	protected MinimalUser user;

	public MinimalUserAttribute() {

	}

	public MinimalUserAttribute(String name, String value) {

		super(name, value);
	}

	public MinimalUser getUser() {

		return user;
	}

	public void setUser(MinimalUser user) {

		this.user = user;
	}
}
