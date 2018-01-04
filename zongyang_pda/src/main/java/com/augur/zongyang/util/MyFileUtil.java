package com.augur.zongyang.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by yunhu on 2018-01-02.
 */

public class MyFileUtil {
    private static String PATH_ROOT;

    private static String PATH_MAP_CONFIG;
    private static String PATH_MAP_MAP;

    public static String getPathRoot() {
        if (PATH_ROOT == null) {
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
            if (sdCardExist) {
                File sdDir = Environment.getExternalStorageDirectory();
                PATH_ROOT = sdDir.toString();
            } else {
                PATH_ROOT = "";
            }
            PATH_ROOT += "/zongyang/";
        }
        if (!new File(PATH_ROOT).exists()) {
            new File(PATH_ROOT).mkdirs();
        }
        return PATH_ROOT;
    }

    public static String getCrashPath(){
        return getPathRoot() + "crash/";
    }

    public static String getPathFile() {
        if (PATH_MAP_CONFIG == null) {
            PATH_MAP_CONFIG = getPathRoot() + "file";
        }
        return PATH_MAP_CONFIG;
    }

    public static File getFile(String fileName) {
        return new File(getFileName(fileName));
    }

    public static String getFileName(String fileName) {
        String filePathName = getPathFile();
        if (!new File(getPathFile()).exists()) {
            new File(getPathFile()).mkdirs();
        }
        return filePathName + File.separator + fileName;
    }

    public static String saveMyBitmap(Bitmap mBitmap, String bitName)
            throws IOException {
        if (mBitmap == null) {
            return null;
        }
        File f = new File(getPathFile() + "/" + bitName);
        new File(getPathFile()).mkdirs();
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fOut = null;
        fOut = new FileOutputStream(f);
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return f.getPath();
    }

