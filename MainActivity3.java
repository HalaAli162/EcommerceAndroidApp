package com.example.e4trely;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    ArrayList<Integer> IDsList=new ArrayList<>();
    ArrayList<Integer> QsList=new ArrayList<>();
    final EcommerceDB DB=new EcommerceDB(this);
    int CurrentUserID;
    String Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        CurrentUserID=getIntent().getExtras().getInt("CurrentUserID",0);
        IDsList=getIntent().getIntegerArrayListExtra("IDs");
        IDsList=getIntent().getIntegerArrayListExtra("Qs");

        Address=getIntent().getExtras().getString("Address","");
        int total=getIntent().getExtras().getInt("total",0);

        TextView Tprice=(TextView)findViewById(R.id.pricetv);
        Tprice.setText(String.valueOf(total)+" L.E");

        TextView address=(TextView)findViewById(R.id.addresstxt);
        if(Address!=""){
            address.setText(Address);
        }
        Button GetL=(Button)findViewById(R.id.MyLoc);
        GetL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity3.this,MapNew.class);
                startActivity(i);
            }
        });
        Button Submit=(Button)findViewById(R.id.submitordr);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long OrderCur=DB.Add_to_Orders(CurrentUserID,Address);
                /*for (int i=0;i<IDsList.size();i++){
                    long orderCur2 = DB.Details_Of_order(QsList.get(i), Math.toIntExact(OrderCur), IDsList.get(i));
                }*/
                Toast.makeText(getApplicationContext(),"Order submitted Successfully :) \n Thanks for using our app",Toast.LENGTH_LONG).show();
                Intent Cart=new Intent(MainActivity3.this,Categories.class);
                Cart.putExtra("CurrentUserID",CurrentUserID);
                startActivity(Cart);
            }
        });
    }
}