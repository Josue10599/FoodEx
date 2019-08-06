package com.fulltime.foodex.ui.scroll;

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

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.fulltime.foodex.helper.eventbus.HideFAB;
import com.fulltime.foodex.helper.eventbus.ShowFAB;

import org.greenrobot.eventbus.EventBus;

@SuppressLint("NewApi")
public class ScrollChangeListener extends RecyclerView.OnScrollListener implements NestedScrollView.OnScrollChangeListener, View.OnScrollChangeListener {

    private EventBus eventBus = EventBus.getDefault();

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) eventBus.post(new HideFAB()); // Scroll Down
        else if (dy < 0) eventBus.post(new ShowFAB()); // Scroll Up
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) eventBus.post(new HideFAB());
        else eventBus.post(new ShowFAB());
    }

    @Override
    public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > oldScrollY) eventBus.post(new HideFAB());
        else eventBus.post(new ShowFAB());
    }
}