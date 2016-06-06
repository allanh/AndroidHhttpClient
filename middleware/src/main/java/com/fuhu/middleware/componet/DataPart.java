package com.fuhu.middleware.componet;


import java.io.File;

/**
 * Simple data container use for passing byte file
 */
public class DataPart {
    private String fileName;
    private String type;
    private File file;

    /**
     * Default data part
     */
    public DataPart(File file) {
        if (file != null) {
            this.fileName = file.getName();
        }
        this.file = file;
    }

    /**
     * Constructor with mime data type
     * @param mimeType mime data like "image/jpeg"
     */
    public DataPart(File file , String mimeType) {
        if (file != null) {
            this.fileName = file.getName();
        }
        this.file = file;
        this.type = mimeType;
    }

    /**
     * Getter file name.
     *
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter file name.
     *
     * @param fileName string file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Getter mime type.
     *
     * @return mime type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter mime type.
     *
     * @param type mime type
     */
    public void setType(String type) {
        this.type = type;
    }
}