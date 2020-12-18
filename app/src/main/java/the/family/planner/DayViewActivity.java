package the.family.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DayViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText dayEditText;
    private Button addTaskButton;
    private ListView taskListView;
    private TextView title;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();

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
            String date = dayOfMonth + "/" + month + "/" + year;
            dayEditText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void onClickAddTask() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
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