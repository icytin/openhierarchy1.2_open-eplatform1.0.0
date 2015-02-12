package se.unlogic.hierarchy.foregroundmodules.minimaluser;

import java.lang.reflect.Field;
import java.util.List;

import javax.sql.DataSource;

import se.unlogic.hierarchy.foregroundmodules.userproviders.daos.AnnotatedUserDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.QueryParameterFactory;
import se.unlogic.standardutils.populators.BeanStringPopulator;
import se.unlogic.standardutils.populators.QueryParameterPopulator;


public class AnnotatedMinimalUserDAO extends AnnotatedUserDAO<MinimalUser> {

	public AnnotatedMinimalUserDAO(DataSource dataSource, Class<MinimalUser> beanClass, AnnotatedDAOFactory daoFactory, List<QueryParameterPopulator<?>> queryParameterPopulators, List<BeanStringPopulator<?>> typePopulators, String usergroupTableName, Field groupsRelation, String userAttributesTableName, Field attributesRelation) {

		super(dataSource, beanClass, daoFactory, queryParameterPopulators, typePopulators, usergroupTableName, groupsRelation, userAttributesTableName, attributesRelation);
	}

	@Override
	public <ParamType> QueryParameterFactory<MinimalUser, ParamType> getParamFactory(String fieldName, Class<ParamType> paramClass) {
		
		if(fieldName.equals("username") || fieldName.equals("password") || fieldName.equals("email"))  {
			
			return null;
		}
		
		return super.getParamFactory(fieldName, paramClass);
	}	
}
