package nisum.com.parispilot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseDrawer;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

    }

    private static class CustomShowcaseView implements ShowcaseDrawer {

        private final float width;
        private final float height;
        private final Paint eraserPaint;
        private final Paint basicPaint;
        private final int eraseColour;
        private final RectF renderRect;

        public CustomShowcaseView(Resources resources) {
            width = resources.getDimension(R.dimen.custom_showcase_width);
            height = resources.getDimension(R.dimen.custom_showcase_height);
            PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
            eraserPaint = new Paint();
            eraserPaint.setColor(0xFFFFFF);
            eraserPaint.setAlpha(0);
            eraserPaint.setXfermode(xfermode);
            eraserPaint.setAntiAlias(true);
            eraseColour = resources.getColor(R.color.custom_showcase_bg);
            basicPaint = new Paint();
            renderRect = new RectF();
        }

        @Override
        public void setShowcaseColour(int color) {
            eraserPaint.setColor(color);
        }

        @Override
        public void drawShowcase(Bitmap buffer, float x, float y, float scaleMultiplier) {
            Canvas bufferCanvas = new Canvas(buffer);
            renderRect.left = x - width / 2f;
            renderRect.right = x + width / 2f;
            renderRect.top = y - height / 2f;
            renderRect.bottom = y + height / 2f;


            bufferCanvas.drawRect(renderRect, eraserPaint);
        }

        @Override
        public int getShowcaseWidth() {
            return (int) width;
        }

        @Override
        public int getShowcaseHeight() {
            return (int) height;
        }

        @Override
        public float getBlockedRadius() {
            return width;
        }

        @Override
        public void setBackgroundColour(int backgroundColor) {
            // No-op, remove this from the API?
        }

        @Override
        public void erase(Bitmap bitmapBuffer) {
            bitmapBuffer.eraseColor(eraseColour);
        }

        @Override
        public void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer) {
            canvas.drawBitmap(bitmapBuffer, 0, 0, basicPaint);
        }

    }

    private void showShowcaseList(){

        Target targetList = new ViewTarget(R.id.viewpager, this);


        new ShowcaseView.Builder(this)
                .setTarget(targetList)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Lista de productos")
                .setContentText("Seleccione un producto para ver su detalle")
                .hideOnTouchOutside()
                .build();


        /*
        new ShowcaseView.Builder(this)
                .setTarget(targetList)
                .setContentTitle("Lista de productos")
                .setContentText("Seleccione un producto para ver su detalle")
                .setShowcaseDrawer(new CustomShowcaseView(getResources()))
                .build();
        */
    }

    private void presentShowcaseView(int withDelay) {


        Target viewTarget = new Target() {
            @Override
            public Point getPoint() {
                return new ViewTarget(findViewById(R.id.action_refresh)).getPoint();
            }
        };

        final ShowcaseView showcaseView;
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(viewTarget)
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("Acciones en la barra")
                .setContentText("Acceda a su carro de compras")
                .build();

        showcaseView.overrideButtonClick(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showcaseView.hide();
                showShowcaseList();
            }
        });



        /*
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(withDelay);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        sequence.setConfig(config);

        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        sequence.addSequenceItem(appBarLayout, "Toolbar titulo","Descripcion toolbar titulo", "OK");

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        sequence.addSequenceItem(viewPager, "ViewPager titulo","Descripcion viewPager titulo", "OK");

        sequence.start();
        */



        //Queremos a esta lib de 1era iter enfocar un icono de accion de la app bar

        /*
        View view = findViewById(R.id.toolbar);
        new MaterialShowcaseView.Builder(this)
                .setTarget(view)
                .setDismissText("GOT IT")
                .setContentText("Example of how to setup a MaterialShowcaseView for menu items in action bar.")
                .show();
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        presentShowcaseView(300);
        return true;
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

}
