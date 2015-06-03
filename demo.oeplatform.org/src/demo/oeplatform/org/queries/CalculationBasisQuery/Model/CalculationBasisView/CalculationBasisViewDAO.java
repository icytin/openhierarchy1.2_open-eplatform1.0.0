package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisView;

import java.sql.SQLException;

import javax.sql.DataSource;

import se.unlogic.hierarchy.core.daos.BaseDAO;
import se.unlogic.standardutils.dao.querys.ObjectQuery;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.populators.StringPopulator;

public class CalculationBasisViewDAO extends BaseDAO {
	private static final String TABLE_NAME = "calculation_basis_views";
	private static final StringPopulator STRINGPOPULATOR = new StringPopulator();
	private static final IntegerPopulator INTPOPULATOR = new IntegerPopulator();
	private static final CalculationBasisViewPopulator VIEWPOPULATOR = new CalculationBasisViewPopulator();

	/**
	 * Konstruktor
	 * 
	 * @param dataSource
	 *            Datakälla
	 */
	public CalculationBasisViewDAO(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Lägger till en ...
	 * 
	 * @param ...
	 * @throws SQLException
	 */
	public void add(CalculationBasisView calculationBasisView) throws SQLException {
		String insertSql = "INSERT INTO " + TABLE_NAME + " (queryId,html) VALUES (?,?)";
		UpdateQuery q = new UpdateQuery(this.dataSource, false, insertSql);
		q.setInt(1, calculationBasisView.getQueryId());
		q.setString(2, calculationBasisView.getHtml());
		q.executeUpdate();
		
		String sql = "SELECT MAX(viewID) FROM " + TABLE_NAME;
		ObjectQuery<Integer> q2 = new ObjectQuery<Integer>(this.dataSource,
				true, sql, INTPOPULATOR);
		Integer insertedId = q2.executeQuery();
		calculationBasisView.setViewId(insertedId);
	}
	
	public void update(CalculationBasisView calculationBasisView) throws SQLException {
		String updateSql = "UPDATE " + TABLE_NAME + " SET html=? WHERE viewId = ?";
		UpdateQuery q = new UpdateQuery(this.dataSource, false, updateSql);
		q.setString(1, calculationBasisView.getHtml());
		q.setInt(2, calculationBasisView.getViewId());
		q.executeUpdate();
	}

	public void remove(String id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE viewId = " + id;
		UpdateQuery q = new UpdateQuery(this.dataSource, true, sql);
		q.executeUpdate();
	}

	public CalculationBasisView getByQueryId(int queryId) throws SQLException {	
		String sql = "SELECT viewId, queryId, html FROM " + TABLE_NAME
				+ " WHERE queryId = " + queryId;
	    
	    ObjectQuery<CalculationBasisView> query = new ObjectQuery<CalculationBasisView>(this.dataSource, true, sql, VIEWPOPULATOR);
	    
	    return query.executeQuery();
	}
}
