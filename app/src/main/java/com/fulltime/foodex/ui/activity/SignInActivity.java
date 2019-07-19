package com.fulltime.foodex.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fulltime.foodex.R;
import com.fulltime.foodex.firebase.authentication.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.fulltime.foodex.firebase.authentication.Usuario.USER;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG_AUTH = "Auth";

    private FirebaseAuth auth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsa = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.activity_sign_in_button);
        signInButton.setOnClickListener((view -> openGoogleSignIn()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) proximaTela(user);
    }

    private void openGoogleSignIn() {
        Intent signInIntent = gsa.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signIn(GoogleSignInAccount currentUser) {
        AuthCredential credential = GoogleAuthProvider.getCredential(currentUser.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener((authResult -> proximaTela(authResult.getUser())))
                .addOnFailureListener((e -> Log.e(TAG_AUTH, "signIn: ", e)));
    }

    private void proximaTela(FirebaseUser currentUser) {
        Intent gerenciarActivity = new Intent(SignInActivity.this, GerenciarActivity.class);
        Usuario usuario = new Usuario(currentUser);
        gerenciarActivity.putExtra(USER, usuario);
        startActivity(gerenciarActivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK)
                GoogleSignIn.getSignedInAccountFromIntent(data)
                        .addOnSuccessListener(this::signIn)
                        .addOnFailureListener(e -> e.getMessage());
        }
    }

}
