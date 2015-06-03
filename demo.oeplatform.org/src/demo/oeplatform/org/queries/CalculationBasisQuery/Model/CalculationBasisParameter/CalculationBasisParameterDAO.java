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
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.populators.StringPopulator;

public class CalculationBasisParameterDAO extends BaseDAO{
    private static final String TABLE_NAME = "calculation_basis_parameters";  
	private static final StringPopulator STRINGPOPULATOR = new StringPopulator();
	private static final IntegerPopulator INTPOPULATOR = new IntegerPopulator();
	
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
	String insertSql = "INSERT INTO "+ TABLE_NAME +" (queryID,name,value,description,refQueryId,refSubQueryId) VALUES (?,?,?,?,?,?)";
    UpdateQuery q = new UpdateQuery(this.dataSource, false, insertSql);
    q.setInt(1, calculationBasisParameter.getQueryID());
    q.setString(2, calculationBasisParameter.getName());
    q.setString(3, calculationBasisParameter.getValue());
    q.setString(4, calculationBasisParameter.getDescription());
    /*if (calculationBasisParameter.getRefQueryID()==null)
    	q.setNull(5, java.sql.Types.INTEGER);
    else
    	q.setInt(5, calculationBasisParameter.getRefQueryID());*/
    q.setObject(5, calculationBasisParameter.getRefQueryID());
    q.setObject(6, calculationBasisParameter.getRefSubQueryID());
    q.executeUpdate();
    
    String sql = "SELECT MAX(parameterID) FROM "+ TABLE_NAME;
    ObjectQuery<Integer> q2 = new ObjectQuery<Integer>(this.dataSource, true, sql, INTPOPULATOR);
    Integer insertedId = q2.executeQuery();
    calculationBasisParameter.setParameterID(insertedId);
  }
}