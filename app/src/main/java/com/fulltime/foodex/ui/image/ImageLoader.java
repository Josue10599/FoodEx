package com.fulltime.foodex.ui.image;

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
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.fulltime.foodex.model.Cliente;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.Objects;

import static com.nostra13.universalimageloader.core.ImageLoader.getInstance;

public class ImageLoader {

    private CircularProgressDrawable drawableLoading;
    private DisplayImageOptions displayImageOptions;
    private ImageLoaderConfiguration imageLoaderConfiguration;
    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    private void configuraCircularProgress() {
        getInstance().init(imageLoaderConfiguration);
        drawableLoading = new CircularProgressDrawable(context);
        drawableLoading.setStyle(CircularProgressDrawable.DEFAULT);
        drawableLoading.start();
    }

    private void configuraApresentacaoDaImagem(TextDrawable textDrawable) {
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .showImageForEmptyUri(textDrawable)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnFail(textDrawable)
                .showImageOnLoading(drawableLoading)
                .build();
    }

    private void configuraImageLoader() {
        imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
                Objects.requireNonNull(context))
                .writeDebugLogs()
                .build();
    }

    private TextDrawable userDefaultImageCreate(Usuario usuario) {
        return TextDrawable.builder()
                .buildRound(String.valueOf(usuario.getDisplayName().charAt(0)),
                        Objects.requireNonNull(context).getResources().getColor(R.color.color_secondary));
    }

    public TextDrawable getDrawableCliente(Cliente cliente) {
        return TextDrawable.builder()
                .buildRound(cliente.primeiraLetraNome() + cliente.primeiraLetraSobrenome(),
                        Objects.requireNonNull(context).getResources().getColor(R.color.color_secondary));
    }

    public void getPhotoUsuario(Usuario usuario, ImageView fotoPerfil) {
        configuraImageLoader();
        configuraCircularProgress();
        configuraApresentacaoDaImagem(userDefaultImageCreate(usuario));
        getInstance().displayImage(usuario.getPhotoUrl(), fotoPerfil, displayImageOptions);
    }
}