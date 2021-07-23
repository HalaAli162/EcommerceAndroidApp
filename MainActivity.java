package com.example.e4trely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    int CurrentUserID;
    private String sharedPreFile="com.example.android.hellosharedprefs";
    SharedPreferences myshared;
    TextInputLayout password;
    TextInputLayout username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView forget=(TextView)findViewById(R.id.forget);
        Button SUp=(Button)findViewById(R.id.SignBtn);
        Button log=(Button)findViewById(R.id.login_btn);
        username=(TextInputLayout)findViewById(R.id.textInputLayout);
        password=(TextInputLayout)findViewById(R.id.textInputLayout2);
        String u;
        String p;
        myshared=getSharedPreferences(sharedPreFile,MODE_PRIVATE);

            if(myshared.getBoolean("Check",false)==true){
                u=myshared.getString("Username","");
                username.getEditText().setText(u);
                p=myshared.getString("Password","");
                password.getEditText().setText(p);
            }
        final EcommerceDB e=new EcommerceDB(this);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []arr=e.LogIN(username.getEditText().getText().toString(),password.getEditText().getText().toString());
                if(Integer.parseInt(arr[0])==-1){
                    Toast.makeText(getApplicationContext(),"Wrong UserName or Password !!!!!",Toast.LENGTH_LONG).show();
                }
                else{
                    CurrentUserID=Integer.parseInt(arr[0]);
                    Toast.makeText(getApplicationContext(),"Welcome "+arr[1],Toast.LENGTH_LONG).show();
                    Intent store =new Intent(MainActivity.this,Categories.class);
                    store.putExtra("CurrentUserID",Integer.parseInt(arr[0]));
                    startActivity(store);
                }
                CheckBox remember=(CheckBox)findViewById(R.id.checkBox);
                if(remember.isChecked()){

                    SharedPreferences.Editor editor=myshared.edit();
                    editor.putString("Username",username.getEditText().getText().toString());
                    editor.putString("Password",password.getEditText().getText().toString());
                    editor.putBoolean("Check",true);
                    editor.apply();
                }

            }

        });

        SUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newP=e.Forget_pass(username.getEditText().getText().toString());
                password.getEditText().setText(newP);
            }
        });


    }
    public boolean Validusername(){
        String inputUN=username.getEditText().getText().toString().trim();
        if(inputUN.isEmpty()){
            username.setError("Field is empty !");
            return false;
        }
        else{
            username.setError("");
            return true;
        }
    }
    public boolean Validpassword(){
        String inputUN=password.getEditText().getText().toString().trim();
        if(inputUN.isEmpty()){
            password.setError("Field is empty !");
            return false;
        }
        else{
            password.setError("");
            return true;
        }
    }

    public void Confirm(View v){
        if(!Validusername()|!Validpassword()){
return;
        }
    }


}

