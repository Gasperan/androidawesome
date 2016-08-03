package nisum.com.parispilot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nisum.com.parispilot.R;
import nisum.com.parispilot.models.Item;

/**
 * Created by thomas on 02-08-16.
 */
public class ListItemViewAdapter extends BaseAdapter {

    private List<Integer> mDataList = new ArrayList<>();
    private LayoutInflater mInflater;

    public ListItemViewAdapter(Context cxt){
        mInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<Integer> dataList){
        this.mDataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int data = mDataList.get(position);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.shop_bag_view,null);

        }
        String product = parent.getContext().getResources().getStringArray(R.array.products)[data];
        String description = parent.getContext().getResources().getStringArray(R.array.products_desc)[data];
        String details = parent.getContext().getResources().getStringArray(R.array.products_details)[data];
        ((TextView)convertView.findViewById(R.id.dateValue)).setText(product);
        ((TextView)convertView.findViewById(R.id.maxValue)).setText(description);
        ((TextView)convertView.findViewById(R.id.humidityValue)).setText(details);
        ((TextView)convertView.findViewById(R.id.minValue)).setText(product);
        ((TextView)convertView.findViewById(R.id.windValue)).setText(details);

        return convertView;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
