package angio102.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class AddRestaurant extends Activity {

    EditText resName, resNum, resURL, resSlogan;
    RatingBar resRating;
    Button addRes, clearRes, back;
    Spinner resCategory;

    @Override
    //binding XML to Java for our AddRestaurant activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant);

        resName = findViewById(R.id.restaurant_name);
        resNum = findViewById(R.id.restaurant_phone);
        resURL = findViewById(R.id.restaurant_website);
        resRating = findViewById(R.id.restaurant_rating);
        resCategory = findViewById(R.id.restaurant_category);
        resSlogan = findViewById(R.id.restaurant_slogan);
        addRes = findViewById(R.id.restaurant_add2);
        clearRes = findViewById(R.id.restaurant_clear);
        back = findViewById(R.id.restaurant_back);

        //sending all the restaurant information from AddRestaurant to Restaurant List and therefore also RestaurantInfo
        addRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String rName = resName.getText().toString();
                String rNum = resNum.getText().toString();
                String rURL = resURL.getText().toString();
                String rCat = resCategory.getSelectedItem().toString();
                String rSlog = resSlogan.getText().toString();
                Float rating = resRating.getRating();
                String theRating = Float.toString(rating);
                i.putExtra("resName", rName);
                i.putExtra("resNum", rNum);
                i.putExtra("resURL", rURL);
                i.putExtra("resCategory", rCat);
                i.putExtra("resSlogan", rSlog);
                i.putExtra("resRating", theRating);
                setResult(RESULT_OK,i);

                //displaying a toast using a broadcast intent only if rating was 5
                Intent intent = new Intent();
                intent.setAction("angio102.restauranttracker");
                if (resRating.getRating() == 5.0) {
                    sendBroadcast(intent);
                }
                finish();
            }
        });
        //going back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //clearing parameters
        clearRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resName.setText("");
                resNum.setText("");
                resURL.setText("");
                resSlogan.setText("");
                resCategory.setSelection(0);
                resRating.setRating(0);

            }
        });

    }
}
