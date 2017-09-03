package com.example.carlosjoseanguiano.multimedia_bsd;
import java.io.Serializable;

/**
 * Created by Carlos Anguiano on 29/06/17.
 * For more info contact: c.joseanguiano@gmail.com
 */
class DataPicture implements Serializable {
    private String filePath;
    private String fileType;
    private String bucket;

    String getFilePath() {
        return filePath;
    }

    void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    String getFileType() {
        return fileType;
    }

    void setFileType(String fileType) {
        this.fileType = fileType;
    }

    String getBucket() {
        return bucket;
    }

    void setBucket(String bucket) {
        this.bucket = bucket;
    }

}
