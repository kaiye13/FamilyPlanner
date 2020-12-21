package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

import the.family.planner.models.Task;

public class DayViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private EditText dayEditText;
    private Button addTaskButton;
    private ListView taskListView;
    private TextView title;
    private int year;
    private int month;
    private int day;
    private FirebaseListOptions<Task> options;
    FirebaseListAdapter adapter;
    String date;
    //ArrayList<Task> tasks;
    //TaskListAdapter adapter;
    //Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
        setList();

    }

    private void setList() {
        Query query = mDatabaseReference.orderByChild("date").equalTo(date);
        options = new FirebaseListOptions.Builder<Task>()
                .setLayout(R.layout.adapter_view_layout)
                .setLifecycleOwner(DayViewActivity.this)
                .setQuery(query,Task.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView startTime = v.findViewById(R.id.startTimeLabel);
                TextView endTime = v.findViewById(R.id.endTimeLabel);
                TextView title = v.findViewById(R.id.titleLabel);

                Task task = (Task) model;
                //set views
                startTime.setText(task.getStart_time());
                endTime.setText(task.getEnd_time());
                title.setText(task.getTitle());

            }
        };

        taskListView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initializeItems() {
        dayEditText = (EditText) findViewById(R.id.dayEditText);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        taskListView = (ListView) findViewById(R.id.ShoppingList);
        title = findViewById(R.id.toolbarTitle);

        title.setText("Day Planner");
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void setListeners() {
        addTaskButton.setOnClickListener(v-> onClickAddTask());
        dayEditText.setOnClickListener(v-> onClickDay());
    }

    private void onClickDay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(DayViewActivity.this, (view, year, month, dayOfMonth) -> {
            month = month + 1;
            date = dayOfMonth + "/" + month + "/" + year;
            dayEditText.setText(date);
            setList();
        }, year, month, day);
        datePickerDialog.show();
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child(getString(R.string.dbnode_tasks));

    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}