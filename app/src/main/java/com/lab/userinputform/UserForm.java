import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ... inside UserForm class ...

private Spinner spinnerCountry, spinnerCity;
private Map<String, List<String>> countryCityMap;

private void setupCascadingSpinners() {
    spinnerCountry = findViewById(R.id.spinner_country);
    spinnerCity = findViewById(R.id.spinner_city);

    // 1. Prepare Data
    countryCityMap = new HashMap<>();

    List<String> pakistanCities = new ArrayList<>();
    pakistanCities.add("Karachi");
    pakistanCities.add("Lahore");
    pakistanCities.add("Islamabad");

    List<String> usaCities = new ArrayList<>();
    usaCities.add("New York");
    usaCities.add("Los Angeles");
    usaCities.add("Chicago");

    countryCityMap.put("Pakistan", pakistanCities);
    countryCityMap.put("USA", usaCities);

    // 2. Set up Country Spinner
    List<String> countries = new ArrayList<>(countryCityMap.keySet());
    ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, countries);
    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerCountry.setAdapter(countryAdapter);

    // 3. Country Selection Listener
    spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedCountry = parent.getItemAtPosition(position).toString();
            updateCitySpinner(selectedCountry);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    });
}

private void updateCitySpinner(String country) {
    List<String> cities = countryCityMap.get(country);
    ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_item, cities);
    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerCity.setAdapter(cityAdapter);
}