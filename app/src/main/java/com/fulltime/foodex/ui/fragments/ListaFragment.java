package com.fulltime.foodex.ui.fragments;

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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fulltime.foodex.R;
import com.fulltime.foodex.ui.viewpager.VendasViewPager;
import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class ListaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vendasView = inflater.inflate(R.layout.fragment_lista, container, false);
        configuraTab(vendasView);
        return vendasView;
    }


    private void configuraTab(View vendasView) {
        TabLayout tabLayout = vendasView.findViewById(R.id.fragment_vendas_tab_layout);
        ViewPager viewPager = vendasView.findViewById(R.id.view_pager);
        VendasViewPager adapter = new VendasViewPager(getFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
