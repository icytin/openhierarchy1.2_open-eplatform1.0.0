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
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import se.unlogic.hierarchy.core.beans.BaseModuleDescriptor;
import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.hierarchy.core.daos.interfaces.ModuleAttributeDAO;
import se.unlogic.hierarchy.core.handlers.SimpleMutableAttributeHandler;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.dao.querys.ArrayListQuery;
import se.unlogic.standardutils.dao.querys.HashMapQuery;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.populators.StringEntryPopulator;

public class MySQLModuleAttributeDAO<Descriptor extends BaseModuleDescriptor> extends BaseDAO implements ModuleAttributeDAO<Descriptor> {

	public static final StringEntryPopulator POPULATOR = new StringEntryPopulator();

	private final String tableName;

	protected MySQLModuleAttributeDAO(DataSource ds, String tableName) {
		super(ds);
		this.tableName = tableName;
	}

	@Override
	public void set(Descriptor moduleDescriptor) throws SQLException {

		TransactionHandler transactionHandler = null;

		try{
			transactionHandler = new TransactionHandler(dataSource);
			set(moduleDescriptor, transactionHandler);
			transactionHandler.commit();
		}finally{

			TransactionHandler.autoClose(transactionHandler);
		}
	}

	@Override
	public void set(Descriptor moduleDescriptor, TransactionHandler transactionHandler) throws SQLException {

		UpdateQuery deleteQuery = transactionHandler.getUpdateQuery("DELETE FROM " + tableName + " WHERE moduleID = ?");

		deleteQuery.setInt(1, moduleDescriptor.getModuleID());

		deleteQuery.executeUpdate();

		if (moduleDescriptor.getAttributeHandler() != null && !moduleDescriptor.getAttributeHandler().isEmpty()) {

			for (Entry<String,String> entry : moduleDescriptor.getAttributeHandler().getAttributeMap().entrySet()) {

				UpdateQuery updateQuery = transactionHandler.getUpdateQuery("INSERT INTO " + tableName + " VALUES (?,?,?)");

				updateQuery.setInt(1, moduleDescriptor.getModuleID());
				updateQuery.setString(2, entry.getKey());
				updateQuery.setString(3, entry.getValue());

				updateQuery.executeUpdate();
			}
		}
	}

	@Override
	public void getAttributeHandler(Descriptor moduleDescriptor, Connection connection) throws SQLException {

		HashMapQuery<String, String> query = new HashMapQuery<String, String>(connection, false, "SELECT name, value FROM " + tableName + " WHERE moduleID = ?", POPULATOR);

		query.setInt(1, moduleDescriptor.getModuleID());

		HashMap<String, String> attributeMap = query.executeQuery();

		if (attributeMap == null) {
			moduleDescriptor.setAttributeHandler(new SimpleMutableAttributeHandler(255, 255));
			return;
		}

		moduleDescriptor.setAttributeHandler(new SimpleMutableAttributeHandler(attributeMap, 255, 255));
	}
	
	public List<Integer> getModulesIDsByAttribute(String name, String value) throws SQLException{
		
		ArrayListQuery<Integer> query = new ArrayListQuery<Integer>(dataSource, true, "SELECT moduleID FROM " + tableName + " WHERE name = ? and `value` = ?", IntegerPopulator.getPopulator());
		
		query.setString(1, name);
		query.setString(2, value);
		
		return query.executeQuery();
	}
}
