package com.example.e4trely;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.controls.Control;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Currency;

public class EcommerceDB  extends SQLiteOpenHelper {
    private static String databaseName="CommerceDatabase";
    SQLiteDatabase CommerceDatabase;

    String n="";

    public EcommerceDB(@Nullable Context context) {
        super(context, databaseName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customer (CustID integer primary key autoincrement,CutName text not null,Username text not null," +
                "Password text not null, Gender text not null, Job text not null, BirthDate text not null)");
        db.execSQL("create table Orders (OrdID integer primary key autoincrement,OrdDate text,Address text not null," +
                "CustID integer,FOREIGN KEY(CustID)REFERENCES Customer (CustID))");
        db.execSQL("create table Category(CatID integer primary key autoincrement,CatName text not null)");
        db.execSQL("create table Product (ProdID integer primary key autoincrement,Barcode text,ProName text not null,Price Integer not null," +
                "Quantity integer not null,CatID integer,FOREIGN KEY(CatID)REFERENCES Category (CatID))");
        db.execSQL("create table Order_Details(Quantity integer not null,OrdID,ProdID integer primary key autoincrement," +
                "FOREIGN KEY(OrdID)REFERENCES Orders (OrdID),FOREIGN KEY(ProdID)REFERENCES Product (ProdID))");
        db.execSQL("create table ShoppingCart(CProdID integer not null,CustID integer not null)");
       /* db.execSQL("create table ShoppingCart (CProdID integer not null,CustID integer not null)");


        */
        ContentValues newC=new ContentValues();
        newC.put("CutName","Hala");
        newC.put("Username","Hala17");
        newC.put("Password","hhhh");
        newC.put("Gender","Female");
        newC.put("Job","Student");
        newC.put("BirthDate","16/2/2000");
        db.insert("Customer",null,newC);

       /* newC.put("CutName","Ahmed");
        newC.put("Username","Ahmed");
        newC.put("Password","hhhh");
        newC.put("Gender","Female");
        newC.put("Job","Student");
        newC.put("BirthDate","16/2/2000");
        db.insert("Customer",null,newC);*/

        ContentValues cat=new ContentValues();
        cat.put("CatName","Laptops");
        db.insert("Category",null,cat);

        cat.put("CatName","Mobile Phones");
        db.insert("Category",null,cat);

        ContentValues prod=new ContentValues();
        prod.put("Barcode","6224000492526");
        prod.put("ProName","HP Laptop i7");
        prod.put("Price",17000);
        prod.put("Quantity",5);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","HP Laptop i3");
        prod.put("Barcode","6222001405385");
        prod.put("Price",12000);
        prod.put("Quantity",3);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","HP Laptop i5");
        prod.put("Barcode","6222001405885");
        prod.put("Price",13500);
        prod.put("Quantity",3);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","DELL Laptop i7");
        prod.put("Barcode","6223004160547");
        prod.put("Price",15000);
        prod.put("Quantity",5);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","DELL Laptop Gaming");
        prod.put("Barcode","6223004160547");
        prod.put("Price",32000);
        prod.put("Quantity",5);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","DELL Laptop i3");
        prod.put("Barcode","6223004160547");
        prod.put("Price",10000);
        prod.put("Quantity",5);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","Lenovo Laptop i5");
        prod.put("Barcode","6224008513490");
        prod.put("Price",11000);
        prod.put("Quantity",5);
        prod.put("CatID",1);
        db.insert("Product",null,prod);

        prod.put("ProName","Samsung A7");
        prod.put("Barcode","6223001930082");
        prod.put("Price",4200);
        prod.put("Quantity",7);
        prod.put("CatID",2);
        db.insert("Product",null,prod);

        prod.put("ProName","Oppo f5");
        prod.put("Barcode","6221039321045");
        prod.put("Price",4100);
        prod.put("Quantity",4);
        prod.put("CatID",2);
        db.insert("Product",null,prod);

        prod.put("ProName","IPhone X6");
        prod.put("Barcode","6221039321145");
        prod.put("Price",10000);
        prod.put("Quantity",4);
        prod.put("CatID",2);
        db.insert("Product",null,prod);

        prod.put("ProName","Huawei Y7");
        prod.put("Barcode","6221039321049");
        prod.put("Price",3200);
        prod.put("Quantity",4);
        prod.put("CatID",2);
        db.insert("Product",null,prod);

        prod.put("ProName","Huawei Y6");
        prod.put("Barcode","6221039321049");
        prod.put("Price",3000);
        prod.put("Quantity",5);
        prod.put("CatID",2);
        db.insert("Product",null,prod);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customer");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists Product");
        db.execSQL("drop table if exists Order_Details");
        db.execSQL("drop table if exists ShoppingCart");

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public long Sign_up(String Name, String UName, String Passw, String Gender, String job, String Bdate){
        ContentValues newC=new ContentValues();
        newC.put("CutName",Name);
        newC.put("Username",UName);
        newC.put("Password",Passw);
        newC.put("Gender",Gender);
        newC.put("Job",job);
        newC.put("BirthDate",Bdate);
        CommerceDatabase=getWritableDatabase();
        long result=CommerceDatabase.insert("Customer",null,newC);
        //CommerceDatabase.close();
        return result;
    }
    public Cursor fetch(){
        Cursor cursor;
        CommerceDatabase=getReadableDatabase();
        String []row={"ProdID","ProName","Price","Quantity","CatID"};
        cursor=CommerceDatabase.query("Product",row,null,null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor retrieve(){
        CommerceDatabase=getReadableDatabase();
        String []arg={"Samsung A7"};
        Cursor c=CommerceDatabase.rawQuery("select * from Product where ProName like ?",arg);
        if(c!=null){
            c.moveToFirst();
        }
        return c;
    }


    public String[] LogIN(String userName,String Pass) {
        CommerceDatabase = getReadableDatabase();
        String[] arg = {userName};
        int id =-1;
        Cursor c = CommerceDatabase.rawQuery("Select Password from Customer where Username like ?", arg);
        if(c.getCount()==0){
            id=-1;
        }
        else {
            if (c != null) {
                c.moveToFirst();
            }
            String r = c.getString(0);
            if (r.equals(Pass)) {
                c = CommerceDatabase.rawQuery("select CustID,CutName from Customer where Username like ?", arg);
                if (c != null) {
                    c.moveToFirst();
                }
                id = Integer.parseInt(c.getString(0));
                n = c.getString(1);
            } else {
                id = -1;
            }
        }
        String[]arr={String.valueOf(id),n};
        return arr;
    }

    public String Forget_pass(String User){
        CommerceDatabase = getReadableDatabase();
        String[] arg = {User};
        Cursor c = CommerceDatabase.rawQuery("select Password from Customer where Username like ?", arg);
        if(c!=null) {
            c.moveToFirst();
        }
        return c.getString(0);
    }

    public Cursor Retrieve_Cart(String id){
        Cursor cart;
        String []arg={id};
        CommerceDatabase = getReadableDatabase();
        cart=CommerceDatabase.rawQuery("SELECT ProdID,ProName,Price,Quantity FROM Product,ShoppingCart WHERE Product.ProdID=ShoppingCart.CProdID AND ShoppingCart.CustID='"+id+"';",null);
        if(cart!=null) {
            cart.moveToFirst();
        }
        //CommerceDatabase.close();
        return cart;
    }
    public long AddToCart(int CustID,int ProdID){
        ContentValues Newp=new ContentValues();
        Newp.put("CProdID",ProdID);
        Newp.put("CustID",CustID);
        CommerceDatabase=getWritableDatabase();
        long result=CommerceDatabase.insert("ShoppingCart",null,Newp);
        //CommerceDatabase.close();
        return result;
    }
    public void Delete_fromcart(int ProductID){
        String [] arg={String.valueOf(ProductID)};
        CommerceDatabase=getWritableDatabase();
        CommerceDatabase.delete("ShoppingCart","CProdID='"+String.valueOf(ProductID)+"'",null);
        //CommerceDatabase.close();
    }
    public Cursor Search_Bycode(String text){
       CommerceDatabase=getReadableDatabase();
       String [] arg={text};
        Cursor result=CommerceDatabase.rawQuery("select ProdID,ProName,Price,Quantity,CatID FROM Product where Barcode like ? ",arg);
        if(result!=null){
            result.moveToFirst();
        }
        //CommerceDatabase.close();
        return result;
    }
    public Cursor Items_OfAuser(int CustID){
        CommerceDatabase=getReadableDatabase();
        String []arg={String.valueOf(CustID)};
        Cursor c=CommerceDatabase.rawQuery("select CProdID from ShoppingCart where CustID like ?",arg);
        if(c!=null){
            c.moveToFirst();
        }
        return c;
    }
    public void Update_quantity(int qua,String IDofc,String IDofp){
        CommerceDatabase=getWritableDatabase();
        String upd="Update ShoppingCart set qua=?where CustID like ? AND CProdID like ?";
        CommerceDatabase.execSQL(upd, new String[]{String.valueOf(qua), IDofc, IDofp});
        CommerceDatabase.close();
    }
    public long Add_to_Orders(int Id,String Address){
        ContentValues order=new ContentValues();
        order.put("CustID",Id);
        order.put("Address",Address);
        CommerceDatabase=getWritableDatabase();
        long result=CommerceDatabase.insert("Orders",null,order);
        CommerceDatabase.close();
        return result;
    }
     public long Details_Of_order(int qua,int OID,int prodid){
         ContentValues order=new ContentValues();
         order.put("Quantity",qua);
         order.put("OrdID",OID);
         order.put("ProdID",prodid);
         CommerceDatabase=getWritableDatabase();
         long result=CommerceDatabase.insert("Order_Details",null,order);
         CommerceDatabase.close();
         return result;
     }
    public Cursor get_qua(String IDofc,String IDofp){
        CommerceDatabase=getReadableDatabase();
        String []arg={IDofc,IDofp};
        Cursor C=CommerceDatabase.rawQuery("select qua from ShoppingCart where CustID like ? AND CProdID like ?",arg);
        if(C!=null){
            C.moveToFirst();
        }
        return C;
    }
}

