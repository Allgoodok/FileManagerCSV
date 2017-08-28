package com.vlad.model;

import java.util.Date;

public interface IDocument {

    String getTitle();
    void setTitle(String title);
    String getUploader();
    void setUploader(String uploader);
    Date getDate();
    void setDate(Date date);
    void setPathToFile(String pathToFile);
}
