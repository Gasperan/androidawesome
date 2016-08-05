package nisum.com.parispilot;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nisum.com.parispilot.adapters.ListItemViewAdapter;

public class ShoppingBagActivity extends AppCompatActivity implements OnItemClicked{
    List<Integer> indexItems = new ArrayList<Integer>();
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        t = (TextView) findViewById(R.id.totalEdit);
        indexItems.add(0);
        indexItems.add(1);
        indexItems.add(2);
        doInBackground();

    }

    private void getListDataResponse(List<Integer> indexItems) {
        ListView listview = (ListView)findViewById(R.id.listItemsAdded);
        ListItemViewAdapter adapter = new ListItemViewAdapter(this);
        listview.setAdapter(adapter);
        adapter.setOnItemClicked(this);
        adapter.setData(indexItems);
    }


    private void doInBackground(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                backToMainThreadWithResponse(indexItems);
            }
        });

        thread.start();
    }

    private void backToMainThreadWithResponse(final List<Integer> indexItems) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getListDataResponse(indexItems);
            }
        });
    }


    @Override
    public void onClick(int price) {
        System.out.println("pico");
        t.setText(price+"");
    }
}
