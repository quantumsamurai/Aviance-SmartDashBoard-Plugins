/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modules;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import edu.wpi.first.wpijavacv.WPIImage;

/**
 *
 * @author pkb
 */
public class WPIImageWrapper extends WPIImage {

    public WPIImageWrapper(WPIImage wpiImage) {
        super(wpiImage.getBufferedImage());
    }

    public IplImage getIplImage() {
        return image;
    }
}
