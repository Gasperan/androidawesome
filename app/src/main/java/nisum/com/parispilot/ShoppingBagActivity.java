package nisum.com.parispilot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InputQueue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nisum.com.parispilot.adapters.ListItemViewAdapter;

public class ShoppingBagActivity extends AppCompatActivity implements OnItemClicked{


    private static final String PREFS_FILE = "com.nisum.parispilot.preferences";
    private static final String SHOPPING_CART = "shopping_cart";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    List<String> indexItems = new ArrayList<>();
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        t = (TextView) findViewById(R.id.totalEdit);

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
                Log.d("Your sister", "Que wena esta tu heramana");
            }
        });

        doInBackground();
    }

    private void getListDataResponse(List<String> indexItems) {
        ListView listview = (ListView)findViewById(R.id.listItemsAdded);
        ListItemViewAdapter adapter = new ListItemViewAdapter(this);
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
    public void onClick(int price) {
        t.setText(price+"");
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
}
