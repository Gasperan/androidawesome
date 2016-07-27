package nisum.com.parispilot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nisum.com.parispilot.model.Barcode;

public class MainActivity extends AppCompatActivity {

    // uso de butterknife para linkear la vista con la logica
    @BindView(R.id.textView) TextView mTextView;
    @BindView(R.id.barcodeButton) ImageView mbarcodeButton;
    @BindView(R.id.qrcodeButton) ImageView mQRCodeButton;
    @BindView(R.id.manualbutton) ImageView mManualInputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.barcodeButton)
    public void onClickBarcode(View view) {
        //Toast.makeText(this, "barcode button", Toast.LENGTH_LONG).show();
        startActivityForResult(new Intent(MainActivity.this,Barcode.class),1 );
    }

    @OnClick(R.id.qrcodeButton)
    public void onClickQRCode(View view) {
        //Toast.makeText(this, "qrcode button", Toast.LENGTH_LONG).show();
        startActivityForResult(new Intent(MainActivity.this,Barcode.class),1 );
    }

    @OnClick(R.id.manualbutton)
    public void onClickManualInputButton(View view) {
        Toast.makeText(this, "manual button", Toast.LENGTH_LONG).show();
    }
}
