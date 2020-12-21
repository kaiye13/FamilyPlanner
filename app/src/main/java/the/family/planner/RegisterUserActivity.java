package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import the.family.planner.models.User;


public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterUserActivity";

    private FirebaseAuth mAuth;
    private EditText fullNameEditText,  emailEditText, conEmailEditText, passwordEditText, conPasswordEditText;
    private Button registerButton;

    private String email, conEmail, password, conPassword, fullName;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        fullNameEditText = (EditText) findViewById(R.id.EmailEditText);
        emailEditText = (EditText) findViewById(R.id.emailAddressEditText);
        conEmailEditText =  (EditText) findViewById(R.id.confirmEmailAddressEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        conPasswordEditText =  (EditText) findViewById(R.id.confirmPasswordEditText);
        registerButton =  (Button) findViewById(R.id.registerButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        registerButton.setOnClickListener(this);
        addToolbar();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.registerButton){
            registerUser();
        }
    }
    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setValues(){
         email = emailEditText.getText().toString();
         conEmail = conEmailEditText.getText().toString();
         password = passwordEditText.getText().toString().trim();
         conPassword = conPasswordEditText.getText().toString().trim();
         fullName = fullNameEditText.getText().toString().trim();
    }


    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        setValues();
        //check null valued EditTexts
        if(!(isEmpty(email)) && !(isEmpty(password)) && !(isEmpty(conPassword)) && !(isEmpty(conEmail))&& !(isEmpty(fullName))){
            //check user had a company email adress{
            if(doStringsMatch(email,conEmail)){

                //check if passwords match
                if(doStringsMatch(password,conPassword)){
                    registerNewEmail(email, password);

                }else {
                    Toast.makeText(RegisterUserActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(RegisterUserActivity.this, "emails do not match", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(RegisterUserActivity.this, "You must fill out all the fields", Toast.LENGTH_LONG).show();
        }
    }

    private void registerNewEmail(String email, String password){
        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete : onComplete: " + task.isSuccessful());

                        if(task.isSuccessful()){
                            Log.d(TAG,"oncomplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            User user = new User();
                            user.setName(fullName);
                            user.setFamily_id("");
                            user.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            FirebaseDatabase.getInstance().getReference()
                                    .child(getString(R.string.dbnode_users))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseAuth.getInstance().signOut();

                                            //redirect the user to the login screen
                                            redirectLoginScreen();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterUserActivity.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();

                                    //redirect the user to the login screen
                                    redirectLoginScreen();
                                }
                            });

                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterUserActivity.this, "Unable to Register", Toast.LENGTH_LONG).show();
                        }
                        hideDialog();
                    }
                }
        );
    }

    private void showDialog(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void hideDialog(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean doStringsMatch(String s1 , String s2){
        return s1.equals(s2);
    }

    private boolean isEmpty(String string) {return string.equals("");}
}