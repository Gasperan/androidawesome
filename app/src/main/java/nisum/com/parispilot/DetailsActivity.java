package nisum.com.parispilot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.HashSet;
import java.util.Set;

public class DetailsActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.nisum.parispilot.preferences";
    private static final String SHOPPING_CART = "shopping_cart";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ImageView mainImage;
    private String title;
    int position;
    private TextView price;
    private TextView description;
    private Button botonCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        TrackerHelper.initTracker(getApplication());

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

        botonCarrito = (Button) findViewById(R.id.button);

        botonCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFab(view);
            }
        });

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("showcaseLoaded", false)) {
            editor = sharedPreferences.edit();
            editor.putBoolean("showcaseLoaded", true);
            editor.commit();
            presentShowcaseAddProduct();
        }


    }

    private void presentShowcaseAddProduct() {

        /*
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Target viewTarget = new Target() {
                    @Override
                    public Point getPoint() {
                       // return new ViewTarget(findViewById(R.id.fab)).getPoint();
                    }
                };

                RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                // This aligns button to the bottom left side of screen
                lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                // Set margins to the button, we add 16dp margins here
                int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
                lps.setMargins(margin, margin, margin, margin);

                final ShowcaseView showcaseView;
                showcaseView = new ShowcaseView.Builder(DetailsActivity.this)
                        .setTarget(viewTarget)
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTitle("Agregar un producto")
                        .setContentText("Ingrese este producto a su carro de compras")
                        .build();
                showcaseView.setButtonPosition(lps);

            }
        }, 1000);*/
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

        //Tracker
        TrackerHelper.sendEvent("ShoppinList", "agregado producto");
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
    protected void onResume() {
        super.onResume();
        TrackerHelper.nameScreen(this);
    }
}
