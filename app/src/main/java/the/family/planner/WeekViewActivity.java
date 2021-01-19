package the.family.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WeekViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView title;
    private TabItem mondayTab, tuesdayTab, wedsdayTab, thursdayTab, fridayTab, saturdayTab, sundayTab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();

    }

    private void setListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initializeItems() {
         tabLayout = findViewById(R.id.TabLayout);
         mondayTab = findViewById(R.id.MondayTab);
         tuesdayTab = findViewById(R.id.TuesdayTab);
         wedsdayTab = findViewById(R.id.WedsdayTab);
         thursdayTab = findViewById(R.id.ThursdayTab);
         fridayTab = findViewById(R.id.FrydayTab);
         saturdayTab = findViewById(R.id.SaturdayTab);
         sundayTab = findViewById(R.id.SundayTab);
         viewPager = findViewById(R.id.viewPager);

         pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                 tabLayout.getTabCount());

         viewPager.setAdapter(pagerAdapter);

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