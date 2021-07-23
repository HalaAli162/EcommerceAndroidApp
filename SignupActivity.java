package com.example.e4trely;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {



    String gender;
    Calendar cal;
    DatePickerDialog Dpd;
    String D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        EcommerceDB c=new EcommerceDB(this);

        EditText name=(EditText)findViewById(R.id.nameTxt);
        EditText usern=(EditText)findViewById(R.id.UnameTxt);
        EditText pass=(EditText)findViewById(R.id.ptxt);
        EditText job=(EditText)findViewById(R.id.JnameTxt);


        TextView bd=(TextView)findViewById(R.id.textView9);

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal=Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=1;
                month+=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);


                Dpd=new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        D=mdayOfMonth+"/"+mmonth+"/"+myear;
                        bd.setText(D);
                    }
                },day,month,year);
                Dpd.show();

            }
        });



        Button SignUpF=(Button)findViewById(R.id.sign);
        SignUpF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.equals("")&!usern.equals("")&!pass.equals("")&!job.equals("")) {
                    RadioButton F = (RadioButton) findViewById(R.id.FRadioBtn);
                    RadioButton M = (RadioButton) findViewById(R.id.MRadioBtn);
                    if (F.isChecked()) {
                        gender = "Female";
                    } else if (M.isChecked()) {
                        gender = "Male";
                    }

                    long result = c.Sign_up(name.getText().toString(), usern.getText().toString(), pass.getText().toString(), gender, job.getText().toString(), D);
                    if (result != -1) {
                        Toast.makeText(getApplicationContext(), "You signed up successfully ID:"+String.valueOf(result), Toast.LENGTH_SHORT).show();
                        Intent store = new Intent(SignupActivity.this, Categories.class);
                        store.putExtra("CurrentUserID",Math.toIntExact(result));
                        startActivity(store);
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went Wrong!", Toast.LENGTH_LONG).show();

                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please fill all the Fields..", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}