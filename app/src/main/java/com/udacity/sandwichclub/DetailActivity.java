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
        originTv = findViewById(R.id.origin_tv);
        alsoTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                // Use this icon before the data is populated. If no image, use icon
                .placeholder(R.mipmap.ic_launcher)
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        descriptionTv.setText(sandwich.getDescription().equals("") ? "No Info" : sandwich.getDescription());
        originTv.setText(sandwich.getPlaceOfOrigin().equals("") ? "No Info" : sandwich.getDescription());
        appendListToTextView(alsoTv, sandwich.getAlsoKnownAs());
        appendListToTextView(ingredientsTv, sandwich.getIngredients());
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
