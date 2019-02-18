package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make an array of sandwiches using the arrays in strings.xml
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);

        // Use an Array adapter provided by Android
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        // Launch detail activity when user taps a list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
    }

    /* Detail activity takes an integer called position. Position is the specific list view
    * item clicked by the user. */
    private void launchDetailActivity(int position) {
        /* Create a door to detail activity */
        Intent intent = new Intent(this, DetailActivity.class);
        // Create a special key to the door at this specific list view item or position
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        // Start detail activity
        startActivity(intent);
    }
}
