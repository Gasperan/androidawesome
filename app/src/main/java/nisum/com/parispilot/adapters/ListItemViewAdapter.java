package nisum.com.parispilot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        TypedArray a = parent.getContext().getResources().obtainTypedArray(R.array.products_picture);
        String product = parent.getContext().getResources().getStringArray(R.array.products)[data];
        String image = parent.getContext().getResources().getStringArray(R.array.products_picture)[data];
        ((TextView)convertView.findViewById(R.id.productName)).setText(product);
        ((ImageView)convertView.findViewById(R.id.productImage)).setImageDrawable(a.getDrawable(position));



        return convertView;

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
