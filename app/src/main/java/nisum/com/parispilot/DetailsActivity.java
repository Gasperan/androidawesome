package nisum.com.parispilot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DetailsActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.nisum.parispilot.preferences";
    private static final String SHOPPING_CART = "shopping_cart";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    private ImageView mainImage;
    private String title;
    int position;
    private TextView price;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        mainImage = (ImageView) findViewById(R.id.main_image);
        position = getElementPosition(savedInstanceState);
        price = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.descripcion);

        Resources resources = getApplicationContext().getResources();
        TypedArray a = resources.obtainTypedArray(R.array.products_picture);
        title = resources.getStringArray(R.array.products)[position];
        mainImage.setImageDrawable(a.getDrawable(position));
        price.setText(resources.getStringArray(R.array.products_prices)[position]);
        String myDescription = resources.getStringArray(R.array.products_desc)[position];
        description.setText(myDescription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
        setTitleColor(Color.parseColor("#2B8DE1"));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFab(view);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Intent i= new Intent(DetailsActivity.this,ShoppingBagActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clickFab(View view){
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        Set<String> set = mSharedPreferences.getStringSet(SHOPPING_CART,null);

        if (set == null) {
            set = new HashSet<String>();
        }

        set.add(position+"");
        mEditor.putStringSet(SHOPPING_CART, set);
        mEditor.apply();
        Snackbar.make(view, "producto agregado al carrito", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

        //test sharedpreferences
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        Set<String> set1 = mSharedPreferences.getStringSet(SHOPPING_CART,null);
        System.out.println("shared_preferences: " + set1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private int getElementPosition(Bundle savedInstanceState) {
        Integer position;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                position = null;
            } else {
                position= extras.getInt("product");
            }
        } else {
           position= (int) savedInstanceState.getSerializable("product");
        }
        return position;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        SurveyHandler.handleSurvey(this);
    }
}
