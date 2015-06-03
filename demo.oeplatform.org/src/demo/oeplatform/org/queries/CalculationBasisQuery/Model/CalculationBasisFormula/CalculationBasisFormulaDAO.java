package demo.oeplatform.org.queries.CalculationBasisQuery.Model.CalculationBasisFormula;



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

public class CalculationBasisFormulaDAO extends BaseDAO {
	private static final String TABLE_NAME = "calculation_basis_formulas";
	private static final StringPopulator STRINGPOPULATOR = new StringPopulator();
	private static final IntegerPopulator INTPOPULATOR = new IntegerPopulator();
	private static final CalculationBasisFormulaTablePopulator TABLEPOPULATOR = new CalculationBasisFormulaTablePopulator();

	/**
	 * Konstruktor
	 * 
	 * @param dataSource
	 *            Datakälla
	 */
	public CalculationBasisFormulaDAO(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * Lägger till en ...
	 * 
	 * @param ...
	 * @throws SQLException
	 */
	public void add(CalculationBasisFormula formula) throws SQLException {
		String insertSql = "INSERT INTO " + TABLE_NAME + " (queryId, name, formula, description) VALUES (?,?,?,?)";
		UpdateQuery q = new UpdateQuery(this.dataSource, false, insertSql);
		q.setInt(1, formula.getQueryId());
		q.setString(2, formula.getName());
		q.setString(3, formula.getFormula());
		q.setString(4, formula.getDescription());
		q.executeUpdate();

		String sql = "SELECT MAX(formulaID) FROM " + TABLE_NAME;
		ObjectQuery<Integer> q2 = new ObjectQuery<Integer>(this.dataSource,
				true, sql, INTPOPULATOR);
		Integer insertedId = q2.executeQuery();
		formula.setFormulaId(insertedId);
	}

	public void remove(String id) throws SQLException {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE formulaID = " + id;
		UpdateQuery q = new UpdateQuery(this.dataSource, true, sql);
		q.executeUpdate();

	}

	public ArrayList<CalculationBasisFormula> listByQueryId(String queryId) throws SQLException {
		String sql = "SELECT formulaId, queryId, name, formula, description FROM " + TABLE_NAME
				+ " WHERE queryId = " + queryId;
	    
	    ArrayListQuery<CalculationBasisFormula> query = new ArrayListQuery<CalculationBasisFormula>(this.dataSource, true, sql, TABLEPOPULATOR);
	    
	    return query.executeQuery();
	}
}
