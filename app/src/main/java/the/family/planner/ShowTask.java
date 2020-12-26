package the.family.planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import the.family.planner.models.Task;

public class ShowTask extends AppCompatActivity {

    private String data;
    ArrayList<Task> tasks;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private TextView taskTitle, start, end, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);



        data = getIntent().getStringExtra("task_id");
        addDatabase();
        addToolbar();
        initializeItems();

    }

    private void initializeItems() {
        taskTitle = findViewById(R.id.taskTitleTv);
        start = findViewById(R.id.taskStartTimeTV);
        end = findViewById(R.id.taskEndTimeTV);
        description = findViewById(R.id.taskDescriptionTV);
        taskTitle.setText(String.format("%slol", data));

    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child(getString(R.string.dbnode_tasks));

        /*Query query = mDatabaseReference.orderByKey().equalTo(data);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    Task t = singleSnapshot.getValue(Task.class);
                    taskTitle.setText(t.getTitle());
                    description.setText(t.getDescription());
                    start.setText(t.getStart_time());
                    end.setText(t.getEnd_time());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId()){
            case R.id.editTask:
                Intent intent = new Intent(this, EditTask.class);
                this.startActivity(intent);
                return true;
            case R.id.deleteTask:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}