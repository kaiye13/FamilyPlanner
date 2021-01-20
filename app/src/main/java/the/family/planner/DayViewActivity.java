package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Calendar;

import the.family.planner.models.Task;
import the.family.planner.task.AddTaskDayActivity;
import the.family.planner.task.ShowTask;

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
    ArrayList<Task> tasks;
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
        Query query = mDatabaseReference.child(date).child("tasks").orderByChild("start_time");
        options = new FirebaseListOptions.Builder<Task>()
                .setLayout(R.layout.adapter_view_layout)
                .setLifecycleOwner(DayViewActivity.this)
                .setQuery(query, Task.class)
                .build();
        adapter = new FirebaseListAdapter<Task>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Task task, int position) {
                TextView startTime = v.findViewById(R.id.startTimeLabel);
                TextView endTime = v.findViewById(R.id.endTimeLabel);
                TextView title = v.findViewById(R.id.titleLabel);
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
        tasks = new ArrayList<Task>();
        title.setText("Day Planner");
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if ((getIntent().getStringExtra("dateToDay")) != null) {
            date = getIntent().getStringExtra("dateToDay");
        } else {
            date = day + "-" + (month + 1) + "-" + year;
        }
        dayEditText.setText(date);
    }

    private void setListeners() {
        addTaskButton.setOnClickListener(v -> onClickAddTask());
        dayEditText.setOnClickListener(v -> onClickDay());
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getAdapter().getItem(position);
                Intent intent = new Intent(DayViewActivity.this, ShowTask.class);
                intent.putExtra("task_id", task.getTask_id());
                intent.putExtra("date",task.getDate());
                startActivity(intent);
            }
        });
    }

    private void onClickDay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(DayViewActivity.this, (view, year, month, dayOfMonth) -> {
            month = month + 1;
            date = dayOfMonth + "-" + month + "-" + year;
            dayEditText.setText(date);
            setList();
        }, year, month, day);
        datePickerDialog.show();
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskDayActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child(getString(R.string.dbnode_dates));

    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}