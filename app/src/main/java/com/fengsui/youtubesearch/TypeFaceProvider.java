package com.fengsui.youtubesearch;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class TypeFaceProvider {
    private static Hashtable typeFaces = new Hashtable(3);

    public static Typeface getTypeFace(Context context, String fileName) {

        Typeface typeface = (Typeface) typeFaces.get(fileName);

        if (typeface == null) {

            String fontPath = "fonts/" + fileName;
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);

            typeFaces.put(fileName, typeface);
        }

        return typeface;
    }
}
