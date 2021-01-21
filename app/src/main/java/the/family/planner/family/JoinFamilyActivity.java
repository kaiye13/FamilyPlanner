package the.family.planner.family;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import the.family.planner.R;
import the.family.planner.models.Family;
import the.family.planner.models.Task;
import the.family.planner.models.User;

public class JoinFamilyActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private DatabaseReference famreference;
    private String familyId;
    private String userId;
    private EditText codeET;
    private ListView memberList;
    private Button checkCodeBTN, joinFamBTN;
    private ArrayList<String> arrayList ;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_family);
        addToolbar();
        addDatabase();
        initializeItems();
        setWidgets();
        setListeners();
    }

    private void setListeners() {
        checkCodeBTN.setOnClickListener(v->onCheckCode());
        joinFamBTN.setOnClickListener(v->onJoin());
    }

    private void onJoin() {
        familyId = codeET.getText().toString();
        famreference.child(familyId).child("members").child(userId).setValue(userName);
        reference.child(userId).child("family_id").setValue(familyId);

        Intent intent = new Intent(JoinFamilyActivity.this, FamilyActivity.class);
        startActivity(intent);
    }

    private void onCheckCode() {
        familyId = codeET.getText().toString();
        famreference.child(familyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Family familyProfile = snapshot.getValue(Family.class);
                if(familyProfile != null){
                    HashMap<String,String> mems = familyProfile.getMembers();
                    Collection<String> values = mems.values();
                    arrayList = new ArrayList<String>(values);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(JoinFamilyActivity.this, android.R.layout.simple_list_item_1, arrayList );
                    memberList.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setWidgets() {

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
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
        codeET = findViewById(R.id.FamilyCodeET);
        memberList = findViewById(R.id.MembersLB);
        joinFamBTN = findViewById(R.id.JoinFBTN);
        checkCodeBTN = findViewById(R.id.CheckCodeBTN);
    }

    private void addDatabase() {
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        famreference = FirebaseDatabase.getInstance().getReference("families");

        userId = user.getUid();
    }
    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}