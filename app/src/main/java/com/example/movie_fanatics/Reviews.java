package com.example.movie_fanatics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Reviews extends AppCompatActivity {

    private ImageView img;

    private RatingBar rate;

    private ScrollView scroll;
    private TextView moviename,nocomment;
    private ImageView imgview,back;
    private EditText comment;
    LinearLayout layout;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        img=findViewById(R.id.movieimg);
        rate=findViewById(R.id.rating);
        moviename=findViewById(R.id.Moviename);
        imgview=findViewById(R.id.send);
        comment=findViewById(R.id.comment);
        back=findViewById(R.id.back);
        nocomment=findViewById(R.id.nocomment);
        layout=findViewById(R.id.comentsection);
        scroll=findViewById(R.id.scroll);

        db=new DBHandler(this);
        Intent get=getIntent();
        int id = get.getIntExtra("id", -1);

//        add error view


        byte[] imgs;
        Bitmap bit;

        Cursor c=db.getmovie(id);
        while(c.moveToNext()){
            moviename.setText(c.getString(1));
            imgs=c.getBlob(2);
            bit= BitmapFactory.decodeByteArray(imgs,0,imgs.length);
            rate.setRating(c.getFloat(4));
            img.setImageBitmap(bit);
        }

        comment.setHint("Comment on "+moviename.getText().toString());
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String com=comment.getText().toString();
                if(!com.isBlank()){

                    db.setreviews(id,com.trim());
                    comment.setText("");
                    layout.removeViewAt(layout.getChildCount()-1);
                    createviews(com.trim());
                    createviews("");
                    scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));

                }
                else {
                    Toast.makeText(Reviews.this, "Comment field can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        c=db.getreviews(id);
        while (c.moveToNext()){
            createviews(c.getString(0));
        }

        if (layout.getChildCount()>1){
            layout.removeViewAt(0);
        }
    }
    private void createviews(String review) {
        TextView textView = new TextView(this);
        if (!review.equals("")){
        textView.setText("Watcher's Review: "+review);}
        textView.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(layoutParams);
        layout.addView(textView); // Add TextView to the parent layout
    }
}