package com.lab.userinputform;
 // Apne project ke package name ke mutabiq ise change kar lein

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class UserForm extends AppCompatActivity {

    // UI Elements Declare Kiye
    private TextView tvId;
    private TextInputEditText etName, etPassword;
    private RadioGroup rgGender;
    private Spinner spinnerCountry, spinnerCity;
    private Button btnAdd;

    // State Variables
    private int nextUserId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form); // Tasalli kar lein ke aapki XML file ka naam yahi ho

        // 1. Views ko Initialize Kiya
        tvId = findViewById(R.id.tv_id);
        etName = findViewById(R.id.et_name);
        rgGender = findViewById(R.id.rg_gender);
        etPassword = findViewById(R.id.et_password);
        spinnerCountry = findViewById(R.id.spinner_country);
        spinnerCity = findViewById(R.id.spinner_city);
        btnAdd = findViewById(R.id.btn_add);

        // ID display ko initial value di
        updateIdDisplay();

        // 2. Setup Country Spinner
        setupCountrySpinner();

        // 3. Add User Button Click Listener
        btnAdd.setOnClickListener(v -> {
            // Button click hone par fresh input values read karein (Taake empty validation ka error na aaye)
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String country = spinnerCountry.getSelectedItem().toString();
            String city = spinnerCity.getSelectedItem() != null ? spinnerCity.getSelectedItem().toString() : "";

            // Selected Gender select karna
            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            RadioButton rbSelectedGender = findViewById(selectedGenderId);
            String gender = rbSelectedGender != null ? rbSelectedGender.getText().toString() : "";

            // Validation Check
            if (validateForm(name, password, country, city)) {
                // Agar validation pass ho jaye toh user processing yahan karein
                String successMessage = "User Added! ID: " + nextUserId + ", Name: " + name + ", Gender: " + gender;
                Toast.makeText(UserForm.this, successMessage, Toast.LENGTH_LONG).show();

                // ID increment karein aur form clear karein
                nextUserId++;
                updateIdDisplay();
                clearForm();
            }
        });
    }

    /**
     * Setup Main Country Spinner and Handle Dependent City Loading
     */
    private void setupCountrySpinner() {
        String[] countries = {"Select Country", "Pakistan", "United States", "United Kingdom"};

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);

        // Country change hone par City dynamically change karne ka Listener
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                updateCitySpinner(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Kuch karne ki zaroorat nahi hai
            }
        });
    }

    /**
     * Updates City Spinner based on Selected Country (Dependent Logic)
     */
    private void updateCitySpinner(String country) {
        String[] cities;

        // Condition ke mutabiq cities array population
        switch (country) {
            case "Pakistan":
                cities = new String[]{"Select City", "Lahore", "Karachi", "Islamabad", "Faisalabad"};
                break;
            case "United States":
                cities = new String[]{"Select City", "New York", "Los Angeles", "Chicago", "Houston"};
                break;
            case "United Kingdom":
                cities = new String[]{"Select City", "London", "Manchester", "Birmingham", "Leeds"};
                break;
            default:
                cities = new String[]{"Select City"}; // Agar "Select Country" choose ho
                break;
        }

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }

    /**
     * Form ki manual fields aur Spinners ko validation check karna
     */
    private boolean validateForm(String name, String password, String country, String city) {
        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }

        if (country.equals("Select Country")) {
            Toast.makeText(this, "Please select a Country", Toast.LENGTH_SHORT).show();
            spinnerCountry.requestFocus();
            return false;
        }

        if (city.equals("Select City") || city.isEmpty()) {
            Toast.makeText(this, "Please select a City", Toast.LENGTH_SHORT).show();
            spinnerCity.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Screen layout text counter update karne ke liye
     */
    private void updateIdDisplay() {
        tvId.setText("Next ID: " + nextUserId);
    }

    /**
     * User save hone ke baad UI fields ko reset karne ke liye
     */
    private void clearForm() {
        etName.setText("");
        etPassword.setText("");
        spinnerCountry.setSelection(0); // Reset to "Select Country"
        rgGender.check(R.id.rb_male);   // Default male selection back
        etName.clearFocus();
        etPassword.clearFocus();
    }
}