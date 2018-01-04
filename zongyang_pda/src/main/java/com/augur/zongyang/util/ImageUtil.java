package com.augur.zongyang.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;

import com.augur.zongyang.database.entity.Cphoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunhu on 2018-01-02.
 */

public class ImageUtil {
    public interface OnDeletedPhotoListener {
        void onDeletedPhoto();
    }

    public static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }

    public static String imgToBase64(String imgPath) {
        Bitmap bitmap = null;
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        if (bitmap == null) {
            // bitmap not found!!
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void deleteImage(final Activity context,
                                   final String imagePath,
                                   final OnDeletedPhotoListener onDeletedPhotoListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("删除图片").setMessage("确定要删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            File file = new File(imagePath);
                            file.delete();
                            onDeletedPhotoListener.onDeletedPhoto();
                        } catch (Exception e) {
                            // TODO: handle exception
                            TimeUtil.MessageBox(context, "删除图片", "删除失败！");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).create().show();
    }

//    public static void showImage(Context context, String imagePath) {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("imagePath", imagePath);
//        intent.putExtras(bundle);
//        intent.setClass(context, PhotoActivity.class);
//        context.startActivity(intent);
//    }

    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
            return bitmap.getAllocationByteCount() / 1024;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
            // 12
            return bitmap.getByteCount() / 1024;
        }
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024; // earlier
        // version
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     */
    @SuppressWarnings("static-access")
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[] { MediaStore.Images.ImageColumns.DATA }, null,
                    null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor
                            .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /*
     * 压缩图片到100k以下
     */
    public static void compressBmpToFile(Bitmap bmp, File file) {
        if (bmp == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {// 当图片大于80k时继续压缩
            baos.reset();
            if (options <= 10) {
                options = 100;
            }
            options -= 10;// 每次都减少10
            // options = 50;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);// options压缩率(100%表示未压缩)
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 保存bitmap到文件
     */
    public static void saveBmpToFile(Bitmap bmp, File file) {
        if (bmp == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSmallBmpToFile(Context context, Uri uri, File outFile) {
        Bitmap b = readBitmap(getRealFilePath(context, uri));// 根据路径获得图片并压缩返回bitmap用于显示
        saveBmpToFile(b, outFile);
    }

    public static void compressAndSaveBmpToFile(Context context, Uri uri,
                                                File outFile) {
        Bitmap b = getSmallBitmap(getRealFilePath(context, uri));// 根据路径获得图片并压缩返回bitmap用于显示
        compressBmpToFile(b, outFile);
    }

    /*
     * 图片压缩并另存
     */
    public static String compressAndSave(Context context, String filePath) {
        String fileName = null;
        if (filePath != null && filePath.contains("/")) {
            String[] arr = filePath.split("/");
            fileName = arr[arr.length - 1];
        } else {
            return null;
        }
        Bitmap b = getSmallBitmap(filePath);// 根据路径获得图片并压缩返回bitmap用于显示
        if (b == null)
            return null;
        File file = MyFileUtil.getFile(fileName);
        compressBmpToFile(b, file);
        return file.getPath();
    }

    public static Bitmap toBitmap(String filepath) {
        if (!MyFileUtil.isFilePathExists(filepath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // 缩小图片
        return BitmapFactory.decodeFile(filepath, options);
    }

    public static List<String> getPhotoPaths(List<Cphoto> pList) {
        List<String> photoPaths = new ArrayList<String>();
        if (!ListUtil.isEmpty(pList)) {
            for (Cphoto cphoto : pList) {
                photoPaths.add(cphoto.getLocalPath());
            }
        }
        return photoPaths;
    }
}
