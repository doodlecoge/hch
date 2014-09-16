package com.hch.utils.io;

import com.hch.utils.HchException;

import java.io.*;

/**
 * Created by huaiwang on 09/16/2014.
 */
public class FileUtils {
    public static void saveAs(InputStream is, String path) throws IOException {
        File file = new File(path);
        if (file.getParentFile() != null && !file.getParentFile().exists())
            throw new HchException("path not exists");
        BufferedInputStream bis = new BufferedInputStream(is);
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int b;
        while ((b = bis.read()) != -1) {
            bos.write(b);
        }

        bos.close();
        bis.close();
    }
}
