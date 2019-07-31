package com.fulltime.foodex.searchablespinner;

/*
  FoodEx is a sales management application.
  Copyright (C) 2019 Josue Lopes.
  <p>
  FoodEx is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  <p>
  FoodEx is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  <p>
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

    private final Context mContext;
    private ArrayList<?> mBackupObjects;
    private ArrayList<?> mObjects;
    private final StringFilter mStringFilter = new StringFilter();

    public SearchableSpinnerAdapter(Context context, ArrayList<?> objects) {
        mContext = context;
        mObjects = objects;
        mBackupObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mObjects.get(position).hashCode();
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

    public void setList(ArrayList<?> objects) {
        mObjects = objects;
        mBackupObjects = objects;
        notifyDataSetChanged();
    }

    class StringFilter extends Filter {

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