/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.foregroundmodules.news;

import java.lang.ref.WeakReference;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.FCKConnector;
import se.unlogic.hierarchy.core.utils.ModuleUtils;
import se.unlogic.hierarchy.core.utils.SimpleFileAccessValidator;
import se.unlogic.hierarchy.foregroundmodules.SimpleForegroundModule;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.webutils.http.URIParser;

public class NewsFileProxyModule extends SimpleForegroundModule {

	private FCKConnector connector;

	private WeakReference<NewsAdminModule> newsAdminModule;

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {
		super.init(moduleDescriptor, sectionInterface, dataSource);
		this.connector = new FCKConnector(moduleDescriptor.getMutableSettingHandler().getString("filestore"));
	}

	@Override
	public ForegroundModuleResponse processRequest(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		News news;
		NewsAdminModule newsAdminModule;

		if(this.newsAdminModule == null || (newsAdminModule = this.newsAdminModule.get()) == null) {

			log.debug("Finding instance of publication admin module");

			Entry<ForegroundModuleDescriptor,NewsAdminModule> moduleEntry = ModuleUtils.findForegroundModule(NewsAdminModule.class, true, null, true, this.systemInterface.getRootSection());

			if(moduleEntry != null){

				newsAdminModule = moduleEntry.getValue();
				this.newsAdminModule = new WeakReference<NewsAdminModule>(newsAdminModule);

			}else{

				newsAdminModule = null;
				this.newsAdminModule = null;
			}
		}

		if (newsAdminModule != null && uriParser.size() > 2 && NumberUtils.isNumber(uriParser.get(1)) && NumberUtils.isInt(uriParser.get(2)) && (news = this.getNews(newsAdminModule.getViewerModuleMap().get(Integer.parseInt(uriParser.get(1))), Integer.parseInt(uriParser.get(2)))) != null) {
			this.connector.processFileRequest(req, res, user, uriParser, moduleDescriptor, sectionInterface, 3, new SimpleFileAccessValidator(News.RELATIVE_PATH_MARKER,news.getUnescapedText()));
			return null;
		}

		throw new URINotFoundException(uriParser);
	}

	private News getNews(NewsViewModule viewModule, int publicationID) {

		if(viewModule == null){

			return null;
		}

		for(News publication : viewModule.newsCache) {

			if(publication.getNewsID().equals(publicationID)) {
				return publication;
			}
		}

		return null;
	}
}
