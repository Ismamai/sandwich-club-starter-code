package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = "ISMA";

    ActivityDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Log.d(TAG, "Loading image " + sandwich.getImage());
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // AlsoKnownAs
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String alsoKnowAsText = TextUtils.join("\n", alsoKnownAs);
        Log.d(TAG, "Also known as : " + alsoKnowAsText);

        if (TextUtils.isEmpty(alsoKnowAsText)) {
            alsoKnowAsText = "..";
        }
        mBinding.alsoKnownTv.setText(alsoKnowAsText);


        //PlaceOfOrigin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        Log.d(TAG, "Place of origin : " + placeOfOrigin);

        if (TextUtils.isEmpty(placeOfOrigin)) {
            placeOfOrigin = "..";
        }
        mBinding.originTv.setText(placeOfOrigin);


        //Description
        String description = sandwich.getDescription();
        Log.d(TAG, "Description : " + description);

        if (TextUtils.isEmpty(placeOfOrigin)) {
            description = "..";
        }
        mBinding.descriptionTv.setText(description);

        //Ingredients
        List<String> ingredients = sandwich.getIngredients();
        String ingredientsText = TextUtils.join("\n", ingredients);
        Log.d(TAG, "Ingredients : " + ingredientsText);
        if (TextUtils.isEmpty(ingredientsText)) {
            ingredientsText = "..";
        }
        mBinding.ingredientsTv.setText(ingredientsText);
    }
}
