package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.Date;

import the.family.planner.models.Task;

public class MonthViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView dateText;
    private ListView taskListView;
    private ImageButton addTask, deleteTask, deleteAll, goToDay, goToWeek;
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
        Query query = mDatabaseReference.orderByChild("date").equalTo(date);
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
        deleteTask.setOnClickListener(v-> onClickDeleteTask());
        goToWeek.setOnClickListener(v-> onClickGoWeek());
        goToDay.setOnClickListener(v-> onClickGoDay());

        calendarview.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date = dayOfMonth + "/" + (month+1) + "/" + year;
            dateText.setText(date);
            setList();
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonthViewActivity.this,ShowTask.class);
                Task task = (Task) parent.getAdapter().getItem(position);
                intent.putExtra("task_id", task.getTask_id());
                startActivity(intent);
            }
        });
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
    }

    private void onClickGoWeek() {
        Intent intent = new Intent(this, WeekViewActivity.class);
        startActivity(intent);
    }

    private void onClickDeleteTask() {

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
        deleteTask = findViewById(R.id.DeleteTaskImageButton);
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
        date= day+"/"+(month+1)+"/"+year;
        dateText.setText(date);


    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("tasks");
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}