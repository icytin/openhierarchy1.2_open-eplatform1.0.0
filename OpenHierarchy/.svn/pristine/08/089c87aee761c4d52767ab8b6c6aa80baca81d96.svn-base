package se.unlogic.hierarchy.core.daos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import se.unlogic.standardutils.dao.TransactionHandler;


public interface ModuleAttributeDAO<T> {

	public void set(T descriptor) throws SQLException;

	public void set(T descriptor, TransactionHandler transactionHandler) throws SQLException;

	public void getAttributeHandler(T descriptor, Connection connection) throws SQLException;
}
