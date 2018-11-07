package angio102.restauranttracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RestaurantList extends AppCompatActivity {
    //declared global variables
    ListView resList;
    ArrayList<String> listOfRes, listOfResNums, listOfRatings, listOfCategories, listOfURL, listOfSlogans, listOfResInfo;
    ArrayAdapter<String> adapter;
    Button addRes;
    android.support.v7.widget.Toolbar bar;
    final int REQUEST_CODE_1 = 1;
    final int REQUEST_CODE_2 = 2;
    final int REQUEST_CODE_3 = 3;
    String showNum = "true", showURL = "true", showCat = "true";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        //linked XML to Java
        resList = findViewById(R.id.restaurant_list);


        listOfRes = new ArrayList<>();
        listOfResNums = new ArrayList<>();
        listOfRatings = new  ArrayList<>();
        listOfCategories =  new ArrayList<>();
        listOfURL = new ArrayList<>();
        listOfSlogans = new ArrayList<>();
        listOfResInfo = new ArrayList<>();
        bar=findViewById(R.id.tbar);
        bar.setTitle("");

        setSupportActionBar(bar);


        IntentFilter f = new IntentFilter("angio102.restauranttracker");
        BroadcastReceiver myBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(RestaurantList.this, "5 ★ Restaurant Added!", Toast.LENGTH_SHORT ).show();

            }

        };
        registerReceiver(myBroadcast, f);


        //passing over our variables to the restaurant info activity
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String resNameInfo = listOfRes.get(position);
                String resNumInfo = listOfResNums.get(position);
                String resURLInfo = listOfURL.get(position);
                String resCatInfo = listOfCategories.get(position);
                String resSlogInfo = listOfSlogans.get(position);
                String resRating = listOfRatings.get(position);


                //intent to go to from List to Info
                Intent info = new Intent(RestaurantList.this, RestaurantInfo.class);
                info.putExtra("rName", resNameInfo);
                info.putExtra("rNum", resNumInfo);
                info.putExtra("rURL", resURLInfo);
                info.putExtra("rCat",resCatInfo);
                info.putExtra("rSlogan",resSlogInfo);
                info.putExtra("rRating", resRating);
                String pos = Integer.toString(position);
                info.putExtra("item_position", pos);
                info.putExtra("pStatus", showNum);
                info.putExtra("wStatus", showURL);
                info.putExtra("cStatus", showCat);
                startActivityForResult(info, REQUEST_CODE_2);

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //created four different cases for the restaurant items (add, clear, load, and preferences)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add:
                Intent i = new Intent(RestaurantList.this, AddRestaurant.class);
                startActivityForResult(i, REQUEST_CODE_1);
                break;
            case R.id.Clear:
                listOfResInfo.clear();
                listOfRes.clear();
                listOfResNums.clear();
                listOfURL.clear();
                listOfCategories.clear();
                listOfSlogans.clear();
                listOfRatings.clear();
                adapter.notifyDataSetChanged();
                break;
            case R.id.Load:

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        InputStream inputStream = getResources().openRawResource(R.raw.restaurantinfo);
                        BufferedReader br= new BufferedReader(new InputStreamReader(inputStream));

                        try{
                            String line= br.readLine();
                            while (line!= null) {
                                String[] restaurants = line.split(",");
                                listOfRes.add(restaurants[0]);
                                listOfResNums.add(restaurants[1]);
                                listOfURL.add(restaurants[2]);
                                listOfCategories.add(restaurants[3]);
                                listOfSlogans.add(restaurants[4]);
                                listOfRatings.add(restaurants[5]);
                                String resInfo = restaurants[0] + "        "  + restaurants[4] + "        " + restaurants[5] + " ★ Rating";
                                listOfResInfo.add(resInfo);
                                line=br.readLine();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }


                    }
                }); t.start();
                adapter = new ArrayAdapter<String>(RestaurantList.this, android.R.layout.simple_list_item_1, listOfResInfo);
                resList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;

            case R.id.Preferences:
                Intent it = new Intent(RestaurantList.this,RestaurantParameters.class);
                it.putExtra("sNum",showNum);
                it.putExtra("sURL", showURL);
                it.putExtra("sCat", showCat);
                startActivityForResult(it, REQUEST_CODE_3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    //transfers the variables from AddClass to the List
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_1){
            if (resultCode == RESULT_OK){

                listOfRes.add(data.getStringExtra("resName"));
                listOfResNums.add(data.getStringExtra("resNum"));
                listOfURL.add(data.getStringExtra("resURL"));
                listOfCategories.add(data.getStringExtra("resCategory"));
                listOfSlogans.add(data.getStringExtra("resSlogan"));
                listOfRatings.add(data.getStringExtra("resRating"));
                String resInfo = data.getStringExtra("resName") + "        "  + data.getStringExtra("resSlogan") + "        " + data.getStringExtra("resRating") + " ★ Rating";
                listOfResInfo.add(resInfo);


                adapter = new ArrayAdapter<String>(RestaurantList.this, android.R.layout.simple_list_item_1, listOfResInfo);
                resList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                }
            }
            if (requestCode == REQUEST_CODE_3){
                if(resultCode == RESULT_OK){

                    showNum = data.getStringExtra("pStatus");
                    showURL = data.getStringExtra("wStatus");
                    showCat = data.getStringExtra("cStatus");


                }
            }

        }
}
