package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import the.family.planner.models.Task;

public class EditTask extends AppCompatActivity {
    private String data;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText taskTitle, start, end;
    private TextView date;
    private Button editTask;
    private TextInputLayout description;
    Task t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        data = getIntent().getStringExtra("taskidEdit");

        addDatabase();
        addToolbar();
        initializeItems();
        setListeners();

    }

    private void setListeners() {
        editTask.setOnClickListener(v-> onClickEditTask());
        start.setOnClickListener(v->setStartTime());
        end.setOnClickListener(v->setEndTime());
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

    private void timePick(EditText timetext){
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditTask.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timetext.setText(hourOfDay + ":" + minute);
            }
        }, 0, 0, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    private void onClickEditTask() {
        String title = taskTitle.getText().toString();
        String startTime = start.getText().toString();
        String endTime = end.getText().toString();
        String descriptionTask = description.getEditText().getText().toString();

        updateTask(data, title, startTime, endTime, descriptionTask);

        Intent intent = new Intent(EditTask.this, ShowTask.class);
        intent.putExtra("task_id", data);
        startActivity(intent);
    }

    private boolean updateTask(String id, String title, String startTime, String endTime, String description){
        DatabaseReference reference = mDatabaseReference.child(id);
        reference.child("title").setValue(title);
        reference.child("description").setValue(description);
        reference.child("start_time").setValue(startTime);
        reference.child("end_time").setValue(endTime);
        return true;

    }
    private void setEndTime() {
        timePick(end);
    }

    private void setStartTime() {
        timePick(start);
    }

    private void initializeItems() {
        taskTitle = findViewById(R.id.taskTitleEditText);
        start = findViewById(R.id.editTextStartTime);
        end = findViewById(R.id.editTextEndTime);
        description = findViewById(R.id.taskDescriptionTextInput);
        date = findViewById(R.id.dateTextView);
        editTask = findViewById(R.id.editTaskBtn);

        Query query = mDatabaseReference.orderByKey().equalTo(data);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    t = singleSnapshot.getValue(Task.class);
                    taskTitle.setText(t.getTitle());
                    description.getEditText().setText(t.getDescription());
                    start.setText(t.getStart_time());
                    end.setText(t.getEnd_time());
                    date.setText(t.getDate());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference= mFireBaseDatabase.getReference().child(getString(R.string.dbnode_tasks));

    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}