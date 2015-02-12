package se.unlogic.hierarchy.foregroundmodules.gallery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.fileuploadutils.MultipartRequest;
import se.unlogic.hierarchy.core.annotations.GroupMultiListSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.ModuleSetting;
import se.unlogic.hierarchy.core.annotations.TextFieldSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.UserMultiListSettingDescriptor;
import se.unlogic.hierarchy.core.annotations.WebPublic;
import se.unlogic.hierarchy.core.annotations.XSLVariable;
import se.unlogic.hierarchy.core.beans.Breadcrumb;
import se.unlogic.hierarchy.core.beans.Group;
import se.unlogic.hierarchy.core.beans.SimpleForegroundModuleResponse;
import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.AccessDeniedException;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.AccessInterface;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleDescriptor;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.interfaces.SectionDescriptor;
import se.unlogic.hierarchy.core.interfaces.SectionInterface;
import se.unlogic.hierarchy.core.utils.AccessUtils;
import se.unlogic.hierarchy.core.utils.CRUDCallback;
import se.unlogic.hierarchy.core.utils.HierarchyAnnotatedDAOFactory;
import se.unlogic.hierarchy.core.utils.IntegerBasedCRUD;
import se.unlogic.hierarchy.foregroundmodules.AnnotatedForegroundModule;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Comment;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Gallery;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Image;
import se.unlogic.hierarchy.foregroundmodules.gallery.cruds.CommentCRUD;
import se.unlogic.hierarchy.foregroundmodules.gallery.cruds.GalleryCRUD;
import se.unlogic.hierarchy.foregroundmodules.gallery.enums.ThumbSize;
import se.unlogic.standardutils.collections.CollectionUtils;
import se.unlogic.standardutils.dao.AdvancedAnnotatedDAOWrapper;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.HighLevelQuery;
import se.unlogic.standardutils.dao.QueryParameterFactory;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.db.DBUtils;
import se.unlogic.standardutils.db.tableversionhandler.TableVersionHandler;
import se.unlogic.standardutils.db.tableversionhandler.UpgradeResult;
import se.unlogic.standardutils.db.tableversionhandler.XMLDBScriptProvider;
import se.unlogic.standardutils.image.ImageUtils;
import se.unlogic.standardutils.io.BinarySizes;
import se.unlogic.standardutils.io.FileUtils;
import se.unlogic.standardutils.mime.MimeUtils;
import se.unlogic.standardutils.numbers.NumberUtils;
import se.unlogic.standardutils.random.RandomUtils;
import se.unlogic.standardutils.streams.StreamUtils;
import se.unlogic.standardutils.string.StringUtils;
import se.unlogic.standardutils.validation.PositiveStringIntegerValidator;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationErrorType;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLUtils;
import se.unlogic.webutils.http.HTTPUtils;
import se.unlogic.webutils.http.RequestUtils;
import se.unlogic.webutils.http.URIParser;

public class GalleryModule extends AnnotatedForegroundModule implements CRUDCallback<User>, AccessInterface {

	private static final List<Field> SMALL_THUMB_EXCLUDED_FIELD = Collections.singletonList(Image.SMALL_THUMB_FIELD);
	private static final List<Field> MEDIUM_THUMB_EXCLUDED_FIELD = Collections.singletonList(Image.MEDIUM_THUMB_FIELD);
	private static final List<Field> ALL_THUMBS_EXCLUDED = CollectionUtils.getList(Image.SMALL_THUMB_FIELD, Image.MEDIUM_THUMB_FIELD);

	private static final ImageFileFilter FILE_FILTER = new ImageFileFilter();

	@XSLVariable
	private String uploadFilesText = "Upload files";

	@XSLVariable
	private String addGalleryText = "Add gallery";

	@XSLVariable
	private String updateGalleryText = "Update gallery";

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Thumbnails per page", description = "The number of thumbnails per page (default is 15)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer numOfThumbsPerPage = 15;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Small thumb height", description = "The max height of the small thumbnails (default is 93)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer smallThumbMaxHeight = 93;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Small thumb width", description = "The max width of the small thumbnails (default is 125)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer smallThumbMaxWidth = 125;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Medium thumb height", description = "The max height of the medium thumbnails (default is 500)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer mediumThumbMaxHeight = 500;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Medium thumb width", description = "The max width of the medium thumbnails (default is 500)", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer mediumThumbMaxWidth = 500;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Max upload size", description = "Maxmium upload size in megabytes allowed in a single post request", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer maxRequestSize = 200;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Max file size", description = "Maxmium file size in megabytes allowed", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected Integer maxFileSize = 15;

	@ModuleSetting
	@TextFieldSettingDescriptor(name="RAM threshold",description="Maximum size of files in KB to be buffered in RAM during file uploads. Files exceeding the threshold are written to disk instead.",required=true,formatValidator=PositiveStringIntegerValidator.class)
	protected Integer ramThreshold = 500;

	@ModuleSetting(allowsNull = true)
	@TextFieldSettingDescriptor(name = "Gallery base path", description = "Directory where new galleries will be created if the given path for a new gallery is relative", required = false)
	protected String basePath;

	@ModuleSetting
	@TextFieldSettingDescriptor(name = "Shutdown timeout", description = "How many seconds to wait for the thread pool to finish on shutdown", required = true, formatValidator = PositiveStringIntegerValidator.class)
	protected long shutdownTimeout = 60;

	@ModuleSetting(allowsNull = true)
	@GroupMultiListSettingDescriptor(name = "Admin groups", description = "Groups allowed to administrate this module")
	private List<Integer> adminGroupIDs;

