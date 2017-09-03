package com.example.carlosjoseanguiano.multimedia_bsd;
import java.util.ArrayList;

/**
 * Created by Carlos Anguiano on 29/06/17.
 * For more info contact: c.joseanguiano@gmail.com
 */
class DataPicturesAlbum {
    private String folder;
    private String type;
    private String imagePaths;
    private ArrayList<String>pathSize;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    String getImagePaths() {
        return imagePaths;
    }

    void setImagePaths(String imagePaths) {
        this.imagePaths = imagePaths;
    }

    ArrayList<String> getPathSize() {
        return pathSize;
    }

    void setPathSize(ArrayList<String> pathSize) {
        this.pathSize = pathSize;
    }
}
