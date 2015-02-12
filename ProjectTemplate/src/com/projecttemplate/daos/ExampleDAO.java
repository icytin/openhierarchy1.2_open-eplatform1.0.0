package com.projecttemplate.daos;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.projecttemplate.beans.Example;
import com.projecttemplate.populators.ExamplePopulator;

import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.standardutils.dao.querys.ArrayListQuery;

public class ExampleDAO extends BaseDAO {
  private static ExamplePopulator POPULATOR = new ExamplePopulator();

  public ExampleDAO(DataSource dataSource) {
    super(dataSource);
  }
  
  public ArrayList<Example> get() throws SQLException
  {
    ArrayListQuery<Example> query = new ArrayListQuery<Example>(dataSource, true, "SELECT id, name FROM examples", POPULATOR);
    
    return query.executeQuery();
  }
}