package com.bk.photogobuy.service;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import java.util.List;

/**
 * Created by Dell on 24-Jun-17.
 */
public class CalculateFeatureVector {


    public List<Double> extractFeatureVt(Bitmap bitmap) {

        Mat imageMat = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8U);
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, imageMat);

        ColorDescriptor descriptor = new ColorDescriptor(new MatOfInt(8, 12, 3));
        List<Double> feature = descriptor.describe(imageMat);

        return feature;
    }





}
