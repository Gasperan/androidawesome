package nisum.com.parispilot;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        presentShowcaseView(300);
    }

    private void presentShowcaseView(int withDelay) {

        //ImageView imageView = (ImageView)findViewById(R.id.tile_picture);

        //CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(withDelay);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        sequence.setConfig(config);
        //sequence.addSequenceItem(coordinatorLayout, "Scroll to move", "Descripcion", "OK");


        /*
        sequence.addSequenceItem(imageView, "Otra funcionalidad",
                "Descripcion de la otra funcionalidad", "OK");

        sequence.addSequenceItem(mBarcodeButton, "Lector de codigo de barras",
                "Aqui puedes escanear codigos de barra", "OK");

        sequence.addSequenceItem(mQRButton, "Lector de codigos QR",
                "con este boton escaneas codigos QR", "OK");

        sequence.addSequenceItem(mManualbutton, "Escaner manual",
                "Además, si ninguno de los anteriores te ha funcionado, puedes intentar ingresar el codigo manualmente", "OK");
        */

        sequence.start();
    }

    // Add Fragments to Tabs
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


}
