package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisView;

	import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import se.unlogic.standardutils.dao.BeanResultSetPopulator;

	public class CalculationBasisViewPopulator implements BeanResultSetPopulator<CalculationBasisView>
	{
	  private static final Logger log = Logger.getLogger(CalculationBasisViewPopulator.class);
	  
	  protected static SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
	  @Override
	  public CalculationBasisView populate(ResultSet rs) throws SQLException
	  {
		  CalculationBasisView view = new CalculationBasisView(rs.getInt("queryId"),rs.getString("html"));
		  view.setViewId(rs.getInt("viewId"));
	      return view;
	  }
}