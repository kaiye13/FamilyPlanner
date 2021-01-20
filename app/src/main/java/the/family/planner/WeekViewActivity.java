package the.family.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import the.family.planner.tabs.MainFragment;
import the.family.planner.tabs.PagerAdapter;

public class WeekViewActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView title;
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
         viewPager = findViewById(R.id.viewPager);
         title = findViewById(R.id.toolbarTitle);
         title.setText(getResources().getString(R.string.WeekView));

         ArrayList<String> arrayList = new ArrayList<>();

         arrayList.add(getResources().getString(R.string.Mon));
        arrayList.add(getResources().getString(R.string.Thu));
        arrayList.add(getResources().getString(R.string.Wed));
        arrayList.add(getResources().getString(R.string.Thu));
        arrayList.add(getResources().getString(R.string.Fry));
        arrayList.add(getResources().getString(R.string.Sat));
        arrayList.add(getResources().getString(R.string.Sun));


        prepareViewPager(viewPager, arrayList);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        MainFragment fragment = new MainFragment();
        for (int i = 0; i<arrayList.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("title",arrayList.get(i));
            fragment.setArguments(bundle);
            pagerAdapter.addFragment(fragment,arrayList.get(i));
            fragment=new MainFragment();
        }
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