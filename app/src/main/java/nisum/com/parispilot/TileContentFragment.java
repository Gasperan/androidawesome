package nisum.com.parispilot;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Provides UI for the view with Tiles.
 */
public class TileContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView price;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tile, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            price = (TextView) itemView.findViewById(R.id.tile_price);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private final String[] mProducts;
        private final String[] mPrices;
        private final Drawable[] mProductsPictures;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mProducts = resources.getStringArray(R.array.products);
            mPrices = resources.getStringArray(R.array.products_prices);
            TypedArray a = resources.obtainTypedArray(R.array.products_picture);
            mProductsPictures = new Drawable[a.length()];
            for (int i = 0; i < mProductsPictures.length; i++) {
                mProductsPictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);




        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.picture.setImageDrawable(mProductsPictures[position % mProductsPictures.length]);
            String myText = mProducts[position % mProducts.length].substring(0,13);
            holder.name.setText(myText+"...");
            holder.price.setText(mPrices[position %mPrices.length]);
            holder.picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), DetailsActivity.class);
                    i.putExtra("product", position);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProducts.length;
        }


    }
}