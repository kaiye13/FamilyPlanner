package the.family.planner.family;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import the.family.planner.DayViewActivity;
import the.family.planner.R;
import the.family.planner.models.Family;
import the.family.planner.models.User;
import the.family.planner.task.AddTaskDayActivity;

public class CreateFamilyActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference userreference;

    private String userId;
    private EditText familyName;
    private Button createBTN;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        addToolbar();
        addDatabase();
        initializeItems();
        setWidgets();
        setListeners();
    }

    private void setListeners() {
        createBTN.setOnClickListener(v->onCreateClicked());
    }

    private void onCreateClicked() {
        Family family = new Family();
        family.setName(familyName.getText().toString());
         String id = reference.push().getKey();
         family.setFamily_id(id);
         reference.child(id).setValue(family);
         reference.child(id).child("members").child(userId).setValue(userName);
         userreference.child(userId).child("family_id").setValue(id);

        Intent intent = new Intent(CreateFamilyActivity.this, FamilyActivity.class);
        startActivity(intent);
    }

    private void setWidgets() {

        userreference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    userName = userProfile.getName(); }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initializeItems() {
        familyName = findViewById(R.id.FamilyNameET);
        createBTN = findViewById(R.id.CreateFamilyBUTTON);
    }

    private void addDatabase() {
        user= FirebaseAuth.getInstance().getCurrentUser();
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