/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.core.daos.implementations.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import se.unlogic.hierarchy.core.beans.SimpleSectionDescriptor;
import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.hierarchy.core.daos.interfaces.SectionDAO;
import se.unlogic.hierarchy.core.interfaces.SectionDescriptor;
import se.unlogic.hierarchy.core.populators.SectionDescriptorPopulator;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.dao.querys.ArrayListQuery;
import se.unlogic.standardutils.dao.querys.IntegerKeyCollector;
import se.unlogic.standardutils.dao.querys.ObjectQuery;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.populators.IntegerPopulator;

public class MySQLSectionDAO extends BaseDAO implements SectionDAO {

	private static SectionDescriptorPopulator Populator = new SectionDescriptorPopulator();

	protected MySQLSectionDAO(DataSource ds) {
		super(ds);
	}

	@Override
	public ArrayList<SimpleSectionDescriptor> getSubSections(SimpleSectionDescriptor section, boolean getSubSections) throws SQLException {

		Connection connection = null;

		try {

			connection = this.dataSource.getConnection();

			return this.getSubSections(connection, section, getSubSections);

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public SimpleSectionDescriptor getRootSection(boolean getSubSections) throws SQLException {

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			ObjectQuery<SimpleSectionDescriptor> query = new ObjectQuery<SimpleSectionDescriptor>(connection, false, "SELECT * FROM openhierarchy_sections WHERE parentSectionID IS NULL", Populator);

			SimpleSectionDescriptor simpleSectionDescriptor = query.executeQuery();

			if (simpleSectionDescriptor != null) {

				this.getSectionUsers(simpleSectionDescriptor, connection);
				this.getSectionGroups(simpleSectionDescriptor, connection);

				if (getSubSections) {
					simpleSectionDescriptor.setSubSectionsList(this.getSubSections(connection, simpleSectionDescriptor, true));
				}
			}

			return simpleSectionDescriptor;

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	private ArrayList<SimpleSectionDescriptor> getSubSections(Connection connection, SimpleSectionDescriptor section, boolean getSubSections) throws SQLException {

		ArrayListQuery<SimpleSectionDescriptor> query;

		query = new ArrayListQuery<SimpleSectionDescriptor>(connection, false, "SELECT * FROM openhierarchy_sections WHERE parentSectionID = ? ORDER BY name", Populator);
		query.setObject(1, section.getSectionID());

		ArrayList<SimpleSectionDescriptor> sectionList = query.executeQuery();

		if (sectionList != null) {
			for (SimpleSectionDescriptor subSection : sectionList) {

				subSection.setFullAlias(section.getFullAlias() + "/" + subSection.getAlias());

				this.getSectionUsers(subSection, connection);
				this.getSectionGroups(subSection, connection);

				if (getSubSections) {
					subSection.setSubSectionsList(this.getSubSections(connection, subSection, true));
				}
			}
		}

		return sectionList;
	}

	@Override
	public ArrayList<SimpleSectionDescriptor> getEnabledSubSections(SectionDescriptor sectionDescriptor, boolean getSubSections) throws SQLException {

		Connection connection = null;

		try {
			connection = this.dataSource.getConnection();

			return this.getEnabledSubSections(connection, sectionDescriptor, getSubSections);

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	private ArrayList<SimpleSectionDescriptor> getEnabledSubSections(Connection connection, SectionDescriptor sectionDescriptor, boolean getSubSections) throws SQLException {
		ArrayListQuery<SimpleSectionDescriptor> query;

		query = new ArrayListQuery<SimpleSectionDescriptor>(connection, false, "SELECT * FROM openhierarchy_sections WHERE parentSectionID = ? and enabled = true ORDER BY name", Populator);
		query.setInt(1, sectionDescriptor.getSectionID());

		ArrayList<SimpleSectionDescriptor> sectionList = query.executeQuery();

		if (sectionList != null) {
			for (SimpleSectionDescriptor subSection : sectionList) {

				// Create full alias using full alias of previous section
				subSection.setFullAlias(sectionDescriptor.getFullAlias() + "/" + subSection.getAlias());

				this.getSectionUsers(subSection, connection);
				this.getSectionGroups(subSection, connection);

				if (getSubSections) {
					subSection.setSubSectionsList(this.getEnabledSubSections(connection, subSection, true));
				}
			}
		}

		return sectionList;
	}

	private void getSectionGroups(SimpleSectionDescriptor subSection, Connection connection) throws SQLException {

		ArrayListQuery<Integer> query = new ArrayListQuery<Integer>(connection, false, "SELECT groupID from openhierarchy_section_groups WHERE sectionID = ?", IntegerPopulator.getPopulator());

		query.setInt(1, subSection.getSectionID());

		subSection.setAllowedGroupIDs(query.executeQuery());
	}

	private void getSectionUsers(SimpleSectionDescriptor subSection, Connection connection) throws SQLException {

		ArrayListQuery<Integer> query = new ArrayListQuery<Integer>(connection, false, "SELECT userID from openhierarchy_section_users WHERE sectionID = ?", IntegerPopulator.getPopulator());

		query.setInt(1, subSection.getSectionID());

		subSection.setAllowedUserIDs(query.executeQuery());

	}

	@Override
	public SimpleSectionDescriptor getSection(int sectionID, boolean fullAlias) throws SQLException {

		Connection connection = null;

		try {
			connection = this.dataSource.getConnection();

			ObjectQuery<SimpleSectionDescriptor> query = new ObjectQuery<SimpleSectionDescriptor>(connection, false, "SELECT * FROM openhierarchy_sections WHERE sectionID = ?", Populator);

			query.setInt(1, sectionID);

			SimpleSectionDescriptor simpleSectionDescriptor = query.executeQuery();

			if (simpleSectionDescriptor != null) {

				this.getSectionUsers(simpleSectionDescriptor, connection);
				this.getSectionGroups(simpleSectionDescriptor, connection);

				if (fullAlias && simpleSectionDescriptor.getParentSectionID() != null) {
					this.getReverseFullAlias(simpleSectionDescriptor);
				}
			}

			return simpleSectionDescriptor;

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	@Override
	public void getReverseFullAlias(SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		if (simpleSectionDescriptor.getParentSectionID() != null) {

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("/" + simpleSectionDescriptor.getAlias());

			SimpleSectionDescriptor parentSection = this.getSection(simpleSectionDescriptor.getParentSectionID(), false);

			while (parentSection != null && parentSection.getParentSectionID() != null) {

				stringBuilder.insert(0, "/" + parentSection.getAlias());

				parentSection = this.getSection(parentSection.getParentSectionID(), false);

			}

			simpleSectionDescriptor.setFullAlias(stringBuilder.toString());
		} else {
			simpleSectionDescriptor.setFullAlias("");
		}
	}

	@Override
	public void update(SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		TransactionHandler transactionHandler = null;

		try {
			transactionHandler = new TransactionHandler(dataSource);

			UpdateQuery query = transactionHandler.getUpdateQuery("UPDATE openhierarchy_sections SET parentSectionID = ?, alias = ?, enabled = ?, anonymousAccess = ?, userAccess = ?, adminAccess = ?, visibleInMenu = ?, breadCrumb = ?, name = ?, description = ?, anonymousDefaultURI = ?, userDefaultURI = ?, requiredProtocol = ? WHERE sectionID = ?");

			query.setObject(1, simpleSectionDescriptor.getParentSectionID());
			query.setString(2, simpleSectionDescriptor.getAlias());
			query.setBoolean(3, simpleSectionDescriptor.isEnabled());
			query.setBoolean(4, simpleSectionDescriptor.allowsAnonymousAccess());
			query.setBoolean(5, simpleSectionDescriptor.allowsUserAccess());
			query.setBoolean(6, simpleSectionDescriptor.allowsAdminAccess());
			query.setBoolean(7, simpleSectionDescriptor.isVisibleInMenu());
			query.setBoolean(8, simpleSectionDescriptor.hasBreadCrumb());
			query.setString(9, simpleSectionDescriptor.getName());
			query.setString(10, simpleSectionDescriptor.getDescription());
			query.setString(11, simpleSectionDescriptor.getAnonymousDefaultURI());
			query.setString(12, simpleSectionDescriptor.getUserDefaultURI());
			if(simpleSectionDescriptor.getRequiredProtocol() != null) {
				query.setString(13, simpleSectionDescriptor.getRequiredProtocol().toString());
			} else {
				query.setString(13, null);
			}
			query.setInt(14, simpleSectionDescriptor.getSectionID());

			query.executeUpdate();

			this.deleteSectionUsers(transactionHandler, simpleSectionDescriptor);

			if (simpleSectionDescriptor.getAllowedUserIDs() != null && !simpleSectionDescriptor.getAllowedUserIDs().isEmpty()) {
				this.setUsers(transactionHandler, simpleSectionDescriptor);
			}

			this.deleteSectionGroups(transactionHandler, simpleSectionDescriptor);

			if (simpleSectionDescriptor.getAllowedGroupIDs() != null && !simpleSectionDescriptor.getAllowedGroupIDs().isEmpty()) {
				this.setGroups(transactionHandler, simpleSectionDescriptor);
			}

			transactionHandler.commit();
		} finally {
			if (transactionHandler != null && !transactionHandler.isClosed()) {
				transactionHandler.abort();
			}
		}
	}

	private void deleteSectionGroups(TransactionHandler transactionHandler, SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		UpdateQuery query = transactionHandler.getUpdateQuery("DELETE FROM openhierarchy_section_groups WHERE sectionID = ?");

		query.setInt(1, simpleSectionDescriptor.getSectionID());

		query.executeUpdate();

	}

	private void deleteSectionUsers(TransactionHandler transactionHandler, SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		UpdateQuery query = transactionHandler.getUpdateQuery("DELETE FROM openhierarchy_section_users WHERE sectionID = ?");

		query.setInt(1, simpleSectionDescriptor.getSectionID());

		query.executeUpdate();
	}

	@Override
	public void add(SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		TransactionHandler transactionHandler = null;

		try {
			transactionHandler = new TransactionHandler(dataSource);

			UpdateQuery query = transactionHandler.getUpdateQuery("INSERT INTO openhierarchy_sections VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			query.setObject(1, simpleSectionDescriptor.getParentSectionID());
			query.setString(2, simpleSectionDescriptor.getAlias());
			query.setBoolean(3, simpleSectionDescriptor.isEnabled());
			query.setBoolean(4, simpleSectionDescriptor.allowsAnonymousAccess());
			query.setBoolean(5, simpleSectionDescriptor.allowsUserAccess());
			query.setBoolean(6, simpleSectionDescriptor.allowsAdminAccess());
			query.setBoolean(7, simpleSectionDescriptor.isVisibleInMenu());
			query.setBoolean(8, simpleSectionDescriptor.hasBreadCrumb());
			query.setString(9, simpleSectionDescriptor.getName());
			query.setString(10, simpleSectionDescriptor.getDescription());
			query.setString(11, simpleSectionDescriptor.getAnonymousDefaultURI());
			query.setString(12, simpleSectionDescriptor.getUserDefaultURI());
			if(simpleSectionDescriptor.getRequiredProtocol() != null) {
				query.setString(13, simpleSectionDescriptor.getRequiredProtocol().toString());
			} else {
				query.setString(13, null);
			}

			IntegerKeyCollector keyCollector = new IntegerKeyCollector();

			query.executeUpdate(keyCollector);

			simpleSectionDescriptor.setSectionID(keyCollector.getKeyValue());

			if (simpleSectionDescriptor.getAllowedUserIDs() != null && !simpleSectionDescriptor.getAllowedUserIDs().isEmpty()) {
				this.setUsers(transactionHandler, simpleSectionDescriptor);
			}

			if (simpleSectionDescriptor.getAllowedGroupIDs() != null && !simpleSectionDescriptor.getAllowedGroupIDs().isEmpty()) {
				this.setGroups(transactionHandler, simpleSectionDescriptor);
			}

			transactionHandler.commit();
		} finally {
			if (transactionHandler != null && !transactionHandler.isClosed()) {
				transactionHandler.abort();
			}
		}
	}

	private void setGroups(TransactionHandler transactionHandler, SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		for (Integer groupID : simpleSectionDescriptor.getAllowedGroupIDs()) {
			UpdateQuery query = transactionHandler.getUpdateQuery("INSERT INTO openhierarchy_section_groups VALUES (?,?)");

			query.setInt(1, simpleSectionDescriptor.getSectionID());
			query.setInt(2, groupID);

			query.executeUpdate();
		}

	}

	private void setUsers(TransactionHandler transactionHandler, SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {

		for (Integer userID : simpleSectionDescriptor.getAllowedUserIDs()) {
			UpdateQuery query = transactionHandler.getUpdateQuery("INSERT INTO openhierarchy_section_users VALUES (?,?)");

			query.setInt(1, simpleSectionDescriptor.getSectionID());
			query.setInt(2, userID);

			query.executeUpdate();
		}

	}

	@Override
	public void delete(SimpleSectionDescriptor simpleSectionDescriptor) throws SQLException {
		UpdateQuery query = new UpdateQuery(this.dataSource.getConnection(), true, "DELETE FROM openhierarchy_sections WHERE sectionID = ?");

		query.setInt(1, simpleSectionDescriptor.getSectionID());

		query.executeUpdate();
	}

	@Override
	public SimpleSectionDescriptor getSection(Integer sectionID, String alias) throws SQLException {

		ObjectQuery<SimpleSectionDescriptor> query = new ObjectQuery<SimpleSectionDescriptor>(this.dataSource.getConnection(), true, "SELECT * FROM openhierarchy_sections WHERE parentSectionID = ? AND alias = ?", Populator);

		query.setObject(1, sectionID);
		query.setString(2, alias);

		return query.executeQuery();
	}
}
