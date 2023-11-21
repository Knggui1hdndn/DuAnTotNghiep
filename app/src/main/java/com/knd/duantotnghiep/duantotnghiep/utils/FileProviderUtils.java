package com.knd.duantotnghiep.duantotnghiep.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileProviderUtils {
    public static File saveImage(ImageView imageView, Context context) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        File file = new File(context.getFilesDir(), "avatar.png");
        if (file.exists()) file.delete();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public static File createFileFromUri(Uri uri, Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageURI(uri);
        File directory = new File(context.getCacheDir() + "/evaluate/image/");
        if (!directory.exists()) directory.mkdirs();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        File file = new File(directory, System.currentTimeMillis() + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = new File(context.getCacheDir() + "/evaluate/image");
            if (dir.isDirectory()) {
               for (File file : dir.listFiles()) {
                   Boolean aBoolean = file.delete();
                }
            }
        } catch (Exception e) {
            Log.d("sdadfaaaaaaa", e.getLocalizedMessage() + "");
        }
    }

//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        return dir.delete();
//    }

    public static Uri getUri(String path, Context context) {
        File file = new File(path);
        return FileProvider.getUriForFile(context, "com.knd.duantotnghiep.duantotnghiep", file);
    }
}
