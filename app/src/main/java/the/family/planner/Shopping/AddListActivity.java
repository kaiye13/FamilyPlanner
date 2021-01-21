package the.family.planner.Shopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import the.family.planner.DayViewActivity;
import the.family.planner.R;
import the.family.planner.ShoppingActivity;
import the.family.planner.models.Shoppinglist;
import the.family.planner.models.User;
import the.family.planner.task.AddTaskDayActivity;

public class AddListActivity extends AppCompatActivity {
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userreference;
    private FirebaseUser user;
    private Button addList;
    private EditText shopNameET, productsET;
    private String familyId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        addToolbar();
        addDatabase();
        initializeItems();
        setWidgets();
        setListeners();

    }
    private void setWidgets() {
        userreference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    if (userProfile.getFamily_id() != null) {
                        familyId = userProfile.getFamily_id();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListeners() {
    addList.setOnClickListener(v->onAddListClick());
    }

    private void onAddListClick() {
        Shoppinglist shoppinglist = new Shoppinglist();
        shoppinglist.setFamily_id(familyId);
        shoppinglist.setShop(shopNameET.getText().toString());
        shoppinglist.setProducts(productsET.getText().toString());
        String id = mDatabaseReference.push().getKey();
        shoppinglist.setShopping_id(id);
        mDatabaseReference.child(id).setValue(shoppinglist);

        Intent intent = new Intent(AddListActivity.this, ShoppingActivity.class);
        startActivity(intent);
    }

    private void initializeItems() {
        shopNameET = findViewById(R.id.ShopNameET);
        productsET = findViewById(R.id.ProductsET);
        addList = findViewById(R.id.AddShopplinglistBTN);
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference("shoppinglists");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userreference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}