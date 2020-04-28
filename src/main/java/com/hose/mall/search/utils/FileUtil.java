package com.hose.mall.search.utils;

import java.io.*;

/**
 * 文件读取
 *
 * @ClassName：FileUtil
 */
public class FileUtil {

    public static String readJsonDefn(String url) {
        StringBuffer bufferJSON = new StringBuffer();
        InputStream input;
        try {
            input = ClassLoaderUtil.getResourceAsStream(url, FileUtil.class);
            DataInputStream inputStream = new DataInputStream(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                bufferJSON.append(line);
            }
            inputStream.close();
            br.close();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bufferJSON.toString();
    }

}
