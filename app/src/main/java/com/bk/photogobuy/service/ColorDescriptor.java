package com.bk.photogobuy.service;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 23-Jun-17.
 */
public class ColorDescriptor {

    private MatOfInt histSize;

    public ColorDescriptor(MatOfInt bins) {

        histSize = bins;
    }

    public List<Double> describe(Mat image) {

//        convert the image to the HSV color space and initialize
//        the features used to quantify the image
        List<Double> features = new ArrayList<>();
        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HSV);
        int h, w;
        int cX, cY;
        int axesX, axesY;

        h = image.rows();
        w = image.cols();

        cX = (int)(w * 0.5);
        cY = (int)(h * 0.5);

//      divide the image into four rectangles/segments (top-left,
//      top-right, bottom-right, bottom-left)
        int [][] segments = new int [][]{{0, cX, 0, cY}, {cX, w, 0, cY}, {cX, w, cY, h}, {0, cX, cY, h}};

        axesX = (int)(w * 0.75) / 2;
        axesY = (int)(h * 0.75) / 2;

        Mat ellipMask = Mat.zeros(h, w, CvType.CV_8U);
        Imgproc.ellipse(ellipMask, new Point(cX, cY), new Size(axesX, axesY), 0, 0, 360, new Scalar(255), -1);
        Mat hist;

        for (int i = 0; i < segments.length; i++) {

            int startX, endX, startY, endY;
            startX = segments[i][0];
            endX = segments[i][1];
            startY = segments[i][2];
            endY = segments[i][3];

            Mat cornerMask = Mat.zeros(h, w, CvType.CV_8U);
            Imgproc.rectangle(cornerMask, new Point(startX, startY), new Point(endX, endY), new Scalar(255), -1);
            Core.subtract(cornerMask, ellipMask, cornerMask);

             hist = histogram(image, cornerMask);
            addToFeatures(features, hist);
        }

        hist = histogram(image, ellipMask);
        addToFeatures(features, hist);
        return features;
    }

    public Mat histogram(Mat image, Mat mask) {

        // 3D color histogram from the masked region of the inage
        Mat hist = new Mat();
        ArrayList<Mat> list = new ArrayList<>();
        list.add(image);

        Imgproc.calcHist(list, new MatOfInt(0, 1, 2), mask, hist, histSize, new MatOfFloat(0, 180, 0, 256, 0, 256));
        Core.normalize(hist, hist);
        hist.reshape(1, 1);

        return hist;
    }

    public void addToFeatures(List<Double> features, Mat hist) {

        double[] histData = hist.get(hist.rows(), hist.cols());
        for (int j = 0; j < histData.length; j++) {
            features.add(histData[j]);
            Log.e("vt" + features.size(), histData[j] + "");
        }
    }



}
