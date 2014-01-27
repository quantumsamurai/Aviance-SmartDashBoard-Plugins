/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

/**
 *
 * @author Darvin
 */

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.*;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpijavacv.*;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 * @author Darvin
 */
public class VisionICE extends WPICameraExtension 
{
    @Override
    public WPIImage processImage(WPIColorImage rawImage) 
    {
        
        // Variables for Robot
        int x = 0;
        int y = 0;
        int h = 0;
        int w = 0;
        
        // Get IplImage from WPIColor image
     WPIImageWrapper wrapper;
     wrapper = new WPIImageWrapper(rawImage);
        IplImage iplImage = wrapper.getIplImage();
        final CanvasFrame canvas = new CanvasFrame("My Image", 1);
        // Create New images for processing
        IplImage HSVimage   = IplImage.create(iplImage.cvSize(), opencv_core.IPL_DEPTH_8U, 3);
        IplImage inRangeMat = IplImage.create(iplImage.cvSize(), opencv_core.IPL_DEPTH_8U, 1);
        
        // Get HSV image
        opencv_imgproc.cvCvtColor(iplImage, HSVimage, opencv_imgproc.CV_BGR2HSV);
        
        
        // Threshold Image for white          
        opencv_core.cvInRangeS(HSVimage, opencv_core.cvScalar(46.0, 25.0, 89.0, 0.0), opencv_core.cvScalar(62.0, 255.0, 255.0, 0.0), inRangeMat);
         // cvScalar(blue, green, red, unused)
        // Filter image
        opencv_imgproc.cvErode(inRangeMat, inRangeMat, null, 2);
        opencv_imgproc.cvDilate(inRangeMat, inRangeMat, null, 4);
        opencv_imgproc.cvErode(inRangeMat, inRangeMat, null, 2);
               
        // Create Storage for the Contours
        opencv_core.CvMemStorage contours = opencv_core.CvMemStorage.create();
        opencv_core.CvSeq hierarchy = new opencv_core.CvSeq();
        
        // Find the Rectangles
        opencv_imgproc.cvFindContours(inRangeMat, contours, hierarchy, com.googlecode.javacpp.Loader.sizeof(opencv_core.CvContour.class), opencv_imgproc.CV_RETR_TREE, opencv_imgproc.CV_CHAIN_APPROX_SIMPLE, opencv_core.cvPoint(0, 0));
        
        // Process the rectangles
        for(int idx = 0; idx < hierarchy.total(); idx++)
        {
            opencv_core.CvRect rect = opencv_imgproc.cvBoundingRect(hierarchy, idx);
            x = rect.x(); y = rect.y(); h = rect.height(); w = rect.width();
            opencv_core.cvRectangle(iplImage, opencv_core.cvPoint(x, y), 
                    opencv_core.cvPoint(x + w, y + h), opencv_core.CvScalar.RED, 1, opencv_core.CV_AA, 0);
            rect.deallocate();
        }
                       canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                       canvas.showImage(HSVimage);
 
        
        contours.deallocate();
        hierarchy.deallocate();
        
        // Send the data to the robot
        ITable table = Robot.getTable();
        
        table.putNumber("X", x);
        table.putNumber("Y", y);
        table.putNumber("Width", w);
        table.putNumber("Height", h);

       
        
        // Put IplImage into a WPIColorImag
        return new WPIColorImage(iplImage.getBufferedImage());
    }
}