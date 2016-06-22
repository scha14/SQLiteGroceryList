package grocerylist.sqlite.app.jsmtech.sqlitegrocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import model.Grocery;
import sqlite.DBAdapter;

/**
 * Created by Sukriti on 6/17/16.
 */
public class AddNewGroceryItem extends AppCompatActivity {
    private Button submitButton;
    private EditText itemName;
    private CheckBox itemChecked;
    private DBAdapter dbAdapter;

    public AddNewGroceryItem() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_grocery_item);

        dbAdapter = new DBAdapter(AddNewGroceryItem.this);


        submitButton = (Button) findViewById(R.id.submit);
        itemName = (EditText) findViewById(R.id.name);
        itemChecked = (CheckBox) findViewById(R.id.checked);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gName = itemName.getText().toString().trim();
                if (gName.isEmpty())
                    Toast.makeText(AddNewGroceryItem.this, "Empty Fields ", Toast.LENGTH_SHORT).show();
                else {


                    submitButton.setEnabled(false);

                    Grocery newGrocery = new Grocery();
                    newGrocery.setItem(gName);
                    newGrocery.setChecked(0);

                    dbAdapter.open();
                    long a = dbAdapter.createGorceryItem(newGrocery);
                    newGrocery.setId(a);
                    dbAdapter.close();

                    submitButton.setEnabled(true);
                    itemName.getText().clear();

                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddNewGroceryItem.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
