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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.hierarchy.core.annotations.CheckboxSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.annotations.XSLVariable;
import se.unlogic.hierarchy.core.beans.Breadcrumb;
import se.unlogic.hierarchy.core.beans.SettingDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleBackgroundModuleDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.SimpleSectionDescriptor;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.daos.interfaces.SectionDAO;
import se.unlogic.hierarchy.core.enums.PathType;
import se.unlogic.hierarchy.core.enums.SystemStatus;
import se.unlogic.hierarchy.core.enums.URLType;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.handlers.SimpleSettingHandler;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.RootSectionInterface;
import se.unlogic.hierarchy.core.interfaces.SectionCacheListener;
import se.unlogic.hierarchy.core.interfaces.SectionDescriptor;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.populators.UserQueryPopulator;
import se.unlogic.hierarchy.core.populators.UserTypePopulator;
import se.unlogic.hierarchy.core.sections.Section;
import se.unlogic.hierarchy.core.utils.AccessUtils;
import se.unlogic.hierarchy.core.utils.BaseFileAccessValidator;
import se.unlogic.hierarchy.core.utils.FCKConnector;
import se.unlogic.hierarchy.core.utils.ModuleUtils;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.standardutils.collections.KeyAlreadyCachedException;
import se.unlogic.standardutils.collections.KeyNotCachedException;
import se.unlogic.standardutils.dao.SimpleAnnotatedDAOFactory;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.validation.PositiveStringIntegerValidator;
import se.unlogic.standardutils.validation.StringIntegerValidator;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;

public class NewsAdminModule extends AnnotatedForegroundModule implements SectionCacheListener {

	private static HashMap<DataSource, ForegroundModuleDescriptor> dataSourceInstanceMap = new HashMap<DataSource, ForegroundModuleDescriptor>();
	private static final ReentrantReadWriteLock mapLock = new ReentrantReadWriteLock();
	private static final Lock mapReadLock = mapLock.readLock();
	private static final Lock mapWriteLock = mapLock.writeLock();

	private ConcurrentHashMap<Integer, NewsViewModule> viewerModuleMap = new ConcurrentHashMap<Integer, NewsViewModule>();

	private NewsDAO newsDAO;
	private SectionDAO sectionDAO;
	private FCKConnector connector;
	private AnnotatedRequestPopulator<News> populator;
	private SimpleForegroundModuleDescriptor fileProxyModuleDescriptor;

	@ModuleSetting(allowsNull = true)
	protected String filestore;

	@ModuleSetting
	protected Integer diskThreshold = 100;

	@ModuleSetting
	@TextFieldSettingDescriptor(name="RAM threshold",description="Maximum size of files in KB to be buffered in RAM during file uploads. Files exceeding the threshold are written to disk instead.",required=true,formatValidator=PositiveStringIntegerValidator.class)
	protected Integer ramThreshold = 500;

	@ModuleSetting(allowsNull = true)
	protected String csspath;

	@ModuleSetting
	private String newsFileProxyModuleAlias = "fileproxy";

	@ModuleSetting
	private String newsFileProxyModuleName = "News file proxy";

	@ModuleSetting
	protected String newsViewModuleName = "News viewer";

	@ModuleSetting
	protected List<String> newsViewModuleAliases = Collections.singletonList("*");

	@ModuleSetting
	protected List<String> newsViewModuleSlots = new ArrayList<String>();

	@ModuleSetting
	protected String newsViewModuleXSLPathType = PathType.Classpath.toString();

	@XSLVariable
	@ModuleSetting
	protected String newsViewModuleXSLPath;

	@ModuleSetting
	protected Integer nrOfNewsToShow = null;

	@ModuleSetting
	@CheckboxSettingDescriptor(name="Display header",description="Display header text above news in view module")
	protected boolean displayHeader = false;

	@ModuleSetting
	@CheckboxSettingDescriptor(name="Display metadata",description="Display metadata below each news post in viewmodule")
	protected boolean displayMetadata = false;

	private void addInstanceToMap() {

		mapWriteLock.lock();
		try {
			dataSourceInstanceMap.put(this.dataSource, this.moduleDescriptor);
		} finally {
			mapWriteLock.unlock();
		}
	}

	private void removeInstanceFromMap() {

		mapWriteLock.lock();
		try {
			dataSourceInstanceMap.remove(this.dataSource);
		} finally {
			mapWriteLock.unlock();
		}
	}

