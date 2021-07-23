package com.example.e4trely;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EcommerceDB e=new EcommerceDB(this);
        TextView t=(TextView)findViewById(R.id.textView12);
        Cursor ProductsCur=e.retrieve();
       // while(!ProductsCur.isAfterLast()){
          /*  Product p1=new Product();
            p1.setProdID(Integer.parseInt(ProductsCur.getString(0)));
            p1.setProName(ProductsCur.getString(1));
            p1.setPrice(Float.parseFloat(ProductsCur.getString(2)));
            p1.setQuantity(Integer.parseInt(ProductsCur.getString(3)));
            p1.setCatID(Integer.parseInt(ProductsCur.getString(4)));
*/
            t.setText(ProductsCur.getString(0));

          //  ProductsCur.moveToNext();
        //}

    }
}