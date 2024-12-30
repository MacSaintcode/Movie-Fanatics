package com.example.movie_fanatics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DBHandler extends SQLiteOpenHelper {
    Context c;

    public DBHandler(@Nullable Context context) {
        super(context, "Movie_Handler", null, 5);
        c=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String Movie_Table="CREATE TABLE if not exists Movie_Detailes (Id INTEGER PRIMARY KEY AUTOINCREMENT, Movie_Names TEXT,Movie_image BLOB,Genre TEXT,Ratings DOUBLE,Description TEXT)";
        String Movie_Reviews="CREATE TABLE if not exists Movie_Reviews (Id INTEGER REFERENCES Movie_Details(Id) ,Review TEXT)";
        db.execSQL(Movie_Table);
        db.execSQL(Movie_Reviews);
    }
    void addmovies(String moviename,int draw, double rating, String description,String genre){
        SQLiteDatabase db=this.getWritableDatabase();


        db= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), draw);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] img = stream.toByteArray();

        values.put("Movie_Names",moviename);
        values.put("Movie_image",img);
        values.put("Genre",genre);
        values.put("Ratings",rating);
        values.put("Description",description);

      long t= db.insert("Movie_Detailes",null,values);
        System.out.println("complete!"+ t);
//        db.execSQL("delete from Movie_Reviews");
//        db.execSQL("delete from Movie_Detailes");
    }
    Cursor getallmovie(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery("Select * from Movie_Detailes",new String[]{});
        return  c;
    }
     Cursor getmoviegenre(String genre,String search){
        SQLiteDatabase db=this.getReadableDatabase();
         Cursor c;
         if (genre.equals("All Genre")){
             if (!(search.isBlank())) {
                 System.out.println(search);
                 c= db.rawQuery("Select * from Movie_Detailes where Movie_Names like ?",new String[]{"%"+search+"%"});
             }else{
                c=getallmovie();
             }
         }else{
             if (!(search.isBlank())) {
                 System.out.println(search);
                 c= db.rawQuery("Select * from Movie_Detailes where Movie_Names like ? and Genre = ? ",new String[]{"%"+search+"%",genre});
             }else{
                 c= db.rawQuery("Select * from Movie_Detailes where Genre =?",new String[]{genre});
            }
         }
        return  c;
    }
    Cursor getreviews(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("Select Review from Movie_Reviews where Id = ?",new String[]{String.valueOf(id)});
        return c;
    }
    void setreviews(int id,String review){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("Review",review);
        values.put("Id",id);
        db.insert("Movie_Reviews",null,values);
    }
    Cursor moviesearch(String name,String genre){
        SQLiteDatabase db;
        Cursor c;
    db=this.getReadableDatabase();
    if (genre.equals("All Genre")){
        c=db.rawQuery("Select * from Movie_Detailes where Movie_Names like ? ",new String[]{"%"+name+"%"});
    }else{
        c=db.rawQuery("Select * from Movie_Detailes where Movie_Names like ? and Genre = ? ",new String[]{"%"+name+"%",genre});
    }
        return c;
    }
    Cursor getmovie(int num){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("Select * from Movie_Detailes where Id = ? ",new String[]{String.valueOf(num)});
        return c;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade="DROP TABLE Movie_Detailes";
        db.execSQL(upgrade);
        onCreate(db);

    }
}
