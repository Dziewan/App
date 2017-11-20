package com.example.coderion.app.model;

import android.graphics.Bitmap;

/**
 * Created by coderion on 20.11.17.
 */

public interface ImageConverter {

    byte[] encode(Bitmap bitmap);

    Bitmap decode(byte[] array);
}
