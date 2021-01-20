package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import the.family.planner.models.User;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private Button saveBTN;
    private int year;
    private int month;
    private int day;
    private EditText nameET, birthdayET;
    private TextView familyTV, emailTV, ageTV;
    private String date;
    private String cDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
        setWidgets();

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
                    nameET.setText(name);
                    emailTV.setText(email);
                    familyTV.setText(familyId);
                    birthdayET.setText(birthdate);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListeners() {
        saveBTN.setOnClickListener(v->onSaveProfile());
        birthdayET.setOnClickListener(v->onClickDate());
    }

    private void onClickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, (view, year, month, dayOfMonth) -> {
            month = month + 1;
            date = dayOfMonth + "/" + month + "/" + year;
            birthdayET.setText(date);
            int age = calculateAge(date , cDate);
            ageTV.setText(Integer.toString(age));
        }, year, month, day);
        datePickerDialog.show();
    }

    private int calculateAge( String birthDate, String currentDate){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDateD = null;
        Date currentDateD = null;
        try {
            birthDateD = formatter.parse(birthDate);
            currentDateD = formatter.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int age = currentDateD.getYear() - birthDateD.getYear();
        if(currentDateD.getDay() < birthDateD.getDay()){
            age--;
        }

        return age;
    }

    private void onSaveProfile() {
        DatabaseReference referenceToAdd = reference.child(userId);
        String setName = nameET.getText().toString();
            String setAge = ageTV.getText().toString();
        String setBDay = birthdayET.getText().toString();

        referenceToAdd.child("name").setValue(setName);
        referenceToAdd.child("age").setValue(Integer.parseInt(setAge));
        referenceToAdd.child("birthdate").setValue(setBDay);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void initializeItems() {
        saveBTN = findViewById(R.id.SaveBTN);
        nameET = findViewById(R.id.NameET);
        emailTV = findViewById(R.id.EmailTV);
        ageTV = findViewById(R.id.ageTV);
        birthdayET = findViewById(R.id.BirthdayET);
        familyTV = findViewById(R.id.FamilyTV);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        cDate = day + "/" + (month + 1) + "/" + year;
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