package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView mImageIv;
    TextView mAlsoKnownLabelTv;
    TextView mAlsoKnownTv;
    TextView mOriginLabelTv;
    TextView mOriginTv;
    TextView mDescriptionLabelTv;
    TextView mDescriptionTv;
    TextView mIngredientsLabelTv;
    TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind all objects and views
        bindViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // Assign values from Sandwich object to proper views
    private void populateUI(Sandwich sandwich) {
        String formattedAlsoKnown = convertListToText(sandwich.getAlsoKnownAs());
        String formattedIngredients = convertListToText(sandwich.getIngredients());
        mAlsoKnownTv.setText(formattedAlsoKnown);
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTv.setText(sandwich.getDescription());
        mIngredientsTv.setText(formattedIngredients);
        setVisibility(sandwich);
    }

    private void bindViews() {
        mImageIv = findViewById(R.id.image_iv);
        mAlsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mOriginLabelTv = findViewById(R.id.origin_label_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mDescriptionLabelTv = findViewById(R.id.description_label_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
    }

    // Hide labels with no data
    private void setVisibility(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownLabelTv.setVisibility(View.GONE);
            mAlsoKnownTv.setVisibility(View.GONE);
        }
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginLabelTv.setVisibility(View.GONE);
            mOriginTv.setVisibility(View.GONE);
        }
        if (sandwich.getDescription().isEmpty()) {
            mDescriptionLabelTv.setVisibility(View.GONE);
            mDescriptionTv.setVisibility(View.GONE);
        }
        if(sandwich.getIngredients().isEmpty()) {
            mIngredientsLabelTv.setVisibility(View.GONE);
            mIngredientsTv.setVisibility(View.GONE);
        }
    }

    // Removes brackets from string received from List.toString()
    private String convertListToText(List<String> list) {
        return list.toString()
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
    }
}
