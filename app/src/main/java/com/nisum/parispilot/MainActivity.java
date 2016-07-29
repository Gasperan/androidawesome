package com.nisum.parispilot;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nisum.parispilot.model.Barcode;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class MainActivity extends AppCompatActivity {


    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final String SHOWCASE_ID = "main_showcase";

    private static boolean splashLoaded = false;
    private final int splashSeconds = 1000;

    //fields
    private ImageView mBarcodeButton;
    private ImageView mQRButton;
    private ImageView mManualbutton;

    String tittleDialog;
    String bodyDialog;
    String buttonDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBarcodeButton = (ImageView) findViewById(R.id.barcodeButton);
        mQRButton = (ImageView) findViewById(R.id.qrcodeButton);
        mManualbutton = (ImageView) findViewById(R.id.manualbutton);

        mBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canAccessCamera()) {
                    startActivityForResult(new Intent(MainActivity.this, Barcode.class), 1);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
                    }
                }
            }
        });

        mQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, Barcode.class), 1);
            }
        });

        mManualbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        tittleDialog = getResources().getString(R.string.dialog_title);
        bodyDialog = getResources().getString(R.string.dialog_body);
        buttonDialog = getResources().getString(R.string.dialog_button);

        if (!splashLoaded) {
            //setContentView(R.layout.splash);
            splashLoaded = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //setContentView(R.layout.activity_main);
                    if (!canAccessCamera()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
                        }
                    }
                }
            }, splashSeconds);

        } else {
            //setContentView(R.layout.activity_main);
            if (!canAccessCamera()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
                }
            }
        }

        presentShowcaseView(300); // one second delay
    }

    private void presentShowcaseView(int withDelay) {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(withDelay);

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this,SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(mBarcodeButton, "Lector de codigo de barras",
                "Aqui puedes escanear codigos de barra", "OK");

        sequence.addSequenceItem(mQRButton, "Lector de codigos QR",
                "con este boton escaneas codigos QR", "OK");

        sequence.addSequenceItem(mManualbutton, "Escaner manual",
                "Además, si ninguno de los anteriores te ha funcionado, puedes intentar ingresar el codigo manualmente", "OK");

        sequence.start();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                String barcode = data.getStringExtra("BARCODE");
                if (!barcode.equals("NULL")) {
                    Log.d("barcode", barcode);
                    Toast.makeText(this, barcode, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, ResultActivity.class);
                    startActivity(intent);
                    //search.setQuery(barcode, true);
                    //search.setIconifiedByDefault(false);
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(tittleDialog)
                .setMessage(bodyDialog)
                .setPositiveButton(buttonDialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent surveyIntent = new Intent(MainActivity.this, SurveyActivity.class);
                        startActivity(surveyIntent);
                    }

                })
                .show();
    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, editText.getText(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}