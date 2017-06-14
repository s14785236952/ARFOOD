package com.example.peter.arfood;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class ARFood extends AppCompatActivity {

    //private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    public static String userEmail,userDisplayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arfood);
        googleSignIn();

    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getBaseContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                userEmail = acct.getEmail();
                userDisplayName = acct.getDisplayName();

                Toast.makeText(getBaseContext(), "Success Sign in", Toast.LENGTH_SHORT).show();
                startMainActivity();
                //Toast.makeText(getBaseContext(), userEmail + userDisplayName, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getBaseContext(), userLatitude + "  " + userLongitude, Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(getBaseContext(), "Fail to Sign in", Toast.LENGTH_SHORT).show();
    }




    private void startMainActivity(){

        Intent intent = new Intent(ARFood.this, MainActivity.class);
        startActivity(intent);
    }

}
