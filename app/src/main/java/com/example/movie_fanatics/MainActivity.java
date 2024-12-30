package com.example.movie_fanatics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.stream.DoubleStream;

public class MainActivity extends AppCompatActivity {

    private LinearLayout parentLayout;
    private SearchView search;
    private DBHandler DBHandler;
    private Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the parent layout defined in the XML
        parentLayout = findViewById(R.id.parentlayout);
        search=findViewById(R.id.searchView);
        DBHandler=new DBHandler(this);
        spin = findViewById(R.id.genre);

//        parentLayout.setHorizontalScrollBarEnabled(true);
//        Random rand=new Random();
//        String name[]={"Romance","Action","X Rated","Horror"};
//        String movi[]={"Instigator","The Fraction","Lily Rose","Roxxan","Anna","Reve Rex","The Salamander",
//                "The Silver Tongue","Razor Sharp","Fillet","The Judas Family","Deadly Kimiyo"};
//        int count=0;
//        for (int i = 1; i < movi.length+1; i++) {
//            if(count==4){
//                count=0;
//            }
//            DBHandler.addmovies(movi[i-1],R.drawable.res,
//                 rand.nextInt(6),"bolo",name[count]);
//            count++;
//        }


        Cursor c=DBHandler.getallmovie();
       generate(c);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c= DBHandler.getmoviegenre(parent.getItemAtPosition(position).toString(),search.getQuery().toString());
                if(c.getCount()==0){
                    nores();
                }else{
                    generate(c);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
    }

    void search(String newText){
        int count=0;
        if (!(newText.isBlank())){
            Cursor c=DBHandler.moviesearch(newText,spin.getSelectedItem().toString());
            count=c.getCount();
            System.out.println(count);
            if(count==0){
                nores();
            }else {
                generate(c);
            }
        }else
        {
            Cursor c=DBHandler.getmoviegenre(spin.getSelectedItem().toString(),newText);
            generate(c);
        }
    }

    void nores(){
        parentLayout.removeAllViews();
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.rt2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
        );
        img.setLayoutParams(layoutParams);
        parentLayout.addView(img);// Add ImageView to the parent layout
    }
    
    void generate(Cursor c){
        parentLayout.removeAllViews();
        String moviename;
        Bitmap bit;
        byte[] img;
        int id;
        while (c.moveToNext()) {
            moviename=c.getString(1);
            img=c.getBlob(2);
            bit= BitmapFactory.decodeByteArray(img,0,img.length);
            id=c.getInt(0);
            System.out.println(id);
            System.out.println(moviename);
            createview(id,moviename,bit);
        }
    }

    private void createview(int id, String moviename,Bitmap imgs) {
        ImageView img = new ImageView(this);
        TextView textView = new TextView(this);
        img.setImageBitmap(imgs);
        img.setId(id);
        Intent call= new Intent(MainActivity.this, Reviews.class);
        call.putExtra("id",id);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textView.setTextColor(Color.BLUE);
                startActivity(call);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        img.setLayoutParams(layoutParams);

//       -----------------------------------------------------------------------------------------------------

        textView.setText(moviename);
        textView.setId(id);
        textView.setTextSize(16);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(20,10,20,40);

        layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(call);
            }
        });

        textView.setLayoutParams(layoutParams);
        parentLayout.addView(img);// Add ImageView to the parent layout
        parentLayout.addView(textView); // Add TextView to the parent layout
    }
}