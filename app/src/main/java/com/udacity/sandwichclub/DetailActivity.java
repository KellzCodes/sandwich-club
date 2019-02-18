package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView originTv, alsoTv, descriptionTv, ingredientsTv;
    Sandwich sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);
        descriptionTv = findViewById(R.id.description_tv);
        originTv = findViewById(R.id.origin_tv);
        alsoTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        /* Each Detail Activity should be launched with an intent.
        * Get the intent. */
        Intent intent = getIntent();

        // If the intent is null then close the app
        if (intent == null) {
            closeOnError();
        }

        // Asserts that an object isn't null.
        assert intent != null;
        // Get the list view position from the intent
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        // Check to see if there is a key for the intent
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent close the app
            closeOnError();
            return;
        }

        // Get sandwich array from strings.xml
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        // create sandwich base from the array
        String json = sandwiches[position];

        // Try parsing sandwich objects from the string base
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) { // Catch JSON exception and print out log
            e.printStackTrace();
        }
        // If the sandwich is null, close the app
        if (sandwich == null) {
            closeOnError();
            return;
        }

        // set text views
        populateUI();

        // Use a placeholder icon before the data is loaded
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    // Call finish when your activity is done and should be closed.
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Use all the information from the sandwich objects to populate the user interface
    private void populateUI() {
        originTv.setText(sandwich.getPlaceOfOrigin().equals("") ? "No Info" : sandwich.getPlaceOfOrigin());
        appendListToTextView(alsoTv, sandwich.getAlsoKnownAs());
        appendListToTextView(ingredientsTv, sandwich.getIngredients());
        descriptionTv.setText(sandwich.getDescription().equals("") ? "No Info" : sandwich.getDescription());
    }

    /*
    * This method takes in a textview and a string list.
    * It will append a list of ingredients and nicknames
    * (also known as) to the textview.
    * */
    public void appendListToTextView(TextView textView, List<String> list) {
        int listSize = list.size();
        if(listSize == 0) {
            // If there is no information to display
            textView.append("No Info");
        }

        for(int i = 0; i < listSize; i++) {
            if (i == listSize - 1) {
                textView.append(list.get(i));
            }else {
                textView.append(list.get(i) + "\n");
            }
        }
    }
}
