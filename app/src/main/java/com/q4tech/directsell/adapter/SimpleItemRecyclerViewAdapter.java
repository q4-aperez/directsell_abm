package com.q4tech.directsell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.q4tech.directsell.activities.ProductListActivity;
import com.q4tech.directsell.R;
import com.q4tech.directsell.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.perez on 28/07/2016.
 */
public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private ProductClickListener listener;

    public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, ProductClickListener listener) {
        mValues = items;
        this.listener = listener;
    }

    public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items, ProductListActivity listener, String query) {
        mValues = new ArrayList<>();
        for (DummyContent.DummyItem item : items) {
            if (item.id.contains(query)) {
                mValues.add(item);
            }
        }
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClick(holder.mItem.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface ProductClickListener {
        void onProductClick(String id);
    }
}