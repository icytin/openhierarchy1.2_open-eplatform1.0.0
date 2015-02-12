package se.unlogic.hierarchy.foregroundmodules.gallery.cruds;

import java.io.File;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.AccessDeniedException;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.IntegerBasedCRUD;
import se.unlogic.hierarchy.foregroundmodules.gallery.GalleryModule;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Gallery;
import se.unlogic.standardutils.dao.CRUDDAO;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;

public class GalleryCRUD extends IntegerBasedCRUD<Gallery, GalleryModule> {

	public static final AnnotatedRequestPopulator<Gallery> POPULATOR = new AnnotatedRequestPopulator<Gallery>(Gallery.class);

	public GalleryCRUD(CRUDDAO<Gallery, Integer> crudDAO, GalleryModule callback) {

		super(crudDAO, POPULATOR, "Gallery", "gallery", "", callback);
	}

	@Override
	protected void checkAddAccess(User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkAdminAccess(user);
	}

	@Override
	protected void checkUpdateAccess(Gallery bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkAdminAccess(user);
	}

	@Override
	protected void checkDeleteAccess(Gallery bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		callback.checkAdminAccess(user);
	}

	@Override
	public ForegroundModuleResponse list(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, List<ValidationError> validationErrors) throws Exception {

		return callback.listGalleries(req, res, user, uriParser, validationErrors);
	}

	@Override
	protected void redirectToListMethod(HttpServletRequest req, HttpServletResponse res, Gallery bean) throws Exception {

		callback.redirectToMethod(req, res, "/" + URLEncoder.encode(bean.getAlias(), "UTF-8"));
	}

	@Override
	protected String getAddTextPrefix() {

		return callback.getAddGalleryText();
	}

	@Override
	protected String getUpdateTextPrefix() {

		return callback.getUpdateGalleryText();
	}

	@Override
	protected String getBeanName(Gallery bean) {

		return bean.getName();
	}

	@Override
	protected void validateAddPopulation(Gallery bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException {

		//For thread safety
		String basePath = callback.getBasePath();

		File dir = new File(bean.getDirectory());

		if(dir.isAbsolute()){

			if(dir.exists()){

				if(!dir.canRead()){

					throw new ValidationException(new ValidationError("UnableToReadDirectory"));
				}

			}else{

				throw new ValidationException(new ValidationError("DirectoryDoesNotExist"));
			}

		}else if(basePath != null){

			File newDir = new File(basePath + File.separator + bean.getDirectory());

			if(!newDir.getAbsolutePath().startsWith(basePath)){

				log.warn("Possible attack from user " + user + ", the specified directory " + bean.getDirectory() + " points outside of the base path " + basePath);

				throw new ValidationException(new ValidationError("InvalidDirectoryName"));

			}else if(!newDir.mkdirs()){

				throw new ValidationException(new ValidationError("UnableToCreateDirectory"));
			}

			bean.setDirectory(newDir.getAbsolutePath());

		}else{

			throw new ValidationException(new ValidationError("CreatingOfNewDirectoriesDisabled"));
		}
	}

	@Override
	protected void validateUpdatePopulation(Gallery bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, SQLException, Exception {

		super.validateAddPopulation(bean, req, user, uriParser);
	}
}
