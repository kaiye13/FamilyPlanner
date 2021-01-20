package the.family.planner.task;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import the.family.planner.MonthViewActivity;
import the.family.planner.R;
import the.family.planner.models.Task;
import the.family.planner.models.User;

public class AddTaskMonthActivity extends AppCompatActivity  {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText titleET,  startTimeET, endTimeET;
    private Button addTask;
    private TextInputLayout descriptionET;
    private TextView title, dateTV;


    private User user;
    private FirebaseUser firebaseAuth;
    TimePickerDialog timePickerDialog;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();

        String date = getIntent().getStringExtra("date");
    }

    private void initializeItems() {
        title = findViewById(R.id.toolbarTitle);
        titleET = findViewById(R.id.taskTitleEditText);
        descriptionET = findViewById(R.id.taskDescriptionTextInput);
        startTimeET = findViewById(R.id.editTextStartTime);
        endTimeET = findViewById(R.id.editTextEndTime);
        addTask = findViewById(R.id.addTaskBtn);
        dateTV = findViewById(R.id.dateTextView);
        title.setText("Add A Task");
        date = getIntent().getStringExtra("date");
        dateTV.setText(date);

    }

    private void setListeners() {
        addTask.setOnClickListener(v-> onClickAddTask());
        startTimeET.setOnClickListener(v->setStartTime());
        endTimeET.setOnClickListener(v->setEndTime());
    }

    private void timePick(EditText timetext){
        timePickerDialog = new TimePickerDialog(AddTaskMonthActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    timetext.setText(String.format("%02d:%02d", hourOfDay, minute));


            }
        }, 0, 0,true  );
        timePickerDialog.show();
    }
    private void setEndTime() {
        timePick(endTimeET);
    }

    private void setStartTime() {
        timePick(startTimeET);
    }

    private void onClickAddTask() {
        Task task = new Task();
        task.setTitle(titleET.getText().toString().trim());
        task.setStart_time(startTimeET.getText().toString().trim());
        task.setEnd_time(endTimeET.getText().toString().trim());
        task.setDescription(descriptionET.getEditText().getText().toString().trim());
        task.setDate(date);
        task.setUser_id(firebaseAuth.getUid());
        String id = mDatabaseReference.push().getKey();
        task.setTask_id(id);
        mDatabaseReference.child(date).child("tasks").child(id).setValue(task);

        Intent intent = new Intent(AddTaskMonthActivity.this, MonthViewActivity.class);
        intent.putExtra("dateToMonth", date);
        startActivity(intent);

    }


    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("dates");
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}