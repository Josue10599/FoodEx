package com.fulltime.foodex.firebase.authentication;

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

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class Usuario implements Serializable {
    public static final String USER = "user";

    private final String uid;
    private final String displayName;
    private final String email;
    private final String photoUrl;
    private final String phoneNumber;

    public Usuario(FirebaseUser user) {
        uid = user.getUid();
        displayName = user.getDisplayName();
        email = user.getEmail();
        phoneNumber = user.getPhoneNumber();
        if (user.getPhotoUrl() != null)
            photoUrl = user.getPhotoUrl().toString();
        else photoUrl = "";
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
