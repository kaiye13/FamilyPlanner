package the.family.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;

import the.family.planner.models.Task;
import the.family.planner.models.User;

public class AddTaskActivity extends AppCompatActivity  {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText titleET,  startTimeET, endTimeET;
    private Button addTask;
    private TextInputLayout descriptionET;
    private TextView title;
    private User user;
    private FirebaseUser firebaseAuth;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
    }

    private void initializeItems() {
        title = findViewById(R.id.toolbarTitle);
        titleET = findViewById(R.id.taskTitleEditText);
        descriptionET = findViewById(R.id.taskDescriptionTextInput);
        startTimeET = findViewById(R.id.editTextStartTime);
        endTimeET = findViewById(R.id.editTextEndTime);
        addTask = findViewById(R.id.addTaskBtn);
        title.setText("Add A Task");
    }

    private void setListeners() {
        addTask.setOnClickListener(v-> onClickAddTask());
        startTimeET.setOnClickListener(v->setStartTime());
        endTimeET.setOnClickListener(v->setEndTime());
    }

    private void timePick(EditText timetext){
        timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timetext.setText(hourOfDay + ":" + minute);
            }
        }, 0, 0,  DateFormat.is24HourFormat(this));
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
        task.setDate("18/12/2020");
        task.setUser_id(firebaseAuth.getUid());
        mDatabaseReference.push().setValue(task);

    }


    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("tasks");
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}