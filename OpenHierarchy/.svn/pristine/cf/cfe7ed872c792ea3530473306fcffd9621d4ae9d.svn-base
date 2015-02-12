package se.unlogic.hierarchy.foregroundmodules.gallery;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;

import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Gallery;
import se.unlogic.hierarchy.foregroundmodules.gallery.beans.Image;
import se.unlogic.standardutils.dao.AnnotatedDAO;
import se.unlogic.standardutils.dao.TransactionHandler;
import se.unlogic.standardutils.image.ImageUtils;
import se.unlogic.standardutils.time.TimeUtils;


public class GenerateThumbsTask implements Runnable {

	protected Logger log = Logger.getLogger(this.getClass());

	private Gallery gallery;
	private File file;
	private GalleryModule galleryModule;
	private Integer imageID;

	public GenerateThumbsTask(Gallery gallery, File file, GalleryModule galleryModule, Integer imageID) {

		super();
		this.gallery = gallery;
		this.file = file;
		this.galleryModule = galleryModule;
		this.imageID = imageID;
	}

	@Override
	public void run() {

		try{

			//Check if gallery is up to date
			Gallery freshGallery = galleryModule.getGallery(gallery.getGalleryID());

			if(freshGallery == null){

				log.info("Gallery " + gallery + " has been deleted, aborting generating of thumbnails for file " + file.getName());

			}else if(!gallery.getDirectory().equals(freshGallery.getDirectory())){

				log.info("Gallery " + gallery + " has been moved, aborting generating of thumbnails for file " + file.getName());

			}else if(!file.exists()){

				log.info("File " + file.getName() + " in gallery " + gallery + " no longer exists, aborting generating of thumbnails");

			}else if(!file.canRead()){

				log.info("File " + file.getName() + " in gallery " + gallery + " is not readable, aborting generating of thumbnails");

			}else{

				log.info("Generating thumbnails for file " + file + " in gallery " + gallery + "...");

				long startTime = System.currentTimeMillis();

				Image image = new Image();
				image.setFilename(file.getName());
				image.setGallery(gallery);
				image.setLastModified(file.lastModified());

				BufferedImage bufferedImage = ImageUtils.getImage(file);

				if(bufferedImage.getWidth() > galleryModule.getMediumThumbMaxWidth() || bufferedImage.getHeight() > galleryModule.getMediumThumbMaxHeight()){

					bufferedImage = ImageUtils.scaleImage(bufferedImage, galleryModule.getMediumThumbMaxHeight(), galleryModule.getMediumThumbMaxWidth(), java.awt.Image.SCALE_SMOOTH, BufferedImage.TYPE_INT_RGB);

					image.setMediumThumb(new SerialBlob(ImageUtils.convertImage(bufferedImage, ImageUtils.JPEG)));
				}

				if(bufferedImage.getWidth() > galleryModule.getSmallThumbMaxWidth() || bufferedImage.getHeight() > galleryModule.getSmallThumbMaxHeight()){

					bufferedImage = ImageUtils.scaleImage(bufferedImage, galleryModule.getSmallThumbMaxHeight(), galleryModule.getMediumThumbMaxWidth(), java.awt.Image.SCALE_SMOOTH, BufferedImage.TYPE_INT_RGB);

					image.setSmallThumb(new SerialBlob(ImageUtils.convertImage(bufferedImage, ImageUtils.JPEG)));
				}

				AnnotatedDAO<Image> imageDAO = galleryModule.getImageDAO();
				TransactionHandler transactionHandler = null;

				try{
					transactionHandler = imageDAO.createTransaction();

					//Check if there was a previous image available in DB for this file when it was queued and if the image still exists in DB
					if(imageID != null && update(image,imageID,transactionHandler,imageDAO)){

						return;
					}

					imageID = galleryModule.getImageID(gallery, file, transactionHandler);

					//Check if an image has been added to the  DB for this file since it was queued
					if(imageID != null && update(image,imageID,transactionHandler,imageDAO)){

						return;
					}

					image.setImageID(null);

					imageDAO.add(image);

					transactionHandler.commit();

				}finally{

					TransactionHandler.autoClose(transactionHandler);
				}

				log.info("Generated thumbnails for file " + file + " in gallery " + gallery + " in" + TimeUtils.millisecondsToString(System.currentTimeMillis() - startTime) + " ms");
			}

		}catch(Throwable t){

			log.error("Error generating thumbnails for file " + file.getName() + " in gallery " + gallery);
		}
	}

	private static boolean update(Image image, Integer imageID, TransactionHandler transactionHandler, AnnotatedDAO<Image> imageDAO) throws SQLException {

		image.setImageID(imageID);

		if(imageDAO.beanExists(image, transactionHandler)){

			imageDAO.update(image, transactionHandler, null);

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((gallery == null) ? 0 : gallery.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		GenerateThumbsTask other = (GenerateThumbsTask)obj;
		if(file == null){
			if(other.file != null){
				return false;
			}
		}else if(!file.equals(other.file)){
			return false;
		}
		if(gallery == null){
			if(other.gallery != null){
				return false;
			}
		}else if(!gallery.equals(other.gallery)){
			return false;
		}
		return true;
	}

}
