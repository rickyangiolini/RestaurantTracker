package angio102.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantInfo extends Activity {

    TextView name, phone, phone2, website, website2, category, category2, slogan;
    RatingBar rating;
    Button toResList;


    @Override
    //binding the XML to Java for our Restaurant Info
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);

        name = findViewById(R.id.resName);
        phone = findViewById(R.id.resPhone);
        phone2 = findViewById(R.id.phone);
        website = findViewById(R.id.resURL);
        website2 = findViewById(R.id.url);
        category = findViewById(R.id.resCat);
        category2 = findViewById(R.id.cat);
        slogan = findViewById(R.id.resSlogan);
        rating = findViewById(R.id.resRating);
        rating.setFocusable(false);

        //getting our data from AddRestaurant using getIntent method
        Intent i = getIntent();

        name.setText(i.getStringExtra("rName"));
        phone.setText(i.getStringExtra("rNum"));
        website.setText(i.getStringExtra("rURL"));
        category.setText(i.getStringExtra("rCat"));
        slogan.setText(i.getStringExtra("rSlogan"));
        float rate = Float.valueOf(i.getStringExtra("rRating"));
        rating.setRating(rate);


        //setting selected TextView's to invisible depending on which CheckBox is checked by the user from the Parameters Activity

        if (i.getStringExtra("pStatus").equals("false")){
            phone.setVisibility(View.INVISIBLE);
            phone2.setVisibility(View.INVISIBLE);


        }

        if (i.getStringExtra("wStatus").equals("false")){
            website.setVisibility(View.INVISIBLE);
            website2.setVisibility(View.INVISIBLE);


        }

        if (i.getStringExtra("cStatus").equals("false")){
            category.setVisibility(View.INVISIBLE);
            category2.setVisibility(View.INVISIBLE);

        }


        toResList = findViewById(R.id.return_list);

        toResList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}