	@ModuleSetting(allowsNull = true)
	@UserMultiListSettingDescriptor(name = "Admin users", description = "Users allowed to administrate this module")
	private List<Integer> adminUserIDs;

	protected final ReentrantReadWriteLock thumbCacheLock = new ReentrantReadWriteLock();
	protected final Lock thumbCacheReadLock = thumbCacheLock.readLock();
	protected final Lock thumbCacheWriteLock = thumbCacheLock.writeLock();

	private HashMap<String, Gallery> galleryByAliasCache;
	private HashMap<Integer, Gallery> galleryByIDCache;
	private HashMap<Gallery,Set<Image>> thumbCache;

	private AnnotatedDAO<Gallery> galleryDAO;
	private AnnotatedDAO<Image> imageDAO;

	private QueryParameterFactory<Image, Gallery> imageGalleryParamFactory;
	private QueryParameterFactory<Image, String> imageFilenameParamFactory;
	private QueryParameterFactory<Image, Long> lastModifiedParamFactory;

	private IntegerBasedCRUD<Gallery, GalleryModule> galleryCRUD;
	private IntegerBasedCRUD<Comment, GalleryModule> commentCRUD;

	private ThreadPoolExecutor threadPoolExecutor;

	private byte[] smallThumbLoadingImage;
	private byte[] mediumThumbLoadingImage;

