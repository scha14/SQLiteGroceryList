package grocerylist.sqlite.app.jsmtech.sqlitegrocerylist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.GroceryAdapter;
import model.Grocery;
import sqlite.DBAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recList;
    private ArrayList<Grocery> mGroceryList = new ArrayList<>();
    private GroceryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBAdapter db = new DBAdapter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddNewGroceryItem.class);
                startActivity(intent);
                finish();

            }
        });

        recList = (RecyclerView) findViewById(R.id.list_of_groceries_rv);

        final LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        mAdapter = new GroceryAdapter(MainActivity.this, mGroceryList);
        recList.setAdapter(mAdapter);

        db.open();

        Cursor c = db.getAllGorceries();

        if(c.moveToFirst()) {
            int isChecked = 0;
            do {
                Grocery grocery = new Grocery();
                grocery.setId(c.getLong(0)); // first column first row
                grocery.setItem(c.getString(1));
                if (c.getInt(2) == 1) {
                    isChecked = 1;
                } else {
                    isChecked = 0;
                }
                grocery.setChecked(isChecked);
                grocery.setIsVisited(false); // Recycler Vieew to remember the status of the grocery!
                mGroceryList.add(grocery);
            } while (c.moveToNext());
        }
        db.close();

        Toast.makeText(MainActivity.this, "Items Added", Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
