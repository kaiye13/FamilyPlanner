package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

import the.family.planner.models.Task;
import the.family.planner.task.AddTaskMonthActivity;
import the.family.planner.task.ShowTask;

public class MonthViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView dateText;
    private ListView taskListView;
    private ImageButton addTask, deleteAll, goToDay, goToWeek;
    private CalendarView calendarview;
    private TextView title;
    private FirebaseListOptions<Task> options;
    FirebaseListAdapter adapter;
    String date;
    private int year;
    private int month;
    private int day;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
        setList();
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

    private void setList() {
        Query query = mDatabaseReference.child(date).child("tasks").orderByChild("start_time");
        options = new FirebaseListOptions.Builder<Task>()
                .setLayout(R.layout.adapter_view_layout_month)
                .setLifecycleOwner(MonthViewActivity.this)
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

        taskListView.setAdapter(adapter);

    }

    private void setListeners() {
        addTask.setOnClickListener(v-> onClickAddTask());
        deleteAll.setOnClickListener(v-> onClickDeleteAllTask());
        goToWeek.setOnClickListener(v-> onClickGoWeek());
        goToDay.setOnClickListener(v-> onClickGoDay());

        calendarview.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date = dayOfMonth + "-" + (month+1) + "-" + year;
            dateText.setText(date);
            setList();
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MonthViewActivity.this, ShowTask.class);
                intent.putExtra("task_id", task.getTask_id());
                startActivity(intent);
            }
        });
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskMonthActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }

    private void onClickGoWeek() {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }


    private void onClickDeleteAllTask() {

    }

    private void onClickGoDay() {
        Intent intent = new Intent(this, DayViewActivity.class);
        intent.putExtra("dateToDay", date);
        startActivity(intent);
    }

    private void initializeItems() {
        dateText = findViewById(R.id.text_month);
        taskListView = findViewById(R.id.taskListView);
        addTask = findViewById(R.id.addTaskImagButton);
        deleteAll = findViewById(R.id.deleteAllTaskImageButton);
        goToDay = findViewById(R.id.imageButton4);
        goToWeek = findViewById(R.id.imageButton5);
        calendarview = findViewById(R.id.calendarView);
        title = findViewById(R.id.toolbarTitle);
        title.setText("Month Planner");


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if ((getIntent().getStringExtra("dateToMonth")) != null) {
            date = getIntent().getStringExtra("dateToMonth");
        } else {
            date = day + "-" + (month + 1) + "-" + year;
        }
        dateText.setText(date);


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