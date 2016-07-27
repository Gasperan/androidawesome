package nisum.com.parispilot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nisum.com.parispilot.model.Barcode;

public class MainActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;


    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.barcodeButton)
    ImageView mbarcodeButton;
    @BindView(R.id.qrcodeButton)
    ImageView mQRCodeButton;
    @BindView(R.id.manualbutton)
    ImageView mManualInputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!canAccessCamera()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            }
        }
    }

    @OnClick(R.id.barcodeButton)
    public void onClickBarcode(View view) {
        if (canAccessCamera()) {
            startActivityForResult(new Intent(MainActivity.this, Barcode.class), 1);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            }
        }
    }

    @OnClick(R.id.qrcodeButton)
    public void onClickQRCode(View view) {
        startActivityForResult(new Intent(MainActivity.this, Barcode.class), 1);
    }

    @OnClick(R.id.manualbutton)
    public void onClickManualInputButton(View view) {
        Toast.makeText(this, "manual button", Toast.LENGTH_LONG).show();
    }

    //this captures the result from barcode and populates in the searchView.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            String barcode = data.getStringExtra("BARCODE");
            if (barcode.equals("NULL")) {

            } else {
                Log.d("barcode", barcode);
                Toast.makeText(this, barcode, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
                //search.setQuery(barcode, true);
                //search.setIconifiedByDefault(false);
            }
        }

    }

    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }
        return true;
    }
}
