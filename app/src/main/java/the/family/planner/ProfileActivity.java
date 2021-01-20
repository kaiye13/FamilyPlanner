package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import the.family.planner.models.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private Button editBTN;
    private TextView nameTv,emailTv,ageTV,birthdayTV,familyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addToolbar();
        addDatabase();
        initializeItems();
        setWidgets();
        setListeners();
    }

    private void setWidgets() {
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String name = userProfile.getName();
                    String email = userProfile.getEmail();
                    String familyId = userProfile.getFamily_id();
                    String birthdate = userProfile.getBirthdate();
                    int age = userProfile.getAge();

                    if(age == Integer.valueOf(0)){
                        ageTV.setText("");
                    }else {
                        ageTV.setText(Integer.toString(age));
                    }
                    nameTv.setText(name);
                    emailTv.setText(email);
                    familyTV.setText(familyId);
                    birthdayTV.setText(birthdate);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListeners() {
    editBTN.setOnClickListener(v->onSaveProfile());
    }

    private void onSaveProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

    private void initializeItems() {
    editBTN = findViewById(R.id.EditBTN);
    nameTv = findViewById(R.id.NameTV);
        emailTv = findViewById(R.id.EmailTV);
    ageTV = findViewById(R.id.ageTV);
    birthdayTV = findViewById(R.id.BirthdayTV);
    familyTV = findViewById(R.id.FamilyTV);


    }


    private void addDatabase() {
    user= FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("users");
    userId = user.getUid();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}