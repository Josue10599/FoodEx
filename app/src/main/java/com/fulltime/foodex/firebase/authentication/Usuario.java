package com.fulltime.foodex.firebase.authentication;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.Objects;

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
        photoUrl = Objects.requireNonNull(user.getPhotoUrl()).toString();
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
