package nisum.com.parispilot;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        mainImage = (ImageView) findViewById(R.id.main_image);

        Resources resources = getApplicationContext().getResources();
        TypedArray a = resources.obtainTypedArray(R.array.products_picture);
        //mPlacePictures = new Drawable[a.length()];

        int position = getElementPosition(savedInstanceState);

        mainImage.setImageDrawable(a.getDrawable(position));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "producto agregado al carrito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }


    private int getElementPosition(Bundle savedInstanceState) {
        Integer position;
        //if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                position = null;
            } else {
                position= extras.getInt("product");
            }
        //} else {
          //  position= (int) savedInstanceState.getSerializable("position");
        //}

        return position;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
