package com.example.e4trely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<Product> listn = new ArrayList<Product>();
    ListView list;
    ArrayList<Product>cartlist;
    cartAdapter adapter;
    final EcommerceDB DB=new EcommerceDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        int CurrentUserID=getIntent().getExtras().getInt("CurrentUserID",0);
        //Toast.makeText(getApplicationContext(),"ID: "+String.valueOf(CurrentUserID),Toast.LENGTH_LONG).show();
        Cursor Mycart=DB.Retrieve_Cart(String.valueOf(CurrentUserID));
        list=(ListView)findViewById(R.id.lst);
        cartlist=new ArrayList<Product>();

        float T=0;
        while(!Mycart.isAfterLast()){
            Product p1=new Product();
            p1.setProName(Mycart.getString(1));
            p1.setPrice(Integer.parseInt(Mycart.getString(2)));
            p1.setQuantity(Integer.parseInt(Mycart.getString(3)));
            T+=Float.parseFloat(Mycart.getString(2));
            /*Cursor num=DB.get_qua(String.valueOf(CurrentUserID),String.valueOf(p1.getProdID()));
            int val=Integer.parseInt(num.getString(0));
            if (val != 0) {
                cartlist.add(p1);
            }*/
            cartlist.add(p1);
            Mycart.moveToNext();
        }
        adapter=new cartAdapter(getApplicationContext(),cartlist,DB,CurrentUserID);
        list.setAdapter(adapter);
        TextView price=(TextView)findViewById(R.id.textv2);

        int s=adapter.totalprice;
        price.setText(String.valueOf(Math.round(T))+" L.E");
        Button comp=(Button)findViewById(R.id.complete);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost=0;
                //listn=adapter.Myllist;
//                long orderCur=DB.Add_to_Orders(CurrentUserID,"");
                ArrayList<Integer>IDsList=new ArrayList<>();
                ArrayList<Integer>QsList=new ArrayList<>();
                for(int i=0;i<cartlist.size();i++){
                    IDsList.add(cartlist.get(i).getProdID());
                    IDsList.add(cartlist.get(i).getQuantity());
                    cost+=cartlist.get(i).getPrice();
                }
                Intent Order=new Intent(CartActivity.this,MainActivity3.class);
                Order.putExtra("CurrentUserID",CurrentUserID);
                Order.putExtra("total",cost);
                Order.putExtra("IDs",IDsList);
                Order.putExtra("Qs",QsList);
                startActivity(Order);
            }
        });

    }


}