	public static ForegroundModuleDescriptor getNewsAdminModule(DataSource dataSource) {

		mapReadLock.lock();
		try {
			return dataSourceInstanceMap.get(dataSource);
		} finally {
			mapReadLock.unlock();
		}
	}

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {

		super.init(moduleDescriptor, sectionInterface, dataSource);

		this.sectionDAO = this.sectionInterface.getSystemInterface().getCoreDaoFactory().getSectionDAO();
		this.connector = new FCKConnector(this.filestore, this.diskThreshold, this.ramThreshold);
		this.populator = new AnnotatedRequestPopulator<News>(News.class, new UserTypePopulator(this.sectionInterface.getSystemInterface().getUserHandler(), false, false));

		this.addInstanceToMap();

		//Add news view modules to all started sections that contain enabled news
		createViewModules(systemInterface.getRootSection(), true);

		// Create news file proxy module in root section
		createNewsProxyModule();

		systemInterface.addSectionCacheListener(this);

	}

	@Override
	public void update(ForegroundModuleDescriptor moduleDescriptor, DataSource dataSource) throws Exception {

		if (this.dataSource != dataSource) {
			this.sectionDAO = this.sectionInterface.getSystemInterface().getCoreDaoFactory().getSectionDAO();
		}

		super.update(moduleDescriptor, dataSource);
		this.connector.setDiskThreshold(this.diskThreshold);
		this.connector.setRamThreshold(this.ramThreshold);
		this.connector.setFilestorePath(filestore);

		this.addInstanceToMap();

		//Update view modules
		updateViewModules(systemInterface.getRootSection());
	}

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		SimpleAnnotatedDAOFactory daoFactory = new SimpleAnnotatedDAOFactory(dataSource);

