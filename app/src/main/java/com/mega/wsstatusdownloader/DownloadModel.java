package com.mega.wsstatusdownloader;

import android.net.Uri;

public class DownloadModel {
    private String downloadName,downloadPath,downloadFilename;
    private Uri downloadUri;

    public DownloadModel() {
    }

    public DownloadModel(String downloadName, String downloadPath, String downloadFilename, Uri downloadUri) {
        this.downloadName = downloadName;
        this.downloadPath = downloadPath;
        this.downloadFilename = downloadFilename;
        this.downloadUri = downloadUri;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getDownloadFilename() {
        return downloadFilename;
    }

    public void setDownloadFilename(String downloadFilename) {
        this.downloadFilename = downloadFilename;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
    }
}
