package com.example.pocdynamictext.helper;

import android.os.Environment;

import java.io.File;

public class FileHelper {

    public static File getSaveFilePath(String fileName) {
        File dir = new File(Environment.getExternalStorageDirectory(), "Fonts");
        dir.mkdirs();
        File file = new File(dir, fileName);

        return file;
    }
}
