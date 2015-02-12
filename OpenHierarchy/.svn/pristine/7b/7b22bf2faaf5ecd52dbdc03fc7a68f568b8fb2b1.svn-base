/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.core.populators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import se.unlogic.hierarchy.core.beans.VirtualMenuItem;
import se.unlogic.hierarchy.core.enums.MenuItemType;
import se.unlogic.standardutils.dao.BeanResultSetPopulator;
import se.unlogic.standardutils.enums.EnumUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationErrorType;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.webutils.http.BeanRequestPopulator;

public class VirtualMenuItemPopulator implements BeanRequestPopulator<VirtualMenuItem>,BeanResultSetPopulator<VirtualMenuItem> {

	@Override
	public VirtualMenuItem populate(HttpServletRequest req) throws ValidationException {

		return populate(new VirtualMenuItem(),req);

	}

	@Override
	public VirtualMenuItem populate(VirtualMenuItem virtualMenuItem, HttpServletRequest req) throws ValidationException {

		ArrayList<ValidationError> validationErrors = new ArrayList<ValidationError>();

		String itemtype = req.getParameter("itemtype");

		if(StringUtils.isEmpty(itemtype)){
			validationErrors.add(new ValidationError("itemtype",ValidationErrorType.RequiredField));
		}else{

			MenuItemType menuItemType = EnumUtils.toEnum(MenuItemType.class, itemtype);

			if(menuItemType == null){
				validationErrors.add(new ValidationError("itemtype",ValidationErrorType.InvalidFormat));
			}else{
				virtualMenuItem.setItemType(menuItemType);
			}
		}

		if(virtualMenuItem.getItemType() != null && !virtualMenuItem.getItemType().equals(MenuItemType.BLANK)){

			String name = req.getParameter("name");

			if(StringUtils.isEmpty(name)){
				validationErrors.add(new ValidationError("name",ValidationErrorType.RequiredField));
			}else{
				virtualMenuItem.setName(name);
			}

			String description = req.getParameter("description");

			if(StringUtils.isEmpty(description)){
				validationErrors.add(new ValidationError("description",ValidationErrorType.RequiredField));
			}else{
				virtualMenuItem.setDescription(description);
			}

			String url = req.getParameter("url");

			if(!StringUtils.isEmpty(url)){
				virtualMenuItem.setUrl(url);
			}else{
				virtualMenuItem.setUrl(null);
			}

		}else{

			virtualMenuItem.setName(null);
			virtualMenuItem.setDescription(null);
			virtualMenuItem.setUrl(null);
		}

		virtualMenuItem.setAnonymousAccess(req.getParameter("anonymousAccess") != null);
		virtualMenuItem.setUserAccess(req.getParameter("userAccess") != null);
		virtualMenuItem.setAdminAccess(req.getParameter("adminAccess") != null);

		if(validationErrors.isEmpty()){

			String[] allowedUserIDs = req.getParameterValues("user");
			ArrayList<Integer> userIDs = null;

			if(allowedUserIDs != null){
				userIDs = NumberUtils.toInt(allowedUserIDs);
			}

			virtualMenuItem.setAllowedUserIDs(userIDs);


			String[] allowedGroupIDs = req.getParameterValues("group");
			ArrayList<Integer> groupIDs = null;

			if(allowedGroupIDs != null){
				groupIDs = NumberUtils.toInt(allowedGroupIDs);
			}

			virtualMenuItem.setAllowedGroupIDs(groupIDs);

			return virtualMenuItem;
		}else{
			throw new ValidationException(validationErrors);
		}
	}

	@Override
	public VirtualMenuItem populate(ResultSet rs) throws SQLException {

		VirtualMenuItem virtualMenuItem = new VirtualMenuItem();

		virtualMenuItem.setMenuItemID(rs.getInt("menuItemID"));
		virtualMenuItem.setItemType(MenuItemType.valueOf(rs.getString("itemtype")));
		virtualMenuItem.setName(rs.getString("name"));
		virtualMenuItem.setDescription(rs.getString("description"));
		virtualMenuItem.setUrl(rs.getString("url"));
		virtualMenuItem.setAnonymousAccess(rs.getBoolean("anonymousAccess"));
		virtualMenuItem.setUserAccess(rs.getBoolean("userAccess"));
		virtualMenuItem.setAdminAccess(rs.getBoolean("adminAccess"));
		virtualMenuItem.setSectionID(rs.getInt("sectionID"));

		return virtualMenuItem;
	}
}
