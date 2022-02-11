package com.mega.wsstatusdownloader;

import android.os.Environment;

import java.io.File;

public class Constant {

    public  static  final  String FOLDER_NAME = "/WhatsApp/";
    public static final String NEW_FOLDER_NAME = "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";

    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
            File.separator + "WhatsApp/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

    public static final String DOWNLOAD_FOLDER_NAME = "/WS StatusDownloader/";
    public static final String SAVE_FOLDER_NAME = "/WS StatusDownloader/";

}










