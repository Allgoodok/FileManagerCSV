package com.vlad.model;

import java.util.Date;

public class CSVDocument implements IDocument {

    private String title;
    private String uploader;
    private Date date;
    private String pathToFile;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUploader() {
        return uploader;
    }
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getPathToFile() {
        return pathToFile;
    }
    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
