package io.github.pavanrkadave.rupeediary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {

    private ListView expenseList;

    List<ExpenseObject> expensesList;

    //Firebase Database Reference.
    private DatabaseReference expenseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        expenseReference = FirebaseDatabase.getInstance().getReference("expenses");

        expenseList = findViewById(R.id.expense_list);

        expensesList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        expenseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                expensesList.clear();
                for (DataSnapshot expenseSnapshot : dataSnapshot.getChildren()){
                    ExpenseObject expenseObj = expenseSnapshot.getValue(ExpenseObject.class);
                    expensesList.add(expenseObj);
                }

                ExpenseAdapter adapter = new ExpenseAdapter(ExpenseActivity.this,expensesList);
                expenseList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
