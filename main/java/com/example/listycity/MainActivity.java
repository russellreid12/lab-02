package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dataList;
    ArrayAdapter<String> cityAdapter;
    ListView listView;
    String[] cities = {"Edmonton", "Calgary"};
    String selectedCity = null; // select city to remove by clicking on it which sets the value for it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this creates the buttons and list UI from the xml file so they correlate to
        //the logic being created for them
        listView = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.addButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        //the container of type ArrayList holding a string array of cities
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        // this initializes the view for the cities so the data can be edited through the listyView
        // UI
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(cityAdapter);


        // uses a listener method to get the location of a click on the screen so the correct
        // item can be selected.
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);
            //Toast used to make little pop up messages instead of Intent creating a new screen
            Toast.makeText(this, "Selected: " + selectedCity, Toast.LENGTH_SHORT).show();
        });

        // defines the logic for adding a city using toast for a pop up input aas well as

        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");

            // for inputting new city into dataList
            final EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String newCity = input.getText().toString().trim();
                if (!newCity.isEmpty()) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

            builder.show();
        });

        // creates the logic for the delete functionality using the selected city from
        // the listView listener method to delete said city from the list.
        deleteButton.setOnClickListener(v -> {
            if (selectedCity != null) {
                dataList.remove(selectedCity);
                cityAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Deleted: " + selectedCity, Toast.LENGTH_SHORT).show();
                selectedCity = null; // reset selection
            } else {
                Toast.makeText(this, "No city selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}