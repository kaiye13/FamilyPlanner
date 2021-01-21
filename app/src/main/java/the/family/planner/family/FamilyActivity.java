package the.family.planner.family;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import the.family.planner.R;
import the.family.planner.models.Family;
import the.family.planner.models.User;

public class FamilyActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference userreference;
    private String userId;
    private TextView familyNameTV, familyIdTV;
    private Button JoinBTN, LeaveBTN, CreateBTN;
    private String familyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        addToolbar();
        addDatabase();
        initializeItems();
        setWidgets();
        setListeners();
    }

    private void setListeners() {
        JoinBTN.setOnClickListener(v -> onJoinBTNClicked());
        LeaveBTN.setOnClickListener(v -> onLeaveBTNClicked());
        CreateBTN.setOnClickListener(v -> onCreateBTNClicked());
    }

    private void onCreateBTNClicked() {
        Intent intent = new Intent(this, CreateFamilyActivity.class);
        startActivity(intent);
    }

    private void onLeaveBTNClicked() {
        userreference.child(userId).child("family_id").removeValue();
        reference.child(familyId).child("members").child(userId).removeValue();
    }


    private void onJoinBTNClicked() {
        Intent intent = new Intent(this, JoinFamilyActivity.class);
        startActivity(intent);
    }

    private void setWidgets() {
        userreference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    if (userProfile.getFamily_id() != null) {
                        familyId = userProfile.getFamily_id();
                        familyIdTV.setText(familyId);
                        reference.child(familyId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Family family = snapshot.getValue(Family.class);

                                if(family != null){
                                familyNameTV.setText(family.getName());
                            }}
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initializeItems() {
        familyIdTV = findViewById(R.id.FamilyIdTV);
        familyNameTV = findViewById(R.id.FamilyNameTV);
        JoinBTN = findViewById(R.id.JoinFamilyBTN);
        LeaveBTN = findViewById(R.id.LeaveFamilyBTN);
        CreateBTN = findViewById(R.id.CreateFamilyBTN);

    }

    private void addDatabase() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("families");
        userreference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}