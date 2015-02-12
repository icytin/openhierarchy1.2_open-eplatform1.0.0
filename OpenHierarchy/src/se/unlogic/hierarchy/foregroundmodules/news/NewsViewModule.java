/*******************************************************************************
 * Copyright (c) 2010 Robert "Unlogic" Olofsson (unlogic@unlogic.se).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 ******************************************************************************/
package se.unlogic.hierarchy.foregroundmodules.news;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import se.unlogic.hierarchy.backgroundmodules.SimpleBackgroundModule;
import se.unlogic.hierarchy.core.beans.LinkTag;
import se.unlogic.hierarchy.core.beans.ScriptTag;
import se.unlogic.hierarchy.core.beans.SimpleBackgroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.interfaces.BackgroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.BackgroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.populators.UserQueryPopulator;
import se.unlogic.hierarchy.core.populators.UserTypePopulator;
import se.unlogic.hierarchy.core.utils.AccessUtils;
import se.unlogic.hierarchy.core.utils.BaseFileAccessValidator;
import se.unlogic.hierarchy.core.utils.ModuleUtils;
import se.unlogic.standardutils.dao.SimpleAnnotatedDAOFactory;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.standardutils.xsl.XSLVariableReader;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class NewsViewModule extends SimpleBackgroundModule {

	protected Logger log = Logger.getLogger(this.getClass());

	protected CopyOnWriteArrayList<News> newsCache = new CopyOnWriteArrayList<News>();

	private NewsDAO newsDAO;

	private String fileProxyModuleAlias;
	
	private Integer nrOfNewsToShow;

	private List<ScriptTag> scripts;
	private List<LinkTag> links;	
	
	protected boolean displayHeader = true;
	protected boolean displayMetadata = true;
	
	@Override
	public void init(BackgroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {
		
		super.init(moduleDescriptor, sectionInterface, dataSource);

		checkSettings();
	}

	@Override
	public void update(BackgroundModuleDescriptor moduleDescriptor, DataSource dataSource) throws Exception {
		
		super.update(moduleDescriptor, dataSource);
		
		checkSettings();
	}

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		this.dataSource = dataSource;

		this.newsDAO = new NewsDAO(dataSource, new SimpleAnnotatedDAOFactory(dataSource), new UserTypePopulator(sectionInterface.getSystemInterface().getUserHandler(), false, false), UserQueryPopulator.POPULATOR);

		this.cacheNews();
	}	
	
	protected void checkSettings() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, URISyntaxException{
		
		XSLVariableReader variableReader = ModuleUtils.getXSLVariableReader(moduleDescriptor, sectionInterface.getSystemInterface());
		
		if(variableReader != null){
			
			this.scripts = ModuleUtils.getScripts(variableReader, sectionInterface, "b",moduleDescriptor);
			this.links = ModuleUtils.getLinks(variableReader, sectionInterface, "b", moduleDescriptor);
		}	
		
		this.fileProxyModuleAlias = this.moduleDescriptor.getMutableSettingHandler().getString("newsFileProxyModuleAlias");
		this.nrOfNewsToShow = this.moduleDescriptor.getMutableSettingHandler().getInt("nrOfNewsToShow");
		
		this.displayHeader = this.moduleDescriptor.getMutableSettingHandler().getPrimitiveBoolean("displayHeader");
		this.displayMetadata = this.moduleDescriptor.getMutableSettingHandler().getPrimitiveBoolean("displayMetadata");
	}
	
	protected synchronized void cacheNews() throws SQLException {

		log.info("Caching news in section " + this.sectionInterface.getSectionDescriptor());

		if(!this.newsCache.isEmpty()){

			this.newsCache.clear();
		}

		List<News> newsList = this.newsDAO.getEnabledNewsList(this.sectionInterface.getSectionDescriptor().getSectionID());

		if(newsList != null){

			for(News news : newsList){

				log.debug("Caching news " + news);

				this.newsCache.add(news);
			}

			// Sort
			this.sortCache();

			log.info( "Cached " + newsList.size() + " news in section " + this.sectionInterface.getSectionDescriptor());

		} else{

			log.info("Cached 0 news in section " + this.sectionInterface.getSectionDescriptor());
		}
	}	
	
	public List<News> getCache() {
		return this.newsCache;
	}

	@Override
	public void unload() {}

	@Override
	public BackgroundModuleResponse processRequest(HttpServletRequest req, User user, URIParser uriParser) throws Exception {

		if(this.sectionInterface.getSectionCache().getEntry(uriParser.get(0)) != null){
			return null;
		}
		
		Document doc = XMLUtils.createDomDocument();
		Element documentElement = doc.createElement("Document");

		// Add request info
		documentElement.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));

		Element newsElement = doc.createElement("news");
		
		String text;
		int counter = 0;
		for(News cachedNews : this.newsCache) {
			
			if(this.nrOfNewsToShow != null && ++counter > this.nrOfNewsToShow) {
				break;
			}
			
			text = this.setAbsoluteFileUrls(cachedNews, req, uriParser);
			newsElement.appendChild(cachedNews.toXML(doc, text));
		}

		documentElement.appendChild(newsElement);
		
		XMLUtils.appendNewElement(doc, documentElement, "displayHeader", displayHeader);
		XMLUtils.appendNewElement(doc, documentElement, "displayMetadata", displayMetadata);
		
		ForegroundModuleDescriptor newsAdminDescriptor = NewsAdminModule.getNewsAdminModule(this.dataSource);

		SectionInterface newsAdminSection;
		
		if (newsAdminDescriptor != null && AccessUtils.checkRecursiveModuleAccess(user, newsAdminDescriptor, systemInterface) && (newsAdminSection = systemInterface.getSectionInterface(newsAdminDescriptor.getSectionID())) != null) {

			Element adminModuleElement = doc.createElement("AdminModule");
			documentElement.appendChild(adminModuleElement);
			
			adminModuleElement.appendChild(newsAdminDescriptor.toXML(doc));
			adminModuleElement.appendChild(newsAdminSection.getSectionDescriptor().toXML(doc));
		}			

		doc.appendChild(documentElement);
		
		SimpleBackgroundModuleResponse moduleResponse = new SimpleBackgroundModuleResponse(doc);
		
		if(scripts != null){
			moduleResponse.addScripts(scripts);
		}
		
		if(links != null){
			moduleResponse.addLinks(links);
		}
		
		return moduleResponse;
	}

	private String getAbsoluteFileURL(News news, HttpServletRequest req, URIParser uriParser) {
		return req.getContextPath() + "/" + this.fileProxyModuleAlias + "/" + news.getSectionID() + "/" + news.getNewsID();
	}

	public String setAbsoluteFileUrls(News news, HttpServletRequest req, URIParser uriParser) {

		String text = news.getText();

		String absoluteFileURL = this.getAbsoluteFileURL(news, req, uriParser);

		for(String attribute : BaseFileAccessValidator.TAG_ATTRIBUTES){

			text = text.replace(attribute + "=\"" + News.RELATIVE_PATH_MARKER, attribute + "=\"" + absoluteFileURL);
			text = text.replace(attribute + "='" + News.RELATIVE_PATH_MARKER, attribute + "='" + absoluteFileURL);
		}

		return text;
	}

	protected synchronized void addNews(News news){

		if(this.newsCache.contains(news)){

			log.warn("News " + news + " is already cached in section " + this.sectionInterface.getSectionDescriptor() + ", doing an update instead");

			this.updateNews(news);

			return;

		} else {

			log.debug("Adding news " + news + " to noews cache in section " + this.sectionInterface.getSectionDescriptor());

			this.newsCache.add(news);

			// Sort
			this.sortCache();
		}
	}

	/**
	 * Updates a {@link News} news in the modules news cache.
	 * 
	 * @param news
	 */	
	protected synchronized void updateNews(News news){

		if(this.newsCache.contains(news)){

			for(News cachedNews : this.newsCache){

				if(cachedNews.equals(news)){

					News oldNews = cachedNews;

					this.newsCache.remove(cachedNews);
					this.newsCache.add(news);

					log.debug("Updated news " + oldNews + " in news cache with news " + news + " in section " + this.sectionInterface.getSectionDescriptor());

					break;
				}
			}

		} else{

			log.warn("Unable to find previously cached copy of news " + news + " in section " + this.sectionInterface.getSectionDescriptor() + ", doing an add instead");

			this.addNews(news);
		}

		// Sort
		this.sortCache();
	}

	/**
	 * Removes a {@link News} from the modules news cache.
	 * 
	 * @param news
	 */	
	protected synchronized void removeNews(News news){

		if(!this.newsCache.remove(news)) {
			log.warn("Unable to find cached copy of news " + news + " in section " + this.sectionInterface.getSectionDescriptor());			
		}

		// Sort
		this.sortCache();

	}

	protected BackgroundModuleDescriptor getBackgroundModuleDescriptor(){
		return this.moduleDescriptor;
	}

	protected SectionInterface getSectionInterface(){
		return this.sectionInterface;
	}

	private void sortCache() {
		List<News> shallowCopy = new ArrayList<News>(this.newsCache);
		Collections.sort(shallowCopy, News.getDefaultComparator());
		this.newsCache = new CopyOnWriteArrayList<News>(shallowCopy);		
	}

}
