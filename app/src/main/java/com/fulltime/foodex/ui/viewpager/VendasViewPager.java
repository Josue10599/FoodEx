package com.fulltime.foodex.ui.viewpager;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fulltime.foodex.R;
import com.fulltime.foodex.ui.fragments.PagamentosFragments;
import com.fulltime.foodex.ui.fragments.VendasFragments;

public class VendasViewPager extends FragmentStatePagerAdapter{

    private final Context context;

    public VendasViewPager(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new PagamentosFragments();
        }
        return new VendasFragments();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 1) {
            return context.getString(R.string.payments);
        }
        return context.getString(R.string.sales);
    }
}
