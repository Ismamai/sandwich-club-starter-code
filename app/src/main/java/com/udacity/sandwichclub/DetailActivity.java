package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
            mBinding.alsoKnownLabel.setVisibility(View.GONE);
            mBinding.alsoKnownTv.setVisibility(View.GONE);
        } else {
            mBinding.alsoKnownLabel.setVisibility(View.VISIBLE);
            mBinding.alsoKnownTv.setVisibility(View.VISIBLE);
            mBinding.alsoKnownTv.setText(alsoKnowAsText);
        }


        //PlaceOfOrigin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        Log.d(TAG, "Place of origin : " + placeOfOrigin);

        if (TextUtils.isEmpty(placeOfOrigin)) {
            mBinding.originLabel.setVisibility(View.GONE);
            mBinding.originTv.setVisibility(View.GONE);
        } else {
            mBinding.originLabel.setVisibility(View.VISIBLE);
            mBinding.originTv.setVisibility(View.VISIBLE);
            mBinding.originTv.setText(placeOfOrigin);
        }

        //Ingredients
        List<String> ingredients = sandwich.getIngredients();
        String ingredientsText = TextUtils.join("\n", ingredients);
        Log.d(TAG, "Ingredients : " + ingredientsText);
        if (TextUtils.isEmpty(ingredientsText)) {
            mBinding.ingredientsLabel.setVisibility(View.GONE);
            mBinding.ingredientsTv.setVisibility(View.GONE);
        } else {
            mBinding.ingredientsLabel.setVisibility(View.VISIBLE);
            mBinding.ingredientsTv.setVisibility(View.VISIBLE);
            mBinding.ingredientsTv.setText(ingredientsText);
        }

        //Description
        String description = sandwich.getDescription();
        Log.d(TAG, "Description : " + description);

        if (TextUtils.isEmpty(description)) {
            mBinding.descriptionLabel.setVisibility(View.GONE);
            mBinding.descriptionTv.setVisibility(View.GONE);
        } else {
            mBinding.descriptionLabel.setVisibility(View.VISIBLE);
            mBinding.descriptionTv.setVisibility(View.VISIBLE);
            mBinding.descriptionTv.setText(description);
        }

    }
}
