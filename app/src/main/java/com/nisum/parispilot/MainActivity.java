package com.nisum.parispilot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class MainActivity extends AppCompatActivity{

    //private Button mButtonShow;
    private ImageView mBarcodeButton;
    private ImageView mQRButton;
    private ImageView mManualbutton;

    private static final String SHOWCASE_ID = "main_showcase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBarcodeButton = (ImageView) findViewById(R.id.barcodeButton);
        mQRButton = (ImageView) findViewById(R.id.qrcodeButton);
        mManualbutton = (ImageView) findViewById(R.id.manualbutton);

        presentShowcaseView(300); // one second delay
    }

    private void presentShowcaseView(int withDelay) {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(withDelay);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(mBarcodeButton,"Lector de codigo de barras",
                "Aqui puedes escanear codigos de barra", "GOT IT");

        sequence.addSequenceItem(mQRButton,"Lector de codigos QR",
                "con este boton escaneas codigos QR", "GOT IT");

        sequence.addSequenceItem(mManualbutton,"Escaner manual",
                "Además, si ninguno de los anteriores te ha funcionado, puedes intentar ingresar el codigo manualmente", "GOT IT");

        sequence.start();
    }


}