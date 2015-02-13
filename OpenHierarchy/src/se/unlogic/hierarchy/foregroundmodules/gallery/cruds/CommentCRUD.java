package se.unlogic.hierarchy.foregroundmodules.gallery.cruds;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.unlogic.hierarchy.core.beans.User;
import se.unlogic.hierarchy.core.exceptions.AccessDeniedException;
import se.unlogic.hierarchy.core.exceptions.URINotFoundException;
import se.unlogic.hierarchy.core.interfaces.ForegroundModuleResponse;
import se.unlogic.hierarchy.core.utils.AccessUtils;
import se.unlogic.hierarchy.core.utils.IntegerBasedCRUD;
import se.unlogic.hierarchy.foregroundmodules.gallery.GalleryModule;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Comment;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Image;
import se.unlogic.standardutils.dao.CRUDDAO;
import se.unlogic.standardutils.time.TimeUtils;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.webutils.http.URIParser;
import se.unlogic.webutils.populators.annotated.AnnotatedRequestPopulator;


public class CommentCRUD extends IntegerBasedCRUD<Comment, GalleryModule> {

	public static final AnnotatedRequestPopulator<Comment> POPULATOR = new AnnotatedRequestPopulator<Comment>(Comment.class);

	public CommentCRUD(CRUDDAO<Comment, Integer> crudDAO, GalleryModule callback) {

		super(crudDAO, POPULATOR, "Comment", "comment", "", callback);
	}

	@Override
	protected Comment populateFromAddRequest(HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, Exception {

		Comment comment = super.populateFromAddRequest(req, user, uriParser);

		comment.setAdded(TimeUtils.getCurrentTimestamp());
		comment.setPoster(user);

		comment.setImage((Image)req.getAttribute("image"));

		return comment;
	}

	@Override
	protected Comment populateFromUpdateRequest(Comment bean, HttpServletRequest req, User user, URIParser uriParser) throws ValidationException, Exception {

		Comment comment = super.populateFromAddRequest(req, user, uriParser);

		comment.setUpdated(TimeUtils.getCurrentTimestamp());
		comment.setEditor(user);

		return comment;
	}

	@Override
	protected void checkAddAccess(User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		Image image = (Image)req.getAttribute("image");

		callback.checkAccess(user,image.getGallery());
	}

	@Override
	protected void checkUpdateAccess(Comment bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {


		callback.checkAccess(user,bean.getImage().getGallery());

		if(!((bean.getPoster() != null && bean.getPoster().equals(user)) || AccessUtils.checkAccess(user, callback))){

			throw new AccessDeniedException("Comment update access denied");
		}
	}

	@Override
	protected void checkDeleteAccess(Comment bean, User user, HttpServletRequest req, URIParser uriParser) throws AccessDeniedException, URINotFoundException, SQLException {

		if(!AccessUtils.checkAccess(user, callback)){

			throw new AccessDeniedException("Comment delete access denied");
		}
	}

	@Override
	public ForegroundModuleResponse showAddForm(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, ValidationException validationException) throws Exception {

		return callback.showImage(req, res, user, uriParser, validationException);
	}

	@Override
	public ForegroundModuleResponse list(HttpServletRequest req, HttpServletResponse res, User user, URIParser uriParser, List<ValidationError> validationErrors) throws Exception {

		return callback.listGalleries(req, res, user, uriParser, validationErrors);
	}

	@Override
	protected void redirectToListMethod(HttpServletRequest req, HttpServletResponse res, Comment bean) throws Exception {

		callback.redirectToMethod(req, res, "/gallery/" + URLEncoder.encode(bean.getImage().getGallery().getAlias(), "UTF-8") + "/" + URLEncoder.encode(bean.getImage().getFilename(), "UTF-8"));
	}
}
