package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisParameter;

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
public class CalculationBasisParameterTablePopulator implements BeanResultSetPopulator<CalculationBasisParameter>
{
  private static final Logger log = Logger.getLogger(CalculationBasisParameterTablePopulator.class);
  
  protected static SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  @Override
  public CalculationBasisParameter populate(ResultSet rs) throws SQLException
  {
	  CalculationBasisParameter parameter = new CalculationBasisParameter(
			  rs.getString("name"),
			  rs.getInt("queryID"),
			  0,
			  0,
			  rs.getString("value"),
			  rs.getString("description"));
	  Long refQueryIDLong = (Long)rs.getObject("refQueryID");
	  Integer refQueryIDInteger = refQueryIDLong != null ? refQueryIDLong.intValue() : null;
	  parameter.setRefQueryID(refQueryIDInteger);
	  
	  Long refSubQueryIDLong = (Long)rs.getObject("refSubQueryID");
	  Integer refSubQueryIDInteger = refSubQueryIDLong != null ? refSubQueryIDLong.intValue() : null;
	  parameter.setRefSubQueryID(refSubQueryIDInteger);
	  
      parameter.setParameterID(rs.getInt("parameterID"));
    return parameter;
  }
}