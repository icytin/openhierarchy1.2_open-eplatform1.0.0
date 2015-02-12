/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.foregroundmodules.news;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import se.unlogic.hierarchy.core.populators.UserQueryPopulator;
import se.unlogic.hierarchy.core.populators.UserTypePopulator;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.AnnotatedDAOFactory;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryParameterFactory;
import se.unlogic.standardutils.dao.querys.UpdateQuery;
import se.unlogic.standardutils.db.DBUtils;
import se.unlogic.standardutils.populators.annotated.AnnotatedResultSetPopulator;
import se.unlogic.standardutils.string.StringUtils;

public class NewsDAO extends AnnotatedDAO<News> {

	protected Logger log = Logger.getLogger(this.getClass());

	private QueryParameterFactory<News, Integer> sectionIDParamFactory;
	private QueryParameterFactory<News, Integer> publicationIDParamFactory;
	private QueryParameterFactory<News, Boolean> enabledParamFactory;

	public NewsDAO(DataSource dataSource, AnnotatedDAOFactory daoFactory, UserTypePopulator userTypePopulator, UserQueryPopulator userQueryPopulator) throws SQLException, IOException {

		super(dataSource, News.class, daoFactory, new AnnotatedResultSetPopulator<News>(News.class, userTypePopulator), userQueryPopulator);

		sectionIDParamFactory = this.getParamFactory("sectionID", Integer.class);
		publicationIDParamFactory = this.getParamFactory("newsID", Integer.class);
		enabledParamFactory = this.getParamFactory("enabled", boolean.class);

		if (!DBUtils.tableExists(dataSource, this.getTableName())) {

			log.info("Creating " + this.getTableName() + " table in datasource " + dataSource);

			String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/NewsTable.sql"));

			new UpdateQuery(dataSource.getConnection(), true, sql).executeUpdate();
		}

	}

	public boolean sectionHasEnabledNews(Integer sectionID) throws SQLException {


		HighLevelQuery<News> query = new HighLevelQuery<News>();

		query.addParameter(sectionIDParamFactory.getParameter(sectionID));
		query.addParameter(enabledParamFactory.getParameter(true));

		return this.getBoolean(query);
	}

	public List<News> getNewsList(Integer sectionID) throws SQLException {

		HighLevelQuery<News> query = new HighLevelQuery<News>();

		query.addParameter(sectionIDParamFactory.getParameter(sectionID));

		return this.getAll(query);
	}

	public List<News> getEnabledNewsList(Integer sectionID) throws SQLException {

		HighLevelQuery<News> query = new HighLevelQuery<News>();

		query.addParameter(sectionIDParamFactory.getParameter(sectionID));
		query.addParameter(enabledParamFactory.getParameter(true));

		return this.getAll(query);
	}

	public News getNews(int newsId) throws SQLException {

		HighLevelQuery<News> query = new HighLevelQuery<News>();

		query.addParameter(publicationIDParamFactory.getParameter(newsId));

		return this.get(query);
	}


}
