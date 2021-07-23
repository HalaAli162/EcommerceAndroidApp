package com.example.e4trely;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainStore extends AppCompatActivity {

    int i=0;
    ListView Showproducts;
    List<Product> Myproducts;
    ProductAdapter Myadapter;
    EditText srch;
    int CurrentUserID;
    public String code="";
    TextView view;
    final EcommerceDB Products=new EcommerceDB(this);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String>textvoice=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            srch.setText(textvoice.get(0));
        }

        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(intentResult!=null){
            if(intentResult.getContents()==null){
                //view.setText("Something is wrong");
            }
            else{
                code=intentResult.getContents();
                Cursor res=Products.Search_Bycode(code);
                if(res.getCount()>0&&res!=null){
                    List<Product> New=new ArrayList<>();
                    boolean exist=true;
                    while(!res.isAfterLast()) {
                        Product p1 = new Product();
                        p1.setProdID(Integer.parseInt(res.getString(0)));
                        p1.setProName(res.getString(1));
                        p1.setPrice(Integer.parseInt(res.getString(2)));
                        p1.setQuantity(Integer.parseInt(res.getString(3)));
                        p1.setCatID(Integer.parseInt(res.getString(4)));
                        if (p1.getCatID() == i) {
                            New.add(p1);
                        }
                        else {
                            exist = false;
                        }

                        res.moveToNext();
                    }
                    if(exist==false){
                        Toast.makeText(getApplicationContext(),"This product does not exists !",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ProductAdapter NewAd = new ProductAdapter(getApplicationContext(), New, CurrentUserID);
                        Showproducts.setAdapter(NewAd);
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"This product does not exists !",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_store);

        view=(TextView)findViewById(R.id.textView14);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},getPackageManager().PERMISSION_GRANTED);

        String CatName=getIntent().getExtras().getString("Category");

        if(CatName.equals("Laptops")){
            i=1;
        }else {
            i=2;
        }

        CurrentUserID=getIntent().getExtras().getInt("CurrentUserID",0);
        //Toast.makeText(getApplicationContext(),"ID: "+String.valueOf(CurrentUserID),Toast.LENGTH_LONG).show();
        Showproducts=(ListView)findViewById(R.id.ProductView);
        Myproducts=new ArrayList<>();

        Cursor ProductsCur=Products.fetch();
        while(!ProductsCur.isAfterLast()){
            Product p1=new Product();
            p1.setProdID(Integer.parseInt(ProductsCur.getString(0)));
            p1.setProName(ProductsCur.getString(1));
            p1.setPrice(Integer.parseInt(ProductsCur.getString(2)));
            p1.setQuantity(Integer.parseInt(ProductsCur.getString(3)));
            p1.setCatID(Integer.parseInt(ProductsCur.getString(4)));
            if(p1.getCatID()==i){
                Myproducts.add(p1);
            }

            ProductsCur.moveToNext();
        }
        Myadapter=new ProductAdapter(getApplicationContext(),Myproducts,CurrentUserID);
        Showproducts.setAdapter(Myadapter);
        ImageButton voice=(ImageButton)findViewById(R.id.imageButton1);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voiceIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(voiceIntent,1);
            }
        });

        srch=(EditText)findViewById(R.id.srch);
        srch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Myadapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ImageButton scan=(ImageButton)findViewById(R.id.imageButton2);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent scanner=new Intent(MainStore.this,BarcodeScanner.class);
                startActivity(scanner);*/
                IntentIntegrator Integrator=new IntentIntegrator(MainStore.this);
                Integrator.initiateScan();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i=new MenuInflater(this);
        i.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Shcart:
                Intent Cart=new Intent(MainStore.this,CartActivity.class);
                Cart.putExtra("CurrentUserID",CurrentUserID);
                startActivity(Cart);
                return true;
        }
        return false;

    }



}