		this.newsDAO = new NewsDAO(dataSource, daoFactory, new UserTypePopulator(this.sectionInterface.getSystemInterface().getUserHandler(), false, false), UserQueryPopulator.POPULATOR);
	}

	@Override
	public void unload() {

		this.removeInstanceFromMap();

		if (systemInterface.getSystemStatus() != SystemStatus.STOPPING) {

			unloadViewModules();

			// Unload news file proxy module in root section
			try {
				systemInterface.getRootSection().getForegroundModuleCache().unload(fileProxyModuleDescriptor);
			} catch (Exception e) {
				log.error("Error unloding file proxy module " + fileProxyModuleDescriptor + " while unloading module " + moduleDescriptor, e);
			}
		}

		systemInterface.removeSectionCacheListener(this);

	}

	private void createViewModules(SectionInterface sectionInterface, boolean recursive) {

		try {
			if (this.newsDAO.sectionHasEnabledNews(sectionInterface.getSectionDescriptor().getSectionID())) {

				log.info("Adding news view module to section " + sectionInterface.getSectionDescriptor());

				SimpleBackgroundModuleDescriptor descriptor = new SimpleBackgroundModuleDescriptor();

				descriptor.setAliases(this.newsViewModuleAliases);
				descriptor.setXslPathType(PathType.valueOf(this.newsViewModuleXSLPathType));
				descriptor.setXslPath(newsViewModuleXSLPath);
				descriptor.setName(this.newsViewModuleName);
				descriptor.setSlots(this.newsViewModuleSlots);
				descriptor.setClassname(NewsViewModule.class.getName());

				descriptor.setAdminAccess(true);
				descriptor.setUserAccess(true);
				descriptor.setAnonymousAccess(true);

				Map<String, List<String>> settings = this.moduleDescriptor.getMutableSettingHandler().getMap();

				settings.put(NewsAdminModule.class.toString(), Collections.singletonList(moduleDescriptor.getModuleID().toString()));

				descriptor.setMutableSettingHandler(new SimpleSettingHandler(settings));
				descriptor.setEnabled(true);
				descriptor.setSectionID(sectionInterface.getSectionDescriptor().getSectionID());
				descriptor.setStaticContentPackage("staticcontent");

				descriptor.setDataSourceID(this.moduleDescriptor.getDataSourceID());

				NewsViewModule newsViewModule = (NewsViewModule) sectionInterface.getBackgroundModuleCache().cache(descriptor);
				//newsViewModule.setNrOfNewsToShow(this.newsViewModuleNewsPerPage);
				//newsViewModule.setFileProxyModuleAlias(this.newsFileProxyModuleAlias);

				this.viewerModuleMap.put(sectionInterface.getSectionDescriptor().getSectionID(), newsViewModule);
			}

		} catch (Exception e) {

			log.error("Error adding news view module to section " + sectionInterface.getSectionDescriptor(), e);
		}

		if (recursive) {

			for (SectionInterface childSection : sectionInterface.getSectionCache().getSectionMap().values()) {

				createViewModules(childSection, recursive);
			}
		}
	}

	private void createNewsProxyModule() {

		try {

			SectionInterface sectionInterface = this.sectionInterface.getSystemInterface().getRootSection();

			log.info("Adding news file proxy module to section " + sectionInterface);

			fileProxyModuleDescriptor = new SimpleForegroundModuleDescriptor();

			fileProxyModuleDescriptor.setAlias(this.newsFileProxyModuleAlias);
			fileProxyModuleDescriptor.setName(this.newsFileProxyModuleName);
			fileProxyModuleDescriptor.setDescription(this.newsFileProxyModuleName);
			fileProxyModuleDescriptor.setClassname(NewsFileProxyModule.class.getName());

			fileProxyModuleDescriptor.setAdminAccess(true);
			fileProxyModuleDescriptor.setUserAccess(true);
			fileProxyModuleDescriptor.setAnonymousAccess(true);

			Map<String, List<String>> settings = this.moduleDescriptor.getMutableSettingHandler().getMap();

			settings.put(NewsFileProxyModule.class.toString(), Collections.singletonList(moduleDescriptor.getModuleID().toString()));

			fileProxyModuleDescriptor.setMutableSettingHandler(new SimpleSettingHandler(settings));
			fileProxyModuleDescriptor.setVisibleInMenu(false);
			fileProxyModuleDescriptor.setEnabled(true);
			fileProxyModuleDescriptor.setSectionID(sectionInterface.getSectionDescriptor().getSectionID());
			fileProxyModuleDescriptor.setStaticContentPackage("");

			fileProxyModuleDescriptor.setDataSourceID(this.moduleDescriptor.getDataSourceID());

			sectionInterface.getForegroundModuleCache().cache(fileProxyModuleDescriptor);

		} catch (Exception e) {

			log.error("Error adding news file proxy module to section " + sectionInterface.getSectionDescriptor(), e);
		}

	}

	private void updateViewModules(RootSectionInterface rootSection) {

		for (NewsViewModule newsViewModule : viewerModuleMap.values()) {

			try {
				SimpleBackgroundModuleDescriptor descriptor = (SimpleBackgroundModuleDescriptor) newsViewModule.getBackgroundModuleDescriptor();

				descriptor.setAliases(this.newsViewModuleAliases);
				descriptor.setXslPathType(PathType.valueOf(this.newsViewModuleXSLPathType));
				descriptor.setXslPath(newsViewModuleXSLPath);
				descriptor.setName(this.newsViewModuleName);
				descriptor.setSlots(this.newsViewModuleSlots);
				descriptor.setDataSourceID(this.moduleDescriptor.getDataSourceID());

				Map<String, List<String>> settings = this.moduleDescriptor.getMutableSettingHandler().getMap();

				settings.put(NewsAdminModule.class.toString(), Collections.singletonList(this.moduleDescriptor.getModuleID().toString()));

				descriptor.setMutableSettingHandler(new SimpleSettingHandler(settings));

				newsViewModule.getSectionInterface().getBackgroundModuleCache().update(descriptor);

			} catch (KeyNotCachedException e) {

				log.debug("Unable to update news view module in section " + sectionInterface.getSectionDescriptor() + ", module not cached", e);
				this.viewerModuleMap.remove(sectionInterface.getSectionDescriptor().getSectionID());

			} catch (Exception e) {

				log.error("Error updating news view module in section " + sectionInterface.getSectionDescriptor(), e);
			}
		}
	}

	private void unloadViewModules() {

		for (NewsViewModule newsViewModule : viewerModuleMap.values()) {

			try {
				newsViewModule.getSectionInterface().getBackgroundModuleCache().unload(newsViewModule.getBackgroundModuleDescriptor());

			} catch (KeyNotCachedException e) {

				log.debug("Unable to unload news view module in section " + newsViewModule.getSectionInterface().getSectionDescriptor() + ", module not cached", e);

			} catch (Exception e) {

				log.error("Error unloading news view module in section " + newsViewModule.getSectionInterface().getSectionDescriptor(), e);
			}
		}

	}

	public ConcurrentHashMap<Integer, NewsViewModule> getViewerModuleMap() {
		return viewerModuleMap;
	}

	@WebPublic(alias="show")
	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		Document doc = this.createDocument(req, uriParser);

		SimpleSectionDescriptor rootSection = this.sectionDAO.getRootSection(true);

		Element sectionsElement = doc.createElement("sections");
		doc.getFirstChild().appendChild(sectionsElement);

		this.appendSection(sectionsElement, doc, rootSection, true);

		return new SimpleForegroundModuleResponse(doc, moduleDescriptor.getName(), this.getDefaultBreadcrumb());
	}

	private void appendSection(Element parentSection, Document doc, SimpleSectionDescriptor simpleSectionDescriptor, boolean includeNews) throws SQLException {

		Element sectionElement = simpleSectionDescriptor.toXML(doc);
		parentSection.appendChild(sectionElement);

		if (!this.viewerModuleMap.containsKey(simpleSectionDescriptor.getSectionID())) {
			sectionElement.appendChild(doc.createElement("noNewsModuleWarning"));
		}

		if (includeNews) {
			List<News> newsList = this.newsDAO.getNewsList(simpleSectionDescriptor.getSectionID());

			if (newsList != null) {
				Element newsElement = doc.createElement("news");
				sectionElement.appendChild(newsElement);

				for (News news : newsList) {
					newsElement.appendChild(news.toXML(doc));
				}
			}
		}

		if (simpleSectionDescriptor.getSubSectionsList() != null) {
			Element subSectionsElement = doc.createElement("subsections");
			sectionElement.appendChild(subSectionsElement);

			for (SimpleSectionDescriptor subSectionBean : simpleSectionDescriptor.getSubSectionsList()) {
				this.appendSection(subSectionsElement, doc, subSectionBean, includeNews);
			}
		}
	}

	public Document createDocument(HttpServletRequest req, URIParser uriParser) {

		Document doc = XMLUtils.createDomDocument();
		Element document = doc.createElement("document");
		document.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		document.appendChild(this.moduleDescriptor.toXML(doc));
		doc.appendChild(document);
		return doc;
	}

	@WebPublic
	public SimpleForegroundModuleResponse add(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws KeyNotCachedException, KeyAlreadyCachedException, SQLException, URINotFoundException, IOException {

		SimpleSectionDescriptor section = null;

		// Check that a valid sectionID is provided
		if (uriParser.size() != 3 || !NumberUtils.isInt(uriParser.get(2)) || (section = this.sectionDAO.getSection(Integer.parseInt(uriParser.get(2)), false)) == null) {

			// No or invalid sectionID provided
			throw new URINotFoundException(uriParser);

		} else {

			// Valid sectionID provided
			ValidationException validationException = null;

			if (req.getMethod().equalsIgnoreCase("POST")) {
				try {
					// Populate news
					News news = populator.populate(req);

					news.setPoster(user);
					news.setAdded(new Timestamp(System.currentTimeMillis()));

					log.info("User " + user + " adding news " + news + " in section " + section);

					// Replace absolute file paths
					this.removeAbsoluteFileUrls(news, uriParser);

					// Set sectionID
					news.setSectionID(section.getSectionID());

					// Save the news
					newsDAO.add(news);

					newsAdded(news);

					// Redirect user
					res.sendRedirect(getModuleURI(req));
					return null;

				} catch (ValidationException e) {
					validationException = e;
				}
			}

			Document doc = this.createDocument(req, uriParser);
			Element document = (Element) doc.getFirstChild();

			Element addNewsForm = doc.createElement("addNewsForm");
			document.appendChild(addNewsForm);
			addNewsForm.appendChild(section.toXML(doc));

			AccessUtils.appendGroupsAndUsers(doc, addNewsForm, systemInterface.getUserHandler(), systemInterface.getGroupHandler());

			if (this.csspath != null) {
				addNewsForm.appendChild(XMLUtils.createCDATAElement("cssPath", csspath, doc));
			}

			// Append any errors
			if (validationException != null) {
				addNewsForm.appendChild(validationException.toXML(doc));
				addNewsForm.appendChild(RequestUtils.getRequestParameters(req, doc));
			}

			return new SimpleForegroundModuleResponse(doc, moduleDescriptor.getName(), this.getDefaultBreadcrumb());
		}
	}

	private News getNews(URIParser uriParser, User user) throws NumberFormatException, URINotFoundException, SQLException {

		News news = null;

		if (NumberUtils.isInt(uriParser.get(2)) && (news = newsDAO.getNews(Integer.parseInt(uriParser.get(2)))) != null) {
			return news;
		} else {
			throw new URINotFoundException(uriParser);
		}
	}

	private Breadcrumb getNewsBreadcrumb(HttpServletRequest req, News news, String method) {

		return new Breadcrumb(news.getTitle(), news.getTitle(), getFullAlias() + "/" + method + "/" + news.getNewsID(), URLType.RELATIVE_FROM_CONTEXTPATH);
	}

	@WebPublic
	public SimpleForegroundModuleResponse move(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		News news = this.getNews(uriParser, user);

		SimpleSectionDescriptor section = null;

		if (uriParser.size() == 3) {

			// Show section tree
			Document doc = this.createDocument(req, uriParser);
			Element moveNews = doc.createElement("moveNewsForm");
			doc.getFirstChild().appendChild(moveNews);
			moveNews.appendChild(news.toXML(doc));

			SimpleSectionDescriptor rootSection = this.sectionDAO.getRootSection(true);

			Element sectionsElement = doc.createElement("sections");
			moveNews.appendChild(sectionsElement);

			this.appendSection(sectionsElement, doc, rootSection, false);

			return new SimpleForegroundModuleResponse(doc, moduleDescriptor.getName(), this.getDefaultBreadcrumb(), this.getNewsBreadcrumb(req, news, "show"));

		} else if (uriParser.size() == 4 && NumberUtils.isInt(uriParser.get(3)) && (section = this.sectionDAO.getSection(Integer.parseInt(uriParser.get(3)), false)) != null) {

			// Store old sectionID
			Integer oldSectionID = news.getSectionID();

			// Move the news to selected section
			log.info("User " + user + " moving news " + news + " to section " + section);
			news.setSectionID(section.getSectionID());
			this.newsDAO.update(news);

			newsRemoved(news, oldSectionID, news.isEnabled());
			newsAdded(news);

			res.sendRedirect(getModuleURI(req));
			return null;

		} else {
			throw new URINotFoundException(uriParser);
		}
	}

	@WebPublic
	public SimpleForegroundModuleResponse copy(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		News news = this.getNews(uriParser, user);

		SimpleSectionDescriptor section = null;

		if (uriParser.size() == 3) {

			// Show section tree
			Document doc = this.createDocument(req, uriParser);
			Element moveNews = doc.createElement("copyNewsForm");
			doc.getFirstChild().appendChild(moveNews);
			moveNews.appendChild(news.toXML(doc));

			SimpleSectionDescriptor rootSection = this.sectionDAO.getRootSection(true);

			Element sectionsElement = doc.createElement("sections");
			moveNews.appendChild(sectionsElement);

			this.appendSection(sectionsElement, doc, rootSection, false);

			return new SimpleForegroundModuleResponse(doc, moduleDescriptor.getName(), this.getDefaultBreadcrumb(), this.getNewsBreadcrumb(req, news, "show"));

		} else if (uriParser.size() == 4 && NumberUtils.isInt(uriParser.get(3)) && (section = this.sectionDAO.getSection(Integer.parseInt(uriParser.get(3)), false)) != null) {

			// Copy the news to selected section
			log.info("User " + user + " copying news " + news + " to section " + section);

			news.setNewsID(null);
			news.setSectionID(section.getSectionID());
			news.setAdded(new Timestamp(System.currentTimeMillis()));

			this.newsDAO.add(news);
			this.newsAdded(news);

			res.sendRedirect(getModuleURI(req));
			return null;

		} else {
			throw new URINotFoundException(uriParser);
		}
	}

	@WebPublic
	public SimpleForegroundModuleResponse update(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		News oldNews = this.getNews(uriParser, user);

		String oldText = oldNews.getText();
		String oldTitle = oldNews.getTitle();

		ValidationException validationException = null;

		if (req.getMethod().equalsIgnoreCase("POST")) {

			try {
				boolean wasEanbled = oldNews.isEnabled();

				// Populate news
				News news = populator.populate(oldNews, req);

				// Title or text changed - Mark as updated
				if(!oldText.equals(news.getText()) || !oldTitle.equals(news.getTitle())) {

					news.setEditor(user);
					news.setUpdated(new Timestamp(System.currentTimeMillis()));

					// Replace absolute file paths
					this.removeAbsoluteFileUrls(news, uriParser);

				}

				// Title, text or visibility changed - Save changes
				if(!oldText.equals(news.getText()) || !oldTitle.equals(news.getTitle()) || !(wasEanbled && news.isEnabled())) {

					log.info("User " + user + " updating news " + news);

					// Save changes
					newsDAO.update(news);
					newsUpdated(news, wasEanbled);

				}

				// Redirect user
				res.sendRedirect(getModuleURI(req));
				return null;

			} catch (ValidationException e) {
				validationException = e;
			}
		}

		Document doc = this.createDocument(req, uriParser);
		Element document = (Element) doc.getFirstChild();

		Element updateNewsForm = doc.createElement("updateNewsForm");
		document.appendChild(updateNewsForm);

		AccessUtils.appendGroupsAndUsers(doc, updateNewsForm, systemInterface.getUserHandler(), systemInterface.getGroupHandler());

		// Append any errors
		if (validationException != null) {
			updateNewsForm.appendChild(validationException.toXML(doc));
			updateNewsForm.appendChild(RequestUtils.getRequestParameters(req, doc));
		}

		this.setAbsoluteFileUrls(oldNews, uriParser);

		// Append the news
		updateNewsForm.appendChild(oldNews.toXML(doc));

		// Append the section
		updateNewsForm.appendChild(sectionDAO.getSection(oldNews.getSectionID(), false).toXML(doc));

		if (this.csspath != null) {
			updateNewsForm.appendChild(XMLUtils.createCDATAElement("cssPath", csspath, doc));
		}

		return new SimpleForegroundModuleResponse(doc, oldNews.getTitle(), this.getDefaultBreadcrumb(), this.getNewsBreadcrumb(req, oldNews, "show"));
	}

	@WebPublic
	public SimpleForegroundModuleResponse delete(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		News news = this.getNews(uriParser, user);

		// Delete the news
		log.info("User " + user + " deleting news " + news);
		newsDAO.delete(news);

		this.newsRemoved(news, news.getSectionID(), news.isEnabled());

		// Redirect user
		res.sendRedirect(getModuleURI(req));

		return null;
	}

	@WebPublic
	public SimpleForegroundModuleResponse connector(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws TransformerFactoryConfigurationError, TransformerException, IOException {

		this.connector.processRequest(req, res, uriParser, user, moduleDescriptor);

		return null;
	}

	@WebPublic
	public SimpleForegroundModuleResponse file(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		this.connector.processFileRequest(req, res, user, uriParser, moduleDescriptor, sectionInterface, 2, null);

		return null;
	}

	private String getAbsoluteFileURL(URIParser uriParser) {

		return uriParser.getCurrentURI(true) + "/" + this.moduleDescriptor.getAlias() + "/file";
	}

	public void removeAbsoluteFileUrls(News news, URIParser uriParser) {

		String text = news.getText();

		String absoluteFileURL = this.getAbsoluteFileURL(uriParser);

		for (String attribute : BaseFileAccessValidator.TAG_ATTRIBUTES) {

			text = text.replace(attribute + "=\"" + absoluteFileURL, attribute + "=\"" + News.RELATIVE_PATH_MARKER);
			text = text.replace(attribute + "='" + absoluteFileURL, attribute + "='" + News.RELATIVE_PATH_MARKER);
		}

		news.setText(text);
	}

	public void setAbsoluteFileUrls(News news, URIParser uriParser) {

		String text = news.getText();

		String absoluteFileURL = this.getAbsoluteFileURL(uriParser);

		for (String attribute : BaseFileAccessValidator.TAG_ATTRIBUTES) {

			text = text.replace(attribute + "=\"" + News.RELATIVE_PATH_MARKER, attribute + "=\"" + absoluteFileURL);
			text = text.replace(attribute + "='" + News.RELATIVE_PATH_MARKER, attribute + "='" + absoluteFileURL);
		}

		news.setText(text);
	}

	@Override
	public List<SettingDescriptor> getSettings() {

		List<SettingDescriptor> settings = new ArrayList<SettingDescriptor>();
		settings.add(SettingDescriptor.createTextFieldSetting("csspath", "Editor CSS", "Path to the desired CSS stylesheet for FCKEditor (relative from the contextpath)", false, null, null));
		settings.add(SettingDescriptor.createTextFieldSetting("filestore", "Filestore path", "Path to the directory to be used as filestore", false, null, null));
		settings.add(SettingDescriptor.createTextFieldSetting("newsFileProxyModuleAlias", "File proxy module alias", "Alias of the file proxy module used by view modules created by this module", false, null, null));
		settings.add(SettingDescriptor.createTextFieldSetting("newsFileProxyModuleName", "File proxy module name", "The name used for news file proxy modules created by this module", false, null, null));
		settings.add(SettingDescriptor.createTextFieldSetting("newsViewModuleName", "View module name", "The name used for news view modules created by this module", true, "News viewer", null));
		settings.add(SettingDescriptor.createTextAreaSetting("newsViewModuleAliases", "View module aliases", "The aliases used for news view modules created by this module", true, "*", null));
		settings.add(SettingDescriptor.createTextAreaSetting("newsViewModuleSlots", "View module slots", "The slots used for news view modules created by this module", true, null, null));
		settings.add(SettingDescriptor.createDropDownSetting("newsViewModuleXSLPathType", "View module XSL path type", "The path type used for news view modules created by this module", true, PathType.Classpath.toString(), ModuleUtils.getValueDescriptors(PathType.values())));
		settings.add(SettingDescriptor.createTextFieldSetting("newsViewModuleXSLPath", "View module XSL path", "Path to the XSL stylesheet used by the news view modules created by this module", true, "NewsViewModule.se.xsl", null));
		settings.add(SettingDescriptor.createTextFieldSetting("nrOfNewsToShow", "News to show", "The number of news to show, selection is based on current ordering", true, null, new StringIntegerValidator(0, null)));

		ModuleUtils.addSettings(settings, super.getSettings());
		return settings;
	}

	@Override
	public void sectionCached(SectionDescriptor sectionDescriptor, Section sectionInstance) throws KeyAlreadyCachedException {
		this.createViewModules(sectionInstance, false);
	}

	@Override
	public void sectionUpdated(SectionDescriptor sectionDescriptor, Section sectionInstance) throws KeyNotCachedException {

	}

	@Override
	public void sectionUnloaded(SectionDescriptor sectionDescriptor, Section sectionInstance) throws KeyNotCachedException {
		this.viewerModuleMap.remove(sectionDescriptor.getSectionID());
	}

	public synchronized void newsAdded(News news){

		if(!news.isEnabled()){

			return;
		}

		NewsViewModule viewModule = this.viewerModuleMap.get(news.getSectionID());

		if(viewModule == null){

			SectionInterface sectionInterface = systemInterface.getSectionInterface(news.getSectionID());

			if(sectionInterface != null){

				this.createViewModules(sectionInterface, false);

			}

		}else{

			viewModule.addNews(news);

		}
	}

	public synchronized void newsUpdated(News news, boolean wasEnabled) throws Exception{

		if(!news.isEnabled() && !wasEnabled){

			return;

		}else if(news.isEnabled() && !wasEnabled){

			this.newsAdded(news);

		}else if(news.isEnabled() && wasEnabled){

			NewsViewModule viewModule = this.viewerModuleMap.get(news.getSectionID());

			if(viewModule != null){

				viewModule.updateNews(news);

			}

		}else if(!news.isEnabled() && wasEnabled){

			this.newsRemoved(news,news.getSectionID(),wasEnabled);
		}
	}

	public synchronized void newsRemoved(News news, Integer sectionID, boolean wasEnabled) throws Exception{

		if(!wasEnabled){

			return;
		}

		NewsViewModule viewModule = this.viewerModuleMap.get(sectionID);

		if(viewModule != null){

			if(this.newsDAO.sectionHasEnabledNews(sectionID)){

				viewModule.removeNews(news);

			}else{

				log.info("Removing news view module from section " + viewModule.getSectionInterface().getSectionDescriptor());

				viewModule.getSectionInterface().getBackgroundModuleCache().unload(viewModule.getBackgroundModuleDescriptor());

				this.viewerModuleMap.remove(viewModule);
			}
		}
	}
}
