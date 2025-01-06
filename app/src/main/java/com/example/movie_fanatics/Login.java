package com.example.movie_fanatics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.datatransport.runtime.dagger.multibindings.ElementsIntoSet;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private  Button login;
    private EditText username,password;
    private DBHandler dbHandler;
    private ImageView back,see;
    private TextView signup,msg;
String status="hide";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back=findViewById(R.id.backk);
        login=findViewById(R.id.login);
        see=findViewById(R.id.see);
        signup=findViewById(R.id.signup);
        username=findViewById(R.id.username);
        password=findViewById(R.id.pass);
        msg=findViewById(R.id.message);

        back.setOnClickListener(this);
        signup.setOnClickListener(this);
        see.setOnClickListener(this);
        login.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        if (v.equals(see)){
            if(status.equals("see")){
                status="hide";
                see.setImageResource(R.drawable.hide);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                status="see";
                see.setImageResource(R.drawable.view);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            password.setSelection(password.getText().length());
        }

        else if(v.equals(back)){
            Intent call=new Intent(this,MainActivity.class);
            startActivity(call);
            finish();
        }

        else if(v.equals(signup)){

        }

        else{

            dbHandler.adduser("sd","22","ww","ww");
            System.out.println("asdasd");

           Cursor c= dbHandler.getuser(username.getText().toString(),password.getText().toString());


           if(c.getCount()>0) {
               SharedPreferences sharedPreferences = getSharedPreferences("storage", MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();

               editor.putString("name", c.getString(1));
               editor.putInt("userid", c.getInt(0));
               editor.apply();
           }
            else {
                msg.setText("Invalid Credentiaals");
           }

        }

    }
}