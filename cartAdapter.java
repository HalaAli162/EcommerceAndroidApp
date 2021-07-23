package com.example.e4trely;

import android.content.Context;
import android.database.Cursor;
import android.os.ProxyFileDescriptorCallback;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends BaseAdapter {

    private Context mcontext;
    public List<Product> Myllist;
    private EcommerceDB DB;
    int totalprice;
    int ID;

    public cartAdapter(Context mcontext, List<Product> myllist, EcommerceDB DB,int id) {
        this.mcontext = mcontext;
        this.Myllist = myllist;
        this.DB = DB;
        DB = new EcommerceDB(mcontext);
        this.totalprice = 0;
        ID=id;
    }

    @Override

    public int getCount() {
        return Myllist.size();
    }

    @Override
    public Object getItem(int position) {
        return Myllist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mcontext, R.layout.cart_items, null);
        TextView name = (TextView) v.findViewById(R.id.tv_name);
        TextView price = (TextView) v.findViewById(R.id.tv_price);
        EditText qua = (EditText) v.findViewById(R.id.tv_Disc);
        name.setText(Myllist.get(position).getProName());
        price.setText(String.valueOf(Myllist.get(position).getPrice()));
        //Cursor num=DB.get_qua(String.valueOf(ID),String.valueOf(Myllist.get(position).getProdID()));
        qua.setText("1");
        int q = Integer.parseInt(qua.getText().toString());
        totalprice += Math.round(Myllist.get(position).getPrice() * q);

        Button Increment = (Button) v.findViewById(R.id.inc);
        Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(String.valueOf(qua.getText()));
                qua.setText(String.valueOf(q + 1));
                Myllist.get(position).setQuantity(q+1);
                totalprice+=Myllist.get(position).getPrice();
                //DB.Update_quantity(q,String.valueOf(ID),String.valueOf(Myllist.get(position).getProdID()));
            }
        });
        Button Decrement = (Button) v.findViewById(R.id.Dec);
        Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(String.valueOf(qua.getText()));
                qua.setText(String.valueOf(q - 1));
                Myllist.get(position).setQuantity(q-1);
                totalprice-=Myllist.get(position).getPrice();
               // DB.Update_quantity(q,String.valueOf(ID),String.valueOf(Myllist.get(position).getProdID()));
            }
        });
        Button Delete = (Button) v.findViewById(R.id.Delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalprice-=Myllist.get(position).getPrice();
                Product p1 = Myllist.get(position);
                Myllist.get(position).setQuantity(0);
                DB.Delete_fromcart(p1.getProdID());
                //DB.Update_quantity(0,String.valueOf(ID),String.valueOf(Myllist.get(position).getProdID()));
                Myllist.remove(position);
                notifyDataSetChanged();
            }
        });

        return v;
    }

}
