package com.example.recipes.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;

public class GetImage {
    private static final String TAG = "GetImage";

    public static String saveImageToInternalStorage(Context context, Uri imageUri) {
        String filePath = null;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                throw new Exception("Unable to open input stream.");
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            if (bitmap == null) {
                throw new Exception("Unable to decode bitmap.");
            }

            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            filePath = context.getFileStreamPath(fileName).getAbsolutePath();
            Log.d(TAG, "Image saved to internal storage as " + filePath);

        } catch (Exception e) {
            Log.e(TAG, "Failed to save image", e);
        }

        return filePath;
    }
}