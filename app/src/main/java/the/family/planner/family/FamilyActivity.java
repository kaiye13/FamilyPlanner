package the.family.planner.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import the.family.planner.R;

public class FamilyActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private TextView familyNameTV, familyIdTV;
    private Button JoinBTN, LeaveBTN, CreateBTN;

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
        JoinBTN.setOnClickListener(v->onJoinBTNClicked());
        LeaveBTN.setOnClickListener(v->onLeaveBTNClicked());
        CreateBTN.setOnClickListener(v->onCreateBTNClicked());
    }

    private void onCreateBTNClicked() {
        Intent intent = new Intent(this, CreateFamilyActivity.class);
        startActivity(intent);
    }

    private void onLeaveBTNClicked() {

    }


    private void onJoinBTNClicked() {
        Intent intent = new Intent(this, JoinFamilyActivity.class);
        startActivity(intent);
    }

    private void setWidgets() {

    }

    private void initializeItems() {
        familyIdTV = findViewById(R.id.FamilyIdTV);
        familyNameTV = findViewById(R.id.FamilyNameTV);
        JoinBTN = findViewById(R.id.JoinFamilyBTN);
        LeaveBTN = findViewById(R.id.LeaveFamilyBTN);
        CreateBTN = findViewById(R.id.CreateFamilyBTN);

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