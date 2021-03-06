package the.family.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import the.family.planner.auth.LoginActivity;
import the.family.planner.auth.ProfileActivity;
import the.family.planner.family.FamilyActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton monthImageButton, weekImageButton, dayImageButton, shoppingImageButton;
    Button familyButton, logOutButton, profileButton;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
    }

    private void initializeItems() {
        monthImageButton = findViewById(R.id.monthPlannerImageButton);
        weekImageButton = findViewById(R.id.weekPlannerImageButton);
        dayImageButton = findViewById(R.id.dayPlannerImageButton);
        shoppingImageButton = findViewById(R.id.shoppingListImageButton);
        familyButton = findViewById(R.id.familyBTN);
        logOutButton = findViewById(R.id.familyBTN2);
        profileButton = findViewById(R.id.profileBTN);
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference("tasks");
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Familiy Planner");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setListeners() {
        dayImageButton.setOnClickListener(v-> onClickDayBTN());
        weekImageButton.setOnClickListener(v-> onClickWeekBTN());
        monthImageButton.setOnClickListener(v-> onClickMonthBTN());
        shoppingImageButton.setOnClickListener(v-> onClickShopBTN());
        logOutButton.setOnClickListener(v-> onClickLogOutBTN());
        profileButton.setOnClickListener(v-> onClickProfileBTN());
        familyButton.setOnClickListener(v->onClickFamilyBTN());

    }

    private void onClickFamilyBTN() {
        Intent intent = new Intent(this, FamilyActivity.class);
        startActivity(intent);
    }

    private void onClickProfileBTN() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void onClickMonthBTN() {
        Intent intent = new Intent(this, MonthViewActivity.class);
        startActivity(intent);
    }

    private void onClickLogOutBTN() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    private void onClickShopBTN() {
        Intent intent = new Intent(this, ShoppingActivity.class);
        startActivity(intent);
    }

    private void onClickWeekBTN() {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }

    private void onClickDayBTN(){
        Intent intent = new Intent(this, DayViewActivity.class);
        startActivity(intent);
    }

}