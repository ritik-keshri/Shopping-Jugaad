package com.example.shoppingjugaad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    private EditText name, emailId, phone, password;
    private Button registerBut;
    private TextView loginBut;
    private ProgressBar progressBar;
    private SignInButton signUpButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String userId;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        init();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrName = name.getText().toString();
                String email = emailId.getText().toString();
                String phoneNumber = phone.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(usrName)) {
                    name.setError("Name can't be empty");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Email Id can't be empty");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailId.setError("Invalid Email Id");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    phone.setError("Phone Number can't be empty");
                    return;
                }
                if (phoneNumber.length() != 10) {
                    phone.setError("Invalid Phone Number");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password can't be empty");
                    return;
                }
                if (pass.length() < 8) {
                    password.setError("Password should be atleast 8 character long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Verification Email has been Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Verification Email", "Verification Mail : " + e.getMessage());
                                }
                            });

                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", usrName);
                            user.put("email", email);
                            user.put("phone", phoneNumber);
                            user.put("isAdmin", "0");
                            documentReference.set(user);
                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), RegLogChoice.class);
                            startActivity(i);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken("710683446090-4j63cjffe96ebhtp2il0tal7p8mls0bu.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginBut.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), RegLogChoice.class);
            startActivity(i);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
        }
    }

    private void init() {
        name = findViewById(R.id.name);
        emailId = findViewById(R.id.emailId);
        phone = findViewById(R.id.phoneNumber);
        signUpButton = findViewById(R.id.input_google);
        password = findViewById(R.id.pass);
        registerBut = findViewById(R.id.signUp);
        loginBut = findViewById(R.id.newSignIn);
        progressBar = findViewById(R.id.progresBar);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            startActivity(i);
                        } else {
                        }
                    }
                });
    }
}