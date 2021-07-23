package com.example.e4trely;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Categories extends AppCompatActivity {

   int CurrentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        CurrentUserID=getIntent().getExtras().getInt("CurrentUserID",0);

        //Toast.makeText(getApplicationContext(),"ID: "+String.valueOf(CurrentUserID),Toast.LENGTH_LONG).show();
        String []arr=getResources().getStringArray(R.array.categories);
        ArrayAdapter<String>grid=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);

        GridView mygrid=(GridView)findViewById(R.id.grid1);
        mygrid.setAdapter(grid);


        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                TextView view=(TextView)arg1;
                String value=view.getText().toString();
                Intent store =new Intent(Categories.this,MainStore.class);
                store.putExtra("CurrentUserID",CurrentUserID);
                store.putExtra("Category",value);
                startActivity(store);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i=new MenuInflater(this);
        i.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Shcart:
                Intent Cart=new Intent(Categories.this,CartActivity.class);
                Cart.putExtra("CurrentUserID",CurrentUserID);
                startActivity(Cart);
                return true;
        }
        return false;

    }
}