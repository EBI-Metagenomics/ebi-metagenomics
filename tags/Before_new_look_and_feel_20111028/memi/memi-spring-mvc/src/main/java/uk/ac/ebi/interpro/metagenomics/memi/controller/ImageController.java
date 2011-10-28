package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Streams images from a specified path and allows to display these images within a JSP.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/" + ImageController.VIEW_NAME)
public class ImageController extends AbstractController {
    private final Log log = LogFactory.getLog(ImageController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "getImage";


    @RequestMapping(method = RequestMethod.GET)
    public void getImage(HttpServletResponse response,
                         @RequestParam(value = "imageName", required = true) String imageName,
                         @RequestParam(value = "imageType", required = true) String imageType,
                         @RequestParam(value = "dir", required = true) String directoryName) {
        long t0 = System.currentTimeMillis();

        if (imageName.trim().length() < 1 && imageType.trim().length() < 1) {
            log.warn("Empty image name and image type specified. Creation of output stream stopped!");
            return;
        }

        directoryName = directoryName.toUpperCase().replace('.', '_');
        imageName = directoryName + imageName;
        log.info("Creating servlet output stream for image " + imageName);
        File inputFile = new File(propertyContainer.getPathToAnalysisDirectory() + directoryName + '/' + imageName);

        //TODO: Write test
        byte[] imageData = getImageData(inputFile, imageType.toUpperCase());

        if (null == imageData || imageData.length < 1) {
            log.warn("Image content is not available!");
            return;
        }
        //set default content type
        //TODO: Write test
        String contentType = getContentType(imageType);
        streamImageData(response, imageData, contentType);

        long t = System.currentTimeMillis() - t0;
        log.info("Finished. Creation of image output stream took " + t + " milliseconds.");
    }

    protected void streamImageData(HttpServletResponse response, byte[] imageData, String contentType) {
        ServletOutputStream servletoutputStream = null;
        try {
            log.info("Streaming (" + contentType + ") size "
                    + imageData.length + " bytes!");
            response.setContentType(contentType);
            response.setContentLength(imageData.length);
            servletoutputStream = response.getOutputStream();
            servletoutputStream.write(imageData);
            servletoutputStream.flush();
        } catch (IOException ex) {
            log.error("Unable to stream image data!", ex);
        } finally {
            if (servletoutputStream != null) {
                try {
                    servletoutputStream.close();
                } catch (IOException e) {
                    log.error("Unable to close servlet output stream!", e);
                }
            }
        }
    }

    protected byte[] getImageData(File inputFile, String imageFormat) {
        BufferedImage image = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            image = ImageIO.read(inputFile);
            ImageIO.write(image, imageFormat, os);
            return os.toByteArray();
        } catch (IOException e) {
            log.warn("Unable to stream input file " + inputFile.getAbsolutePath() + " and write it to a byte array.", e);
            return new byte[0];
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                log.error("Unable to close ImageInputStream!", e);
            }
        }
    }

    /**
     * Supports the following image types at the moment:<br>
     * JPEG, GIF and PNG.
     *
     * @param imageType
     */
    protected String getContentType(String imageType) {
        String result = "image/png";
        if (imageType != null) {
            imageType = imageType.trim();
            if (imageType.equalsIgnoreCase("jpeg")) {
                result = "image/jpeg";
            } else if (imageType.equalsIgnoreCase("gif")) {
                result = "image/gif";
            }
        }
        return result;
    }

    @Override
    protected String getModelViewName() {
        return VIEW_NAME;
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity obj) {
        return null;
    }
}