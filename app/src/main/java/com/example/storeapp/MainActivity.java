package com.example.storeapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.clickMenuItemListener
{
    Button wishlist_btn;
    Button cart_btn;
    List<String> Names;
    List<Integer> images;
    List<Integer> Prices;
    List<Product> Products;

    RecyclerView datalist;
    Adapter myadapter;
    myDatabaseHelper MyStore;
    static String Phone_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Phone_Number = getIntent().getExtras().getString(Login.EXTRA_PHONENUMBER);       //getting phone number from Main Activity
        MyStore = new myDatabaseHelper(this);
        Names = new ArrayList<String>();
        images = new ArrayList<Integer>();
        Prices = new ArrayList<Integer>();

        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        datalist = findViewById(R.id.datalist);


        cart_btn = findViewById(R.id.cartBtn);

        Cursor cursor = MyStore.Retrive_all();

        while (!cursor.isAfterLast()) {
            Names.add(cursor.getString(0));
            Prices.add(cursor.getInt(1));
            images.add(cursor.getInt(2));
            cursor.moveToNext();
        }

        myadapter = new Adapter(this, Names,images, Prices,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(myadapter);


        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Cart.class);
                i.putExtra(Login.EXTRA_PHONENUMBER,Phone_Number);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean clickMenuItem(MenuItem menuItem, int position) {
        switch (menuItem.getItemId()) {
            case R.id.cartBtn:
                Toast.makeText(getApplicationContext(),"Item Added to cart " + position,Toast.LENGTH_SHORT).show();
                MyStore.InsertTOCart(Phone_Number,position+1,50);
                return true;
            default:
                return false;
        }
    }
}