    public static void saveImage(Bitmap mBitmap, String fileName)
            throws IOException {
        File f = new File(getPathFile() + "/" + fileName);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdir();
        }
        if (!f.exists()) {
            f.createNewFile();
        }
        ImageUtil.compressBmpToFile(mBitmap, f);
    }

    public static void saveBmpByName(Bitmap bmp, String fileName)
            throws IOException {
        File f = new File(getPathFile() + "/" + fileName);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdir();
        }
        if (!f.exists()) {
            f.createNewFile();
        }
        ImageUtil.saveBmpToFile(bmp, f);
    }

    public static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }

    // 写在/mnt/sdcard/目录下面的文件

    public static void writeFileSdcard(String fileName, String message) {

        try {
            File file = new File(fileName);
            if (file.exists())
                file.delete();
            FileOutputStream fout = new FileOutputStream(fileName);

            byte[] bytes = message.getBytes();

            fout.write(bytes);

            fout.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    // 读在/mnt/sdcard/目录下面的文件

    public static String readFileSdcard(String fileName) {

        String res = "";

        try {

            FileInputStream fin = new FileInputStream(fileName);

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    // 写文件在./data/data/packageName/files/下面

    public static void writeFileData(Context context, String fileName,
                                     String message) {

        try {

            FileOutputStream fout = context.openFileOutput(fileName,
                    context.MODE_PRIVATE);

            byte[] bytes = message.getBytes();

            fout.write(bytes);

            fout.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    // 读文件在./data/data/packageName/files/下面

    public static String readFileData(Context context, String fileName) {

        String res = "";

        try {

            FileInputStream fin = context.openFileInput(fileName);

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);

            res = EncodingUtils.getString(buffer, "UTF-8");

            fin.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    public static boolean isFilePathExists(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(Context context, File file) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 获取文件file的MIME类型
        String type = getMIMEType(file);
        // 设置intent的data和Type属性。
        intent.setDataAndType(/* uri */Uri.fromFile(file), type);
        // 跳转
        try {
            // 这里最好try一下，有可能会报错。 //比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
		/* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static String getFileType(String name) {
        String type = "*";
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
		/* 获取文件的后缀名 */
        String end = name.substring(dotIndex + 1, name.length()).toLowerCase();
        return end;
    }

    public static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            { ".3gp", "video/3gpp" },
            { ".apk", "application/vnd.android.package-archive" },
            { ".asf", "video/x-ms-asf" },
            { ".avi", "video/x-msvideo" },
            { ".bin", "application/octet-stream" },
            { ".bmp", "image/bmp" },
            { ".c", "text/plain" },
            { ".class", "application/octet-stream" },
            { ".conf", "text/plain" },
            { ".cpp", "text/plain" },
            { ".doc", "application/msword" },
            { ".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".exe", "application/octet-stream" },
            { ".gif", "image/gif" },
            { ".gtar", "application/x-gtar" },
            { ".gz", "application/x-gzip" },
            { ".h", "text/plain" },
            { ".htm", "text/html" },
            { ".html", "text/html" },
            { ".jar", "application/java-archive" },
            { ".java", "text/plain" },
            { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" },
            { ".js", "application/x-javascript" },
            { ".log", "text/plain" },
            { ".m3u", "audio/x-mpegurl" },
            { ".m4a", "audio/mp4a-latm" },
            { ".m4b", "audio/mp4a-latm" },
            { ".m4p", "audio/mp4a-latm" },
            { ".m4u", "video/vnd.mpegurl" },
            { ".m4v", "video/x-m4v" },
            { ".mov", "video/quicktime" },
            { ".mp2", "audio/x-mpeg" },
            { ".mp3", "audio/x-mpeg" },
            { ".mp4", "video/mp4" },
            { ".mpc", "application/vnd.mpohun.certificate" },
            { ".mpe", "video/mpeg" },
            { ".mpeg", "video/mpeg" },
            { ".mpg", "video/mpeg" },
            { ".mpg4", "video/mp4" },
            { ".mpga", "audio/mpeg" },
            { ".msg", "application/vnd.ms-outlook" },
            { ".ogg", "audio/ogg" },
            { ".pdf", "application/pdf" },
            { ".png", "image/png" },
            { ".pps", "application/vnd.ms-powerpoint" },
            { ".ppt", "application/vnd.ms-powerpoint" },
            { ".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
            { ".prop", "text/plain" }, { ".rc", "text/plain" },
            { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
            { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
            { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
            { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
            { ".wmv", "audio/x-ms-wmv" },
            { ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
            { ".z", "application/x-compress" },
            { ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

    public static boolean getAllFiles(File root, String fileName) {
        boolean isExsits = false;
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    // getAllFiles(f);
                } else {
                    // System.out.println(f);
                    if (f.getName().equals(fileName))
                        isExsits = true;
                }
            }
        } else {
        }
        return isExsits;
    }

    public static String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /*
     * 将文件转成base64 字符串
     */
    public static String encodeBase64File(String path) {

        String end = MyFileUtil.getFileType((new File(path)).getName());
        if (end.equals("png") || end.equals("jpg") || end.equals("jpeg"))
            return ImageUtil.imgToBase64(path);

        File file = new File(path);
        FileInputStream inputFile;
        byte[] buffer = null;
        try {
            inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /*
     * 将base64字符解码保存文件
     */
    public static void decoderBase64File(String base64Code, String savePath) {
        // byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out;
        try {
            out = new FileOutputStream(savePath);
            out.write(buffer);
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 写文件
     * @param file -- 操作文件
     * @param content	写入内容
     * @param append	true：追加写入  ， false：覆盖写入
     */
    public static void writeFile(File file, String content,
                                 boolean append) {
        RandomAccessFile raf = null;
        FileOutputStream out = null;
        try {
            if (append) {
                // 如果为追加则在原来的基础上继续写文件
                raf = new RandomAccessFile(file, "rw");
                raf.seek(file.length());
                raf.write(content.getBytes());
                raf.write("\n".getBytes());
            } else {
                // 重写文件，覆盖掉原来的数据
                out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
