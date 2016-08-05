package nisum.com.parispilot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import nisum.com.parispilot.OnItemClicked;
import nisum.com.parispilot.R;
import nisum.com.parispilot.models.Item;

public class ListItemViewAdapter extends BaseAdapter {
    private static final String[] daysOfWeek = {"1"};
    private List<String> mDataList = new ArrayList<>();
    private LayoutInflater mInflater;
    public TextView subTotal;
    private OnItemClicked mListener;
    public int actualPosition;
    public int productPrice;
    public TextView productPriceTxt;
    public TextView priceTxt;
    public String product;
    public TextView productTxt;
    public TypedArray productImgArray;
    public ImageView productImg;
    public ImageView deleteButton;

    public void setOnItemClicked(OnItemClicked listener) {
        this.mListener = listener;
    }

    public ListItemViewAdapter(Context cxt){
        mInflater = LayoutInflater.from(cxt);
    }

    public void setData(List<String> dataList){
        this.mDataList = dataList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int data = new Integer(mDataList.get(position));

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.shop_bag_view,null);

        }

        final Spinner itemQuantity = ((Spinner)convertView.findViewById(R.id.itemQuantity));


        productImgArray = parent.getContext().getResources().obtainTypedArray(R.array.products_picture);
        product = parent.getContext().getResources().getStringArray(R.array.products)[data];
        productPrice = Integer.parseInt(parent.getContext().getResources().getStringArray(R.array.products_prices)[data]);
        deleteButton = ((ImageView)convertView.findViewById(R.id.delete));
        productTxt = ((TextView)convertView.findViewById(R.id.productName));
        productTxt.setText(product.substring(0,10));
        productImg = ((ImageView)convertView.findViewById(R.id.productImage));
        productImg.setImageDrawable(productImgArray.getDrawable(data));
        productPriceTxt = ((TextView)convertView.findViewById(R.id.productPrice));
        productPriceTxt.setText("$"+productPrice);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);

            }
        });

        /*ArrayAdapter<String> aa = new ArrayAdapter<String>(parent.getContext(), android.R.layout.simple_spinner_item, daysOfWeek);
        itemQuantity.setAdapter(aa);
        actualPosition = itemQuantity.getSelectedItemPosition();
        itemQuantity.setEnabled(false);*/

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
