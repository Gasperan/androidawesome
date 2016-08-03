package nisum.com.parispilot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nisum.com.parispilot.adapters.ListItemViewAdapter;

public class ShoppingBagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);
        Intent intent = getIntent();
        List<Integer> indexItems = new ArrayList<Integer>();
        indexItems.add(0);
        indexItems.add(1);
        indexItems.add(2);

        getListDataResponse(indexItems);
    }

    private void getListDataResponse(List<Integer> indexItems) {
        ListView listview = (ListView)findViewById(R.id.listItemsAdded);
        ListItemViewAdapter adapter = new ListItemViewAdapter(this);
        listview.setAdapter(adapter);
        adapter.setData(indexItems);
    }


}
