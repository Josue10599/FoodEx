package com.fulltime.foodex.searchablespinner;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fulltime.foodex.R;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView;

public class SearchableSpinnerAdapter extends BaseAdapter implements Filterable, ISpinnerSelectedView {

    private Context mContext;
    private ArrayList<Object> mBackupObjects;
    private ArrayList<Object> mObjects;
    private StringFilter mStringFilter = new StringFilter();

    public SearchableSpinnerAdapter(Context context, ArrayList<Object> objects) {
        mContext = context;
        mObjects = objects;
        mBackupObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects == null ? 0 : mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mObjects == null && position > 0)
            return mObjects.get(position).hashCode();
        else
            return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getNoSelectionView();
        TextView dispalyName = view.findViewById(R.id.item_lista_nome);
        dispalyName.setText(mObjects.get(position).toString());
        return view;
    }

    @Override
    public View getSelectedView(int position) {
        View view = getNoSelectionView();
        TextView dispalyName = view.findViewById(R.id.item_lista_nome);
        dispalyName.setText(mObjects.get(position).toString());
        return view;
    }

    @Override
    public View getNoSelectionView() {
        return View.inflate(mContext, R.layout.item_lista, null);
    }

    @Override
    public Filter getFilter() {
        return mStringFilter;
    }

    public class StringFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults filterResults = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                filterResults.count = mBackupObjects.size();
                filterResults.values = mBackupObjects;
                return filterResults;
            }
            final ArrayList<Object> filterStrings = new ArrayList<>();
            for (Object object : mBackupObjects) {
                if (object.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    filterStrings.add(object);
                }
            }
            filterResults.count = filterStrings.size();
            filterResults.values = filterStrings;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mObjects = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    }
}