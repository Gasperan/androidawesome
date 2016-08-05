package nisum.com.parispilot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Paris Pilot");
        setTitleColor(Color.parseColor("#2B8DE1"));
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TrackerHelper.initTracker(getApplication());

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("showcaseLoaded", false)) {
            editor = sharedPreferences.edit();
            editor.putBoolean("showcaseLoaded", false);
            editor.commit();
        }

    }

    private void presentShowcaseList(int withDelay) {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Target targetList = new ViewTarget(R.id.viewpager, MainActivity.this);

                new ShowcaseView.Builder(MainActivity.this)
                        .setTarget(targetList)
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTitle("Lista de productos")
                        .setContentText("Seleccione un producto para ver su detalle")
                        .hideOnTouchOutside()
                        .build();
            }
        }, withDelay);
        //Tracker
        TrackerHelper.sendEvent("ShowCase", "mostrado showcase de lista");
    }

    private void presentShowcaseView() {
        Target viewTarget = new Target() {
            @Override
            public Point getPoint() {
                return new ViewTarget(findViewById(R.id.action_refresh)).getPoint();
            }
        };

        final ShowcaseView showcaseView;
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setStyle(R.style.CustomShowcaseTheme3)
                .setContentTitle("Acciones en la barra")
                .setContentText("Acceda a su carro de compras")
                .build();

        showcaseView.overrideButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showcaseView.hide();
                presentShowcaseList(1000);
            }
        });
        //Tracker
        TrackerHelper.sendEvent("ShowCase", "mostrado showcase de barra");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        if (!sharedPreferences.getBoolean("showcaseLoaded", false)) {
            editor.putBoolean("showcaseLoaded", true);
            editor.commit();
            presentShowcaseView();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Intent i = new Intent(MainActivity.this, ShoppingBagActivity.class);
            startActivity(i);
            return true;
        }

        //Tracker
        TrackerHelper.sendEvent("MenuItem", "seleccionado item " + id);

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new TileContentFragment(), "Tile");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        SurveyHandler.handleSurvey(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TrackerHelper.nameScreen(this);
    }
}
