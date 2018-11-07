package angio102.restauranttracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class RestaurantParameters extends Activity {
    CheckBox viewPhone, viewWeb, viewCat;
    Button savePreferences;
    Boolean showPhone, showWeb, showCat;
    String p1, p2, p3;


    @Override
    //binding the xml to the java and using three booleans to separate Checkbox's
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_parameters);

        viewPhone = findViewById(R.id.viewPhoneBox);
        viewWeb = findViewById(R.id.viewWebBox);
        viewCat = findViewById(R.id.viewCatBox);
        savePreferences = findViewById(R.id.savePref);

        Intent in = getIntent();
        p1 = in.getStringExtra("sNum");
        System.out.println(p1);
        p2 = in.getStringExtra("sURL");
        System.out.println(p2);
        p3 = in.getStringExtra("sCat");
        System.out.println(p3);

        if (p1.equals("true")){
            viewPhone.setChecked(true);
        }

        if (p2.equals("true")){
            viewWeb.setChecked(true);
        }

        if (p3.equals("true")){
            viewCat.setChecked(true);
        }

        //save preferences button checking which checkbox is checked
        savePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhone = viewPhone.isChecked();
                showWeb = viewWeb.isChecked();
                showCat = viewCat.isChecked();



                String x1 = showPhone.toString();

                String x2 = showWeb.toString();

                String x3 = showCat.toString();


                Intent intent = new Intent();
                intent.putExtra("pStatus", x1);
                intent.putExtra("wStatus", x2);
                intent.putExtra("cStatus", x3);
                setResult(RESULT_OK,intent);
                finish();



            }
        });


    }




    }
