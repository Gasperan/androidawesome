package com.nisum.parispilot2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nisum.parispilot2.adapters.ListItemViewAdapter;

public class ShoppingBagActivity extends AppCompatActivity implements OnItemClicked{


    private static final String PREFS_FILE = "com.nisum.parispilot.preferences";
    private static final String SHOPPING_CART = "shopping_cart";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public String[] productArray;
    public ListView listview;
    public ListItemViewAdapter adapter;
    List<String> indexItems = new ArrayList<>();
    TextView t;
    TextView st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        t = (TextView) findViewById(R.id.totalEdit);
        st = (TextView) findViewById(R.id.subTotalEdit);
        productArray = getResources().getStringArray(R.array.products_prices);
        TrackerHelper.initTracker(getApplication());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Shopping Cart");
        setTitleColor(Color.parseColor("#2B8DE1"));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        Set<String> set1 = mSharedPreferences.getStringSet(SHOPPING_CART,null);

        if (set1 != null) {
            indexItems = new ArrayList(set1);
            System.out.println("shared_preferencesssssss: " +indexItems);
        }

        Button checkout = (Button) findViewById(R.id.btCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.no_checkout);
                builder.setTitle("Opción no disponible");
                builder.create().show();
            }
        });
        t.setText("$"+getTotal());
        st.setText("$"+getTotal());
        doInBackground();
    }

    private void getListDataResponse(List<String> indexItems) {
        listview = (ListView)findViewById(R.id.listItemsAdded);
        adapter = new ListItemViewAdapter(this);
        listview.setAdapter(adapter);
        adapter.setOnItemClicked(this);
        adapter.setData(indexItems);
    }


    private void doInBackground(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(indexItems);
            }
        });

        thread.start();
    }

    private void backToMainThreadWithResponse(final List<String> indexItems) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getListDataResponse(indexItems);
            }
        });
    }


    @Override
    public void onClick(int position) {
        mEditor = mSharedPreferences.edit();
        Set<String> set1 = mSharedPreferences.getStringSet(SHOPPING_CART,null);
        ArrayList qwerty = new ArrayList(set1);
        indexItems = qwerty;
        qwerty.remove(position);
        Set<String> set = new HashSet<String>(qwerty);
        mEditor.remove(SHOPPING_CART);
        mEditor.putStringSet(SHOPPING_CART, set);
        mEditor.apply();
        //test sharedpreferences
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        Set<String> set10 = mSharedPreferences.getStringSet(SHOPPING_CART,null);
        System.out.println("shared_preferences_tommy: " + set10);
        adapter.setData(qwerty);
        adapter.notifyDataSetChanged();
        t.setText("$"+getTotal());
        st.setText("$"+getTotal());
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        TrackerHelper.nameScreen(this);
    }

    public Context getActivity() {
        return this;
    }


    public int getTotal(){
        int total = 0;
        for(int i=0; i<indexItems.size(); i++){
            int itemPosition = Integer.parseInt(indexItems.get(i));
            total = total + Integer.parseInt(productArray[itemPosition]);
        }
        return total;
    }

}
