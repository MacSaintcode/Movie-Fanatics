package com.example.movie_fanatics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText username,password;
    ImageView back,see;
    TextView signup;
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


        }
        else if(v.equals(signup)){


        }
        else{

        }

    }
}