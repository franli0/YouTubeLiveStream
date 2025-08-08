package com.fengsui.youtubesearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalStorage extends SQLiteOpenHelper {

    //資料庫名稱
    private final static String DATABASE_NAME = "iptv.db";
    //資料庫版本
    private final static int DATABASE_VERSION = 1;

    public final static String MOVIES_TABLE = "items_list";

    public LocalStorage(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tb_movies = "CREATE TABLE "+ MOVIES_TABLE +" ("
                + "id INTEGER PRIMARY KEY , "
                + "category VARCHAR(100) , "
                + "title TEXT , "
                + "channel TEXT ,"
                + "videoUrl TEXT , "
                + "cardImageUrl TEXT "
                + ");";
        db.execSQL(tb_movies);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getMovies(String category)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(MOVIES_TABLE, new String[]{"id","title","cardImageUrl","videoUrl","channel"}, "category=?", new String[]{category}, null, null, null);
    }

    public Cursor getCategory()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(true, MOVIES_TABLE, new String[]{"category"},
                null,null,"category",null, "id", null);
    }

    public void truncate() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+MOVIES_TABLE);
        this.onCreate(db);
        db.close();
    }

    public long addMovie(String category, String title,String channel,String videoUrl,String cardImageUrl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("title", title);
        cv.put("channel", channel);
        cv.put("videoUrl", videoUrl);
        cv.put("cardImageUrl", cardImageUrl);

        return db.insert(MOVIES_TABLE, null, cv);
    }
}
