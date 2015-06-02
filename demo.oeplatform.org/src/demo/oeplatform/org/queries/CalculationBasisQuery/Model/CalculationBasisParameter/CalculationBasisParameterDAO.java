package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.dao.querys.ArrayListQuery;
import se.unlogic.standardutils.dao.querys.ObjectQuery;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.populators.StringPopulator;

public class CalculationBasisParameterDAO extends BaseDAO{
  
  /**
   * Konstruktor
   * 
   * @param dataSource Datakälla
   */
  public CalculationBasisParameterDAO(DataSource dataSource)
  {
    super(dataSource);
  }
  
  /**
   * Lägger till en ...
   * 
   * @param ...
   * @throws SQLException
   */
  public void add(CalculationBasisParameter calculationBasisParameter) throws SQLException
  {
    /*String sql = "SELECT MAX(id) FROM issue";
    
    ObjectQuery<String> query = new ObjectQuery<String>(this.dataSource, true, sql, STRINGPOPULATOR);
    
    String maxID = query.executeQuery();
    
    issue.setRequestID(this.createRequestAPID(maxID));
    
    String sql2 = "INSERT INTO issue VALUES (null,?,?,?,null,?,?,?,?,?,?,null,?,null)";
    
    UpdateQuery query2 = new UpdateQuery(this.dataSource, false, sql2);
    query2.setString(1, issue.getRequestID());
    query2.setString(2, issue.getType());
    query2.setString(3, issue.getSummary());
    query2.setString(4, issue.getRequestType());
    query2.setString(5, issue.getStatus());
    query2.setString(6, issue.getSystem());
    query2.setString(7, issue.getOriginator());
    query2.setString(8, issue.getAdministrator());
    query2.setString(9, issue.getDescription());
    query2.setString(10, issue.getCreatedDate());
    
    query2.executeUpdate();*/
  }
}