	@Override
	public void init(ForegroundModuleDescriptor moduleDescriptor, SectionInterface sectionInterface, DataSource dataSource) throws Exception {

		super.init(moduleDescriptor, sectionInterface, dataSource);

		int poolSize = Runtime.getRuntime().availableProcessors();

		threadPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	@Override
	public void update(ForegroundModuleDescriptor moduleDescriptor, DataSource dataSource) throws Exception {

		super.update(moduleDescriptor, dataSource);
	}

	@Override
	public void unload() throws Exception {

		log.info("Stopping thread pool...");

		threadPoolExecutor.shutdownNow();

		try{
			this.threadPoolExecutor.awaitTermination(shutdownTimeout, TimeUnit.MILLISECONDS);
		}catch(InterruptedException e){
			log.error("Error waiting for thread pool to shutdown", e);
		}

		if(threadPoolExecutor.isShutdown()){

			log.info("Thread pool succesfully shutdown.");

		}else{

			log.info("Timeout of " + shutdownTimeout + " seconds exceeded while waiting for thread pool to shutdown.");
		}

		super.unload();
	}

	@Override
	protected void createDAOs(DataSource dataSource) throws Exception {

		super.createDAOs(dataSource);

		//Old style table version handling
		checkTables(dataSource);

		//New automatic table version handling
		UpgradeResult upgradeResult = TableVersionHandler.upgradeDBTables(dataSource, this.getClass().getName(), new XMLDBScriptProvider(this.getClass().getResourceAsStream("dbscripts/DB script.xml")));

		if(upgradeResult.isUpgrade()){

			log.info(upgradeResult.toString());
		}

		HierarchyAnnotatedDAOFactory daoFactory = new HierarchyAnnotatedDAOFactory(dataSource, systemInterface.getUserHandler(), systemInterface.getGroupHandler());

		galleryDAO = daoFactory.getDAO(Gallery.class);
		imageDAO = daoFactory.getDAO(Image.class);

		galleryCRUD = new GalleryCRUD(galleryDAO.getAdvancedWrapper(Integer.class), this);

		AdvancedAnnotatedDAOWrapper<Comment, Integer> commentDAOWrapper = daoFactory.getDAO(Comment.class).getAdvancedWrapper(Integer.class);
		commentDAOWrapper.getGetQuery().addRelation(Comment.IMAGE_RELATION);
		commentDAOWrapper.getGetQuery().addRelation(Image.GALLERY_RELATION);
		commentDAOWrapper.getGetQuery().setExcludedFields(ALL_THUMBS_EXCLUDED);

		commentCRUD = new CommentCRUD(commentDAOWrapper, this);

		cacheGalleries();
	}

	public synchronized void cacheGalleries() throws SQLException {

		HashMap<String, Gallery> aliasCache = new HashMap<String, Gallery>();
		HashMap<Integer, Gallery> idCache = new HashMap<Integer, Gallery>();

		List<Gallery> galleries = galleryDAO.getAll();

		if(galleries != null){
			for(Gallery gallery : galleries){

				aliasCache.put(gallery.getAlias(), gallery);
				idCache.put(gallery.getGalleryID(), gallery);
			}
		}

		galleryByAliasCache = aliasCache;
		galleryByIDCache = idCache;
	}

	public void cacheThumbNails(){


	}

	public void cacheThumbNails(Gallery gallery) throws SQLException{

		try{
			thumbCacheWriteLock.lock();

			HighLevelQuery<Image> query = new HighLevelQuery<Image>();

			query.setExcludedFields(ALL_THUMBS_EXCLUDED);
			query.addParameter(imageGalleryParamFactory.getParameter(gallery));

			List<Image> images = imageDAO.getAll(query);

			if(images == null){

				thumbCache.remove(gallery);

			}else{

				thumbCache.put(gallery, new HashSet<Image>(images));
			}

		}finally{
			thumbCacheWriteLock.unlock();
		}
	}

	public void galleryRemoved(Gallery gallery){


	}

	public void generateThumbnailLoadingImages() throws IOException {

		BufferedImage baseImage = ImageUtils.getImageByURL(this.getClass().getResource("staticcontent/pics/thumbnail_base.png"));

		baseImage = ImageUtils.scaleImage(baseImage, mediumThumbMaxHeight, mediumThumbMaxWidth, java.awt.Image.SCALE_SMOOTH, baseImage.getType());

		this.mediumThumbLoadingImage = ImageUtils.convertImage(baseImage, ImageUtils.JPEG);

		baseImage = ImageUtils.scaleImage(baseImage, smallThumbMaxHeight, smallThumbMaxWidth, java.awt.Image.SCALE_SMOOTH, baseImage.getType());

		this.smallThumbLoadingImage = ImageUtils.convertImage(baseImage, ImageUtils.JPEG);

		//TODO add text to thumbnails
	}

	private void checkTables(DataSource dataSource) throws SQLException, IOException {

		log.debug("Checking for gallery tables in datasource " + dataSource + "...");

		TransactionHandler transactionHandler = null;

		try{
			transactionHandler = new TransactionHandler(dataSource);

			if(TableVersionHandler.getTableGroupVersion(transactionHandler, this.getClass().getName()) != null){

				//New table version handler has taken over
				return;
			}

			if(!DBUtils.tableExists(this.dataSource, "galleries")){

				log.info(this.moduleDescriptor + " creating galleries table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/GalleryTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "gallerygroups")){

				log.info(this.moduleDescriptor + " creating gallerygroups table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/GalleryGroupsTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "galleryusers")){

				log.info(this.moduleDescriptor + " creating galleryusers table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/GalleryUsersTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "galleryuploadgroups")){

				log.info(this.moduleDescriptor + " creating galleryuploadgroups table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/GalleryUploadGroupsTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "galleryuploadusers")){

				log.info(this.moduleDescriptor + " creating galleryuploadusers table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/GalleryUploadUsersTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "pictures")){

				log.info(this.moduleDescriptor + " creating pictures table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/PicturesTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			if(!DBUtils.tableExists(this.dataSource, "picturecomments")){

				log.info(this.moduleDescriptor + " creating picturecomments table in datasource " + dataSource);

				String sql = StringUtils.readStreamAsString(this.getClass().getResourceAsStream("dbscripts/PictureCommentsTable.sql"));

				transactionHandler.getUpdateQuery(sql).executeUpdate();
			}

			transactionHandler.commit();

		}finally{

			TransactionHandler.autoClose(transactionHandler);
		}
	}

	@Override
	public ForegroundModuleResponse defaultMethod(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception, Throwable {

		return listGalleries(req, res, user, uriParser, null);
	}

	public ForegroundModuleResponse listGalleries(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, List<ValidationError> validationErrors) throws SQLException {

		log.info("User " + user + " listing galleries");

		Document doc = this.createDocument(req, uriParser, user);
		Element galleriesElement = doc.createElement("Galleries");
		doc.getFirstChild().appendChild(galleriesElement);

		if(!galleryByAliasCache.isEmpty()){

			for(Gallery gallery : galleryByAliasCache.values()){

				if(AccessUtils.checkAccess(user, gallery)){

					Element galleryElement = gallery.toXML(doc);

					File dir = new File(gallery.getDirectory());

					if(dir.canRead()){

						File[] files = dir.listFiles(FILE_FILTER);

						int fileCount = files.length;

						galleryElement.appendChild(XMLUtils.createElement("numPics", fileCount, doc));

						if(fileCount > 0){

							File file = files[RandomUtils.getRandomInt(0, files.length)];

							galleryElement.appendChild(XMLUtils.createElement("randomFile", file.getName(), doc));

							if(!isThumbnailAvaiable(gallery, file)){

								XMLUtils.appendNewElement(doc, galleryElement, "thumbNotAvailable");
							}
						}

					}else{
						galleryElement.appendChild(XMLUtils.createElement("numPics", "0", doc));
					}

					galleriesElement.appendChild(galleryElement);
				}
			}
		}

		return new SimpleForegroundModuleResponse(doc, this.moduleDescriptor.getName(), this.getDefaultBreadcrumb());
	}

	private boolean isThumbnailAvaiable(Gallery gallery, File file) throws SQLException {

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		query.addParameter(imageFilenameParamFactory.getParameter(file.getName()));
		query.addParameter(imageGalleryParamFactory.getParameter(gallery));
		query.addParameter(lastModifiedParamFactory.getParameter(file.lastModified()));

		return imageDAO.getBoolean(query);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse addGallery(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return galleryCRUD.add(req, res, user, uriParser);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse updateGallery(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return galleryCRUD.update(req, res, user, uriParser);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse deleteGallery(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return galleryCRUD.delete(req, res, user, uriParser);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse addComment(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		Gallery gallery = null;

		if(uriParser.size() != 4 || (gallery = galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}

		this.checkAccess(user, gallery);

		// check if path is valid
		File dir = new File(gallery.getDirectory());

		if(!dir.exists() || !dir.canRead()){

			redirectToMethod(req, res, "/" + URLEncoder.encode(gallery.getAlias(), "UTF-8"));
			return null;
		}

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		query.setExcludedFields(ALL_THUMBS_EXCLUDED);
		query.addParameter(imageFilenameParamFactory.getParameter(uriParser.get(3)));
		query.addParameter(imageGalleryParamFactory.getParameter(gallery));

		Image image = imageDAO.get(query);

		if(image == null){

			throw new URINotFoundException(uriParser);
		}

		image.setGallery(gallery);

		req.setAttribute("image", image);

		return commentCRUD.add(req, res, user, uriParser);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse updateComment(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return commentCRUD.update(req, res, user, uriParser);
	}

	@WebPublic(toLowerCase = true, requireLogin = true)
	public ForegroundModuleResponse deleteComment(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		return commentCRUD.delete(req, res, user, uriParser);
	}

	@WebPublic(alias = "gallery")
	public SimpleForegroundModuleResponse showGallery(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		Gallery gallery = null;

		if(uriParser.size() < 3 || uriParser.size() > 4 || (gallery = galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}else{

			checkAccess(user, gallery);

			Integer page = 1;

			if(uriParser.size() == 4){
				page = NumberUtils.toInt(uriParser.get(3));

				if(page == null || page <= 0){
					throw new URINotFoundException(uriParser);
				}
			}

			log.info("User " + user + " requested page " + page + " in gallery " + gallery);

			Document doc = this.createDocument(req, uriParser, user);

			Element showGalleryElement = doc.createElement("showGallery");
			doc.getFirstChild().appendChild(showGalleryElement);

			Element galleryElement = gallery.toXML(doc);
			showGalleryElement.appendChild(showGalleryElement);

			int pages = 1;
			int imageCount = 0;
			File[] files = null;

			// check if path is valid
			File dir = new File(gallery.getDirectory());

			if(!dir.exists()){

				XMLUtils.appendNewElement(doc, galleryElement, "directoryNotFound");

			}else if(!dir.canRead()){

				XMLUtils.appendNewElement(doc, galleryElement, "directoryNotReadable");

			}else{

				galleryElement.appendChild(XMLUtils.createElement("hasUploadAccess", this.checkUploadAccess(user, gallery), doc));

				files = dir.listFiles(FILE_FILTER);
				imageCount = files.length;

				if(imageCount > 0){

					pages = imageCount % numOfThumbsPerPage == 0 ? imageCount / numOfThumbsPerPage : (imageCount / numOfThumbsPerPage) + 1;

					if(page <= pages){

						Arrays.sort(files);

						// find next and previous page
						Integer nextPage = page == pages ? null : page + 1;
						Integer prevPage = page == 1 ? null : page - 1;

						// calculate start- and endindex
						int startIndex = (numOfThumbsPerPage * page) - numOfThumbsPerPage;
						int endIndex = startIndex + numOfThumbsPerPage;

						if(page == pages){
							endIndex = files.length;
						}

						if(nextPage != null){
							galleryElement.appendChild(XMLUtils.createElement("nextPage", nextPage, doc));
						}

						if(prevPage != null){
							galleryElement.appendChild(XMLUtils.createElement("prevPage", prevPage, doc));
						}

						Element filesElement = doc.createElement("files");

						// find images for requested page
						for(int i = startIndex; i < endIndex; i++){
							File file = files[i];
							filesElement.appendChild(XMLUtils.createElement("file", file.getName(), doc));

							if(!isThumbnailAvaiable(gallery, file)){

								XMLUtils.appendNewElement(doc, galleryElement, "thumbNotAvailable");
							}
						}

					}else{

						redirectToMethod(req, res, "/gallery/" + gallery.getAlias());
						return null;
					}
				}
			}

			galleryElement.appendChild(XMLUtils.createElement("pages", pages, doc));
			galleryElement.appendChild(XMLUtils.createElement("currentPage", page, doc));
			galleryElement.appendChild(XMLUtils.createElement("imageCount", imageCount, doc));

			return new SimpleForegroundModuleResponse(doc, gallery.getName(), getDefaultBreadcrumb(), getGalleryBreadcrumb(gallery));
		}
	}

	@WebPublic(alias = "image")
	public SimpleForegroundModuleResponse showImage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		return showImage(req, res, user, uriParser, null);
	}

	public SimpleForegroundModuleResponse showImage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ValidationException validationException) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		Gallery gallery = null;

		if(uriParser.size() != 4 || (gallery = galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}else{

			this.checkAccess(user, gallery);

			// check if path is valid
			File dir = new File(gallery.getDirectory());

			if(!dir.exists() || !dir.canRead()){

				redirectToMethod(req, res, "/" + URLEncoder.encode(gallery.getAlias(), "UTF-8"));
				return null;
			}

			// get all filenames
			String filename = uriParser.get(3);

			// get all files
			File[] allFiles = dir.listFiles(FILE_FILTER);

			Arrays.sort(allFiles);

			// check if filename exist
			boolean found = false;
			int currentIndex = -1;

			for(int i = 0; i < allFiles.length; i++){
				if(allFiles[i].getName().equals(filename)){
					found = true;
					currentIndex = i;
					break;
				}
			}

			int picsInGallery = allFiles.length;

			if(found){

				log.info("User " + user + " requested image " + filename + " in gallery " + gallery);

				// find next and previous picture
				String nextPicture = currentIndex < picsInGallery - 1 ? allFiles[currentIndex + 1].getName() : null;
				String prevPicture = currentIndex > 0 ? allFiles[currentIndex - 1].getName() : null;

				// create XML-document containing information about the requested gallery and images
				Document doc = this.createDocument(req, uriParser, user);

				Element showImageElement = doc.createElement("showImage");
				doc.getFirstChild().appendChild(showImageElement);

				Element galleryElement = gallery.toXML(doc);
				galleryElement.appendChild(XMLUtils.createElement("numPics", String.valueOf(picsInGallery), doc));
				galleryElement.appendChild(XMLUtils.createElement("currentPic", String.valueOf(currentIndex + 1), doc));

				if(currentIndex == 0){
					currentIndex++;
				}

				showImageElement.appendChild(XMLUtils.createElement("currentPage", (((currentIndex) / numOfThumbsPerPage) + 1) + "", doc));

				if(nextPicture != null){
					galleryElement.appendChild(XMLUtils.createElement("nextImage", nextPicture.toString(), doc));
				}
				if(prevPicture != null){
					galleryElement.appendChild(XMLUtils.createElement("prevImage", prevPicture.toString(), doc));
				}

				Element fileElement = doc.createElement("file");
				fileElement.appendChild(XMLUtils.createElement("filename", filename, doc));

				XMLUtils.append(doc, galleryElement, "comments", getComments(gallery, filename));

				galleryElement.appendChild(fileElement);
				showImageElement.appendChild(galleryElement);

				if(validationException != null){
					showImageElement.appendChild(validationException.toXML(doc));
					showImageElement.appendChild(RequestUtils.getRequestParameters(req, doc));
				}

				return new SimpleForegroundModuleResponse(doc, filename, getDefaultBreadcrumb(), getGalleryBreadcrumb(gallery), getImageBreadcrumb(gallery, filename));

			}else{

				throw new URINotFoundException(uriParser);
			}
		}
	}

	private List<Comment> getComments(Gallery gallery, String filename) throws SQLException {

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		query.setExcludedFields(ALL_THUMBS_EXCLUDED);

		query.addParameter(imageFilenameParamFactory.getParameter(filename));
		query.addParameter(imageGalleryParamFactory.getParameter(gallery));

		query.addRelation(Image.COMMENTS_RELATION);

		Image image = imageDAO.get(query);

		if(image != null){

			return image.getComments();
		}

		return null;
	}

	@WebPublic(alias = "fullimage")
	public SimpleForegroundModuleResponse getFile(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws SQLException, URINotFoundException, AccessDeniedException {

		Gallery gallery = null;

		if(uriParser.size() != 4 || (gallery = galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}else{

			checkAccess(user, gallery);

			String filename = uriParser.get(3).toString();

			File file = new File(gallery.getDirectory() + File.separator + filename);

			// check if file is readable
			if(ImageFileFilter.isValidFilename(filename) && FileUtils.isReadable(file)){

				sendFile(gallery, file, res, user);

			}else{
				log.info("The picture " + filename + " in gallery " + gallery + " requested by user " + user + " does not exist or is not readable");
				throw new URINotFoundException(uriParser);
			}
		}

		return null;
	}

	public void sendFile(Gallery gallery, File file, HttpServletResponse res, User user) {

		FileInputStream in = null;
		OutputStream out = null;

		try{
			log.debug("User " + user + " requesting image " + file.getName() + " in gallery " + gallery);

			in = new FileInputStream(file);

			HTTPUtils.setContentLength(file.length(), res);
			res.setContentType(MimeUtils.getMimeType(file));
			res.setHeader("Content-Disposition", "inline; filename=\"" + FileUtils.toValidHttpFilename(file.getName()) + "\"");

			out = res.getOutputStream();

			StreamUtils.transfer(in, out);

		}catch(RuntimeException e){

			log.info("Caught exception " + e + " while sending image " + file.getName() + " in gallery " + gallery + " to " + user);

		}catch(IOException e){

			log.info("Caught exception " + e + " while sending image " + file.getName() + " in gallery " + gallery + " to " + user);

		}finally{

			StreamUtils.closeStream(in);
			StreamUtils.closeStream(out);
		}
	}

	@WebPublic(alias = "small")
	public SimpleForegroundModuleResponse getSmallThumb(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		return getThumb(req, res, user, uriParser, ThumbSize.SMALL);
	}

	@WebPublic(alias = "medium")
	public SimpleForegroundModuleResponse getMediumThumb(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		return getThumb(req, res, user, uriParser, ThumbSize.MEDIUM);
	}

	@WebPublic(alias = "generatingmedium")
	public SimpleForegroundModuleResponse generatingMediumThumbImage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		ByteArrayInputStream in = null;
		OutputStream out = null;

		try{
			log.debug("User " + user + " requesting placeholder for medium thumbs");

			in = new ByteArrayInputStream(mediumThumbLoadingImage);

			HTTPUtils.setContentLength(mediumThumbLoadingImage.length, res);
			res.setContentType("image/jpeg");
			res.setHeader("Content-Disposition", "inline; filename=\"generatingmedium.jpg\"");

			out = res.getOutputStream();

			StreamUtils.transfer(in, out);

		}catch(RuntimeException e){

			log.info("Caught exception " + e + " while sending placeholder for medium thumbs to " + user);

		}catch(IOException e){

			log.info("Caught exception " + e + " while sending placeholder for medium thumbs to " + user);

		}finally{

			StreamUtils.closeStream(in);
			StreamUtils.closeStream(out);
		}

		return null;
	}

	@WebPublic(alias = "generatingsmall")
	public SimpleForegroundModuleResponse generatingSmallThumbImage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		ByteArrayInputStream in = null;
		OutputStream out = null;

		try{
			log.debug("User " + user + " requesting placeholder for small thumbs");

			in = new ByteArrayInputStream(smallThumbLoadingImage);

			HTTPUtils.setContentLength(smallThumbLoadingImage.length, res);
			res.setContentType("image/jpeg");
			res.setHeader("Content-Disposition", "inline; filename=\"generatingsmall.jpg\"");

			out = res.getOutputStream();

			StreamUtils.transfer(in, out);

		}catch(RuntimeException e){

			log.info("Caught exception " + e + " while sending placeholder for small thumbs to " + user);

		}catch(IOException e){

			log.info("Caught exception " + e + " while sending placeholder for small thumbs to " + user);

		}finally{

			StreamUtils.closeStream(in);
			StreamUtils.closeStream(out);
		}

		return null;
	}

	public SimpleForegroundModuleResponse getThumb(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ThumbSize thumbSize) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		Gallery gallery = null;

		// check that the requested gallery exist
		if(uriParser.size() != 4 || (gallery = this.galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}else{

			checkAccess(user, gallery);

			String filename = uriParser.get(3).toString();

			File file = new File(gallery.getDirectory() + File.separator + filename);

			if(ImageFileFilter.isValidFilename(filename) && FileUtils.isReadable(file)){

				log.debug("User " + user + " requesting " + thumbSize.toString().toLowerCase() + " thumb of image " + filename + " in gallery " + gallery);

				// check if there are thumbnails created
				Image image = getImage(gallery, file, thumbSize);

				if(image == null){

					//Redirect to "Thumb being generated image"
					redirectToMethod(req, res, "/generating" + thumbSize.toString().toLowerCase());
				}

				if(image.getSmallThumb() != null || image.getMediumThumb() != null){

					try{
						writeThumb(image, thumbSize, res);

					}catch(Exception e){

						log.info("Caught exception " + e + " while sending image " + image + " in gallery " + gallery + " to " + user);
					}

				}else{

					//Image is so small the it doesnt need any thumbs, send original file instead
					sendFile(gallery, file, res, user);
				}

			}else{
				log.info("The image " + filename + " does not exist in gallery " + gallery);
				throw new URINotFoundException(uriParser);
			}
		}

		return null;
	}

	public Image getImage(Gallery gallery, File file, ThumbSize thumbSize) throws SQLException {

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		if(thumbSize == ThumbSize.SMALL){

			query.setExcludedFields(MEDIUM_THUMB_EXCLUDED_FIELD);

		}else{

			query.setExcludedFields(SMALL_THUMB_EXCLUDED_FIELD);
		}

		query.addParameter(imageFilenameParamFactory.getParameter(file.getName()));
		query.addParameter(imageGalleryParamFactory.getParameter(gallery));

		Image image = imageDAO.get(query);

		if(image == null){

			queueTask(new GenerateThumbsTask(gallery, file, this, null));

			return null;

		}else if(file.lastModified() != image.getLastModified()){

			queueTask(new GenerateThumbsTask(gallery, file, this, image.getImageID()));

			return null;

		}else{

			if(thumbSize == ThumbSize.MEDIUM && image.getMediumThumb() == null){

				return getImage(gallery, file, ThumbSize.SMALL);
			}

			return image;
		}
	}

	private void writeThumb(Image image, ThumbSize thumbSize, HttpServletResponse res) throws SQLException, IOException {

		InputStream in = null;
		OutputStream out = null;

		try{

			if(thumbSize == ThumbSize.MEDIUM && image.getMediumThumb() != null){

				in = image.getMediumThumb().getBinaryStream();
				HTTPUtils.setContentLength(image.getMediumThumb().length(), res);

			}else{

				in = image.getSmallThumb().getBinaryStream();
				HTTPUtils.setContentLength(image.getSmallThumb().length(), res);
			}

			res.setContentType("image/jpeg");
			res.setHeader("Content-Disposition", "inline; filename=\"" + FileUtils.toValidHttpFilename(image.getFilename()) + "\"");

			out = res.getOutputStream();

			StreamUtils.transfer(in, out);

		}finally{

			StreamUtils.closeStream(in);
			StreamUtils.closeStream(out);
		}

	}

	@WebPublic(alias = "updategallerythumbs")
	public SimpleForegroundModuleResponse updateGalleryThumbs(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws IOException, SQLException, URINotFoundException, AccessDeniedException {

		this.checkAdminAccess(user);

		Gallery gallery = null;

		if(uriParser.size() < 3 || (gallery = galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);
		}

		log.info("User updating thumbs for gallery " + gallery);
		this.generateGalleryThumbs(gallery);

		this.redirectToMethod(req, res, "/#" + gallery.getAlias());

		return null;
	}

	private void generateGalleryThumbs(Gallery gallery) throws SQLException {

		File dir = new File(gallery.getDirectory());

		if(FileUtils.isReadable(dir)){

			log.info("Directory of gallery " + gallery + " does not exist or is not readable, aborting update of thumbnails");
			return;
		}

		File[] files = dir.listFiles(FILE_FILTER);

		if(files.length > 0){

			Connection connection = null;

			try{
				connection = imageDAO.getDataSource().getConnection();

				for(File file : files){

					if(!file.canRead()){

						continue;
					}

					HighLevelQuery<Image> query = new HighLevelQuery<Image>();

					query.setExcludedFields(ALL_THUMBS_EXCLUDED);
					query.addParameter(imageFilenameParamFactory.getParameter(file.getName()));
					query.addParameter(imageGalleryParamFactory.getParameter(gallery));

					Image image = imageDAO.get(query, connection);

					if(image == null){

						queueTask(new GenerateThumbsTask(gallery, file, this, null));

					}else if(file.lastModified() != image.getLastModified()){

						queueTask(new GenerateThumbsTask(gallery, file, this, image.getImageID()));
					}
				}

			}finally{

				DBUtils.closeConnection(connection);
			}
		}

		clearOldThumbnails(gallery, files);
	}

	@WebPublic(toLowerCase = true)
	public SimpleForegroundModuleResponse deleteImage(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws AccessDeniedException, URINotFoundException, UnsupportedEncodingException, IOException, SQLException {

		this.checkAdminAccess(user);

		Gallery gallery = null;

		if(uriParser.size() != 3 || (gallery = this.galleryByAliasCache.get(uriParser.get(2))) == null){
			throw new URINotFoundException(uriParser);
		}

		String[] filenames = req.getParameterValues("file");

		if(filenames != null && filenames.length != 0){

			ArrayList<String> deletedFiles = new ArrayList<String>();

			log.info("User " + user + " deleting files " + StringUtils.toCommaSeparatedString(filenames) + " from gallery " + gallery);

			for(String filename : filenames){

				File file = new File(gallery.getDirectory() + File.separator + filename);

				if(!gallery.getDirectory().startsWith(file.getAbsolutePath())){

					log.warn("Possible URL attack from user " + user + ", specified file/path " + filename + " points outside gallery directory " + gallery.getDirectory() + " of gallery " + gallery + ". Ignoring request!");
					break;

				}else if(file.isDirectory()){

					log.info("File " + filename + " is a directory, ignoring");
					continue;

				}else if(file.delete()){

					log.info("User " + user + " deleted file " + filename + " from gallery " + gallery);
					deletedFiles.add(filename);
				}
			}

			if(!deletedFiles.isEmpty()){

				HighLevelQuery<Image> query = new HighLevelQuery<Image>();

				query.addParameter(imageGalleryParamFactory.getParameter(gallery));
				query.addParameter(imageFilenameParamFactory.getWhereInParameter(deletedFiles));

				imageDAO.delete(query);
			}
		}

		res.sendRedirect(this.getModuleURI(req) + "/gallery/" + URLEncoder.encode(gallery.getAlias(), "UTF-8"));

		return null;
	}

	@WebPublic(alias = "upload")
	public SimpleForegroundModuleResponse uploadImages(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws SQLException, URINotFoundException, AccessDeniedException, UnsupportedEncodingException, IOException {

		Gallery gallery = null;

		if(uriParser.size() != 3 || (gallery = this.galleryByAliasCache.get(uriParser.get(2))) == null){

			throw new URINotFoundException(uriParser);

		}else if(!AccessUtils.checkAccess(user, this) || !checkUploadAccess(user, gallery)){

			throw new AccessDeniedException("Upload access to gallery " + gallery + " denied!");
		}

		ValidationException validationException = null;

		if(req.getMethod().equalsIgnoreCase("POST")){

			MultipartRequest requestWrapper = null;

			try{
				requestWrapper = new MultipartRequest(this.ramThreshold * BinarySizes.KiloByte, this.maxRequestSize * BinarySizes.MegaByte, this.maxFileSize * BinarySizes.MegaByte, req);

				if(requestWrapper.getFileCount() == 0){

					throw new ValidationException(new ValidationError("files", ValidationErrorType.RequiredField));
				}

				log.info("User " + user + " uploading " + requestWrapper.getFileCount() + " to gallery " + gallery + "...");

				for(FileItem fileItem : requestWrapper.getFiles()){

					if(!FILE_FILTER.accept(fileItem)){

						log.info("Skipping file " + fileItem.getName() + " to gallery " + gallery + " uploaded by user " + user + " due to wrong file format");
						continue;
					}

					File file = new File(gallery.getDirectory() + File.separator + fileItem.getName());

					if(file.exists()){

						log.info("Skipping file " + fileItem.getName() + " to gallery " + gallery + " uploaded by user " + user + ", a file with this name already exists");
						continue;
					}

					try{
						fileItem.write(file);
					}catch(Exception e){
						log.error("Error adding file " + fileItem.getName() + " to gallery " + gallery + " uploaded by user " + user);
					}
				}

				res.sendRedirect(this.getModuleURI(req) + "/gallery/" + URLEncoder.encode(gallery.getAlias(), "UTF-8"));

				// create thumbs
				generateGalleryThumbs(gallery);

				return null;

			}catch(ValidationException e){
				validationException = e;
			}catch(FileSizeLimitExceededException e){
				validationException = new ValidationException(new ValidationError("FileSizeLimitExceeded"));
			}catch(SizeLimitExceededException e){
				validationException = new ValidationException(new ValidationError("RequestSizeLimitExceeded"));
			}catch(FileUploadException e){
				validationException = new ValidationException(new ValidationError("UnableToParseRequest"));
			}finally{
				if(requestWrapper != null){
					requestWrapper.deleteFiles();
				}
			}
		}

		log.info("User " + user + " requesting upload form for gallery " + gallery);

		Document doc = createDocument(req, uriParser, user);

		Element uploadFilesElement = doc.createElement("UploadFiles");
		doc.getFirstChild().appendChild(uploadFilesElement);

		uploadFilesElement.appendChild(gallery.toXML(doc));

		XMLUtils.appendNewElement(doc, uploadFilesElement, "maxFileSize", maxFileSize);
		XMLUtils.appendNewElement(doc, uploadFilesElement, "maxRequestSize", maxRequestSize);
		XMLUtils.appendNewElement(doc, uploadFilesElement, "basePathSet", this.basePath != null);

		if(validationException != null){

			uploadFilesElement.appendChild(validationException.toXML(doc));
		}

		return new SimpleForegroundModuleResponse(doc, gallery.getName(), getDefaultBreadcrumb(), getGalleryBreadcrumb(gallery), new Breadcrumb(this, uploadFilesText, "/upload/" + gallery.getAlias()));
	}

	@WebPublic(alias = "updateallthumbs")
	public SimpleForegroundModuleResponse regenerateThumbs(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {

		this.checkAdminAccess(user);

		Collection<Gallery> galleries = galleryByIDCache.values();

		for(Gallery gallery : galleries){
			this.generateGalleryThumbs(gallery);
		}

		this.redirectToDefaultMethod(req, res);

		return null;
	}

	@WebPublic(alias = "thumbavailable")
	public SimpleForegroundModuleResponse isThumbnailAvaiable(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser) throws Exception {



		return null;
	}

	private void clearOldThumbnails(Gallery gallery, File[] files) throws SQLException {

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		query.addParameter(imageGalleryParamFactory.getParameter(gallery));

		if(files.length > 0){

			ArrayList<String> filenameList = new ArrayList<String>();

			for(File file : files){

				filenameList.add(file.getName());
			}

			query.addParameter(imageFilenameParamFactory.getWhereNotInParameter(filenameList));
		}

		Integer deletedRows = imageDAO.delete(query);

		if(deletedRows > 0){

			log.info("Deleted " + deletedRows + " old thumbnails from gallery " + gallery);
		}
	}

	public Integer getImageID(Gallery gallery, File file, TransactionHandler transactionHandler) throws SQLException {

		HighLevelQuery<Image> query = new HighLevelQuery<Image>();

		query.setExcludedFields(ALL_THUMBS_EXCLUDED);
		query.addParameter(imageFilenameParamFactory.getParameter(file.getName()));
		query.addParameter(imageGalleryParamFactory.getParameter(gallery));

		Image image = imageDAO.get(query, transactionHandler);

		if(image != null){

			return image.getImageID();
		}

		return null;
	}

	private synchronized void queueTask(GenerateThumbsTask task) {

		if(!threadPoolExecutor.getQueue().contains(task)){

			threadPoolExecutor.execute(task);
		}
	}

	public void checkAdminAccess(User user) throws AccessDeniedException {

		if(!AccessUtils.checkAccess(user, this)){
			throw new AccessDeniedException("Administration permission denied");
		}
	}

	public void checkAccess(User user, Gallery gallery) throws AccessDeniedException {

		if(!AccessUtils.checkAccess(user, gallery) && !AccessUtils.checkAccess(user, this)){
			throw new AccessDeniedException("Permission to gallery " + gallery + " denied");
		}
	}

	private Breadcrumb getGalleryBreadcrumb(Gallery gallery) {

		return new Breadcrumb(this, gallery.getName(), gallery.getDescription(), "/gallery/" + gallery.getAlias());
	}

	private Breadcrumb getImageBreadcrumb(Gallery gallery, String filename) {

		return new Breadcrumb(this, filename, "/image/" + gallery.getAlias() + "/" + filename);
	}

	private boolean checkUploadAccess(User user, Gallery gallery) {

		if(user == null){

			return false;

		}else if(!CollectionUtils.isEmpty(gallery.getAllowedUploadUserIDs()) && gallery.getAllowedUploadUserIDs().contains(user.getUserID())){

			return true;

		}else if(!CollectionUtils.isEmpty(gallery.getAllowedUploadGroupIDs()) && !CollectionUtils.isEmpty(user.getGroups())){

			for(Group group : user.getGroups()){

				if(group.isEnabled() && gallery.getAllowedUploadGroupIDs().contains(group.getGroupID())){
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Document createDocument(HttpServletRequest req, URIParser uriParser, User user) {

		Document doc = XMLUtils.createDomDocument();
		Element document = doc.createElement("Document");
		document.appendChild(RequestUtils.getRequestInfoAsXML(doc, req, uriParser));
		document.appendChild(this.moduleDescriptor.toXML(doc));
		document.appendChild(XMLUtils.createElement("isAdmin", AccessUtils.checkAccess(user, this), doc));
		doc.appendChild(document);
		return doc;
	}

	@Override
	public String getTitlePrefix() {

		return moduleDescriptor.getName();
	}

	@Override
	public boolean allowsAdminAccess() {

		return false;
	}

	@Override
	public boolean allowsUserAccess() {

		return false;
	}

	@Override
	public boolean allowsAnonymousAccess() {

		return false;
	}

	@Override
	public Collection<Integer> getAllowedGroupIDs() {

		return this.adminGroupIDs;
	}

	@Override
	public Collection<Integer> getAllowedUserIDs() {

		return this.adminUserIDs;
	}

	public Gallery getGallery(Integer galleryID) {

		return galleryByIDCache.get(galleryID);
	}

	public Integer getSmallThumbMaxHeight() {

		return smallThumbMaxHeight;
	}

	public void setSmallThumbMaxHeight(Integer smallImageMaxHeight) {

		this.smallThumbMaxHeight = smallImageMaxHeight;
	}

	public Integer getSmallThumbMaxWidth() {

		return smallThumbMaxWidth;
	}

	public void setSmallThumbMaxWidth(Integer smallImageMaxWidth) {

		this.smallThumbMaxWidth = smallImageMaxWidth;
	}

	public Integer getMediumThumbMaxHeight() {

		return mediumThumbMaxHeight;
	}

	public void setMediumThumbMaxHeight(Integer mediumImageMaxHeight) {

		this.mediumThumbMaxHeight = mediumImageMaxHeight;
	}

	public Integer getMediumThumbMaxWidth() {

		return mediumThumbMaxWidth;
	}

	public void setMediumThumbMaxWidth(Integer mediumImageMaxWidth) {

		this.mediumThumbMaxWidth = mediumImageMaxWidth;
	}

	public AnnotatedDAO<Image> getImageDAO() {

		return imageDAO;
	}

	public String getAddGalleryText() {

		return addGalleryText;
	}

	public String getUpdateGalleryText() {

		return updateGalleryText;
	}

	public String getBasePath() {

		return basePath;
	}

	public SectionDescriptor getSectionDescriptor() {

		return sectionInterface.getSectionDescriptor();
	}

	public ForegroundModuleDescriptor getModuleDescriptor() {

		return moduleDescriptor;
	}
}
