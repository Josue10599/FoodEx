package com.fulltime.foodex.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

    private ProgressBar carregando;

    private FirebaseAuth auth;
    private GoogleSignInClient gsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        configuraProgressBar();
        configuraBotaoSignInGoogle();
    }

    private void configuraProgressBar() {
        carregando = findViewById(R.id.activity_sign_in_progress);
        carregando.setActivated(true);
    }

    private void configuraBotaoSignInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
        carregando.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(currentUser.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener((authResult -> proximaTela(authResult.getUser())))
                .addOnFailureListener((e -> {
                    carregando.setVisibility(View.GONE);
                    Log.e(TAG_AUTH, "signIn: ", e);
                }));
    }

    private void proximaTela(FirebaseUser currentUser) {
        Intent gerenciarActivity = new Intent(SignInActivity.this, GerenciarActivity.class);
        gerenciarActivity.putExtra(USER, new Usuario(currentUser));
        startActivity(gerenciarActivity);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK)
                GoogleSignIn.getSignedInAccountFromIntent(data)
                        .addOnSuccessListener(SignInActivity.this::signIn)
                        .addOnFailureListener(e -> e.getMessage());
        }
    }

}
