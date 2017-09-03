//package com.example.carlosjoseanguiano.multimedia_bsd;
//import android.graphics.Bitmap;
//import android.net.Uri;
//
//import com.squareup.picasso.Transformation;
//
///**
// * Created by: Carlos Anguiano on 14/08/17.
// * For more info contact: c.joseanguiano@gmail.com
// */
//
//class BitmapTransform implements Transformation {
//
//    private final float maxWidth;
//    private final float maxHeight;
//    private Uri uri;
//
//    BitmapTransform(float maxWidth, float maxHeight, Uri uri) {
//        this.maxWidth = maxWidth;
//        this.maxHeight = maxHeight;
//        this.uri = uri;
//    }
//
//    @Override
//    public Bitmap transform(Bitmap source) {
//        int targetWidth, targetHeight;
//        double aspectRatio;
//
//        if (source.getWidth() > source.getHeight()) {
//            targetWidth = (int) maxWidth;
//            aspectRatio = (double) source.getHeight() / (double) source.getWidth();
//            targetHeight = (int) (targetWidth * aspectRatio);
//        } else {
//            targetHeight = (int) maxHeight;
//            aspectRatio = (double) source.getWidth() / (double) source.getHeight();
//            targetWidth = (int) (targetHeight * aspectRatio);
//        }
//
////        Bitmap result = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(uri.getPath()), targetWidth, targetHeight, true);
//
////        Bitmap result = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(uri.getPath()), 100, 100, true);
//        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
////        Bitmap result = Bitmap.createScaledBitmap(source, (int) maxWidth, (int) maxHeight, false);
//
//        if (result != source) {
//            source.recycle();
//        }
//        return result;
//    }
//
//    @Override
//    public String key() {
//        return maxWidth + "x" + maxHeight;
//    }
//
//}