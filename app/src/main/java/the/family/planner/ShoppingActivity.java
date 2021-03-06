package the.family.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import the.family.planner.Shopping.AddListActivity;
import the.family.planner.family.JoinFamilyActivity;
import the.family.planner.models.Family;
import the.family.planner.models.Shoppinglist;
import the.family.planner.models.Task;
import the.family.planner.models.User;

public class ShoppingActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userreference;
    private FirebaseUser user;
    private ListView shoppingList;
    private List<String> arraylist;
    private TextView title;
    private Button addList;
    private String familyId;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        addToolbar();
        addDatabase();
        initializeItems();
        setListeners();
       // setWidgets();


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

        mDatabaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Shoppinglist shoppinglist = snapshot.getValue(Shoppinglist.class);
                arraylist = new ArrayList<>();
                arraylist.add(shoppinglist.getShop());
                ArrayAdapter arrayAdapter = new ArrayAdapter(ShoppingActivity.this, android.R.layout.simple_list_item_1, arraylist );
                shoppingList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void setListeners() {
    addList.setOnClickListener(v->onAddListClicked());
    }

    private void onAddListClicked() {
        Intent intent = new Intent(this, AddListActivity.class);
        startActivity(intent);
    }

    private void initializeItems() {
        shoppingList = findViewById(R.id.ShoppingList);
        title = findViewById(R.id.toolbarTitle);
        addList = findViewById(R.id.addShoppingList);

        title.setText("ShoppingLists");
    }

    private void addDatabase() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("shoppinglists");
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