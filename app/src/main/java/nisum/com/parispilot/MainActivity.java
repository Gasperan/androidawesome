package nisum.com.parispilot;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // uso de butterknife para linkear la vista con la logica
    @BindView(R.id.textView) TextView mTextView;
    @BindView(R.id.toolbar)  Toolbar mToolbar;
    String tittleDialog;
    String bodyDialog;
    String buttonDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tittleDialog = getResources().getString(R.string.dialog_title);
        bodyDialog = getResources().getString(R.string.dialog_body);
        buttonDialog = getResources().getString(R.string.dialog_button);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(tittleDialog)
                .setMessage(bodyDialog)
                .setPositiveButton(buttonDialog, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent surveyIntent = new Intent(MainActivity.this, SurveyActivity.class);
                        startActivity(surveyIntent);
                    }

                })
                .show();
    }
}

