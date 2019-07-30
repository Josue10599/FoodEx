package com.fulltime.foodex.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.fulltime.foodex.firebase.authentication.Usuario.USER;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG_AUTH = "Auth";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) proximaTela(user);
        else initLogin();
    }

    private void initLogin() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        Intent signIn = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_iconfoodex) // Set logo drawable
                .setTheme(R.style.MyTheme_DayNight) // Set theme
                .build();
        signIn.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(
                signIn,
                RC_SIGN_IN);
    }

    private void proximaTela(FirebaseUser currentUser) {
        Intent gerenciarActivity = new Intent(getApplicationContext(), GerenciarActivity.class);
        Usuario user = new Usuario(currentUser);
        gerenciarActivity.putExtra(USER, user);
        startActivity(gerenciarActivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            assert response != null;
            if (resultCode == Activity.RESULT_OK) {
                signIn(response);
            } else {
                errorSignIn(Objects.requireNonNull(response.getError()));
            }
        }
    }

    private void errorSignIn(Exception response) {
        Log.e(TAG_AUTH, Objects.requireNonNull(response.getMessage()));
        Toast.makeText(this, R.string.fui_error_unknown, Toast.LENGTH_SHORT).show();
    }

    private void signIn(IdpResponse response) {
        auth.signInWithCustomToken(Objects.requireNonNull(response.getIdpToken()))
                .addOnSuccessListener((authResult -> proximaTela(authResult.getUser())))
                .addOnFailureListener(this::errorSignIn);
    }

}
