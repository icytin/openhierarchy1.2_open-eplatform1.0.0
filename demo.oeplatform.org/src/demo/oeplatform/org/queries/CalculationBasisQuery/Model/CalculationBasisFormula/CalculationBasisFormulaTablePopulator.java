package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisFormula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import se.unlogic.standardutils.dao.BeanResultSetPopulator;


/**
 * Populatorklass för ...
 * 
 * @author ...
 * @see <a href="www.cgi.com">www.cgi.com</a>
 */
public class CalculationBasisFormulaTablePopulator implements BeanResultSetPopulator<CalculationBasisFormula>
{
  private static final Logger log = Logger.getLogger(CalculationBasisFormulaTablePopulator.class);
  
  protected static SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  @Override
  public CalculationBasisFormula populate(ResultSet rs) throws SQLException
  {
	  CalculationBasisFormula parameter = new CalculationBasisFormula(
			  rs.getInt("queryId"),
			  rs.getString("name"),
			  rs.getString("formula"),
			  rs.getString("description"));

      parameter.setFormulaId(rs.getInt("formulaId"));
    return parameter;
  }
}