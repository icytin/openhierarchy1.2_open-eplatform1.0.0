package com.projecttemplate.populators;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.projecttemplate.beans.Example;

import se.unlogic.standardutils.dao.BeanResultSetPopulator;

public class ExamplePopulator implements BeanResultSetPopulator<Example> {

  @Override
  public Example populate(ResultSet rs) throws SQLException {
    Example example = new Example();
    
    example.setId(rs.getInt("id"));
    example.setName(rs.getString("name"));
    
    return null;
  }

}
