package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MonthViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView dateText;
    private ListView taskListView;
    private ImageButton addTask, deleteTask, deleteAll, goToDay, goToWeek;
    private CalendarView calendarview;
    private TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();

    }

    private void setListeners() {
        addTask.setOnClickListener(v-> onClickAddTask());
        deleteAll.setOnClickListener(v-> onClickDeleteAllTask());
        deleteTask.setOnClickListener(v-> onClickDeleteTask());
        goToWeek.setOnClickListener(v-> onClickGoWeek());
        goToDay.setOnClickListener(v-> onClickGoDay());
        calendarview.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month+1) + "/" + year;
            dateText.setText(date);
        });
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskActivity.class);
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
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference("tasks");
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}