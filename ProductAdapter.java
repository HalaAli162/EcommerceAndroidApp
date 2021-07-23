package com.example.e4trely;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter implements Filterable {

    private Context mcontext;
    private List<Product> Myllist,temp;
    int ID;
    Customfilter CF;
    public ProductAdapter(Context mcontext, List<Product> myllist,int ID) {
        this.mcontext = mcontext;
        this.Myllist = myllist;
        this.temp=myllist;
        this.ID=ID;
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
        View v=View.inflate(mcontext,R.layout.item_products,null);
        TextView name=(TextView)v.findViewById(R.id.tv_name);
        TextView price=(TextView)v.findViewById(R.id.tv_price);
        TextView qua=(TextView)v.findViewById(R.id.tv_Disc);
        name.setText(Myllist.get(position).getProName());
        price.setText(String.valueOf(Myllist.get(position).getPrice())+" L.E.");
        qua.setText(String.valueOf(Myllist.get(position).getQuantity()));

        v.setTag(Myllist.get(position).getProdID());

        Button btn=(Button)v.findViewById(R.id.AddC);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Product p1 = Myllist.get(position);
                EcommerceDB DB = new EcommerceDB(mcontext);
               /* Cursor Items= DB.Items_OfAuser(ID);
                ArrayList<Integer>items=new ArrayList<>();
                while(!Items.isAfterLast()) {
                    items.add(Integer.parseInt(Items.getString(0)));
                }
                boolean found=false;
                for(int i=0;i<items.size();i++){
                    if(items.get(i) ==p1.getProdID()){
                        found=true;
                    }
                }
                 if (found == false){*/
                    long r = DB.AddToCart(ID, p1.getProdID());
                    if (r != -1) {
                        Toast.makeText(mcontext, "Product Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mcontext, "Something wrong !!!", Toast.LENGTH_SHORT).show();

                    }
                }
              /*  else {
                    Toast.makeText(mcontext, "Product is already exist!", Toast.LENGTH_SHORT).show();
                        }
                }*/
            });


        return v;

    }

    @Override
    public Filter getFilter() {
        if(CF==null){
            CF=new Customfilter();
        }
        return CF;
    }
    class Customfilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults FR=new FilterResults();
            if(constraint!=null&&constraint.length()>0){
                constraint=constraint.toString().toLowerCase();
                ArrayList<Product> filterarr=new ArrayList<Product>();
                for(int i=0;i<temp.size();i++){
                    if(temp.get(i).getProName().toLowerCase().contains(constraint)){
                        Product p=new Product();
                        p.setProdID(temp.get(i).getProdID());
                        p.setProName(temp.get(i).getProName());
                        p.setPrice((int) temp.get(i).getPrice());
                        p.setQuantity(temp.get(i).getQuantity());
                        p.setCatID(temp.get(i).getCatID());
                        filterarr.add(p);
                    }
                }
                FR.count=filterarr.size();
                FR.values=filterarr;
            }else {
                FR.count=temp.size();
                FR.values=temp;
            }


            return FR;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Myllist=(ArrayList<Product>)results.values;
            notifyDataSetChanged();

        }
    }
}
