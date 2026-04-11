package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private Spinner spinnerFilterCategory, spinnerSortBy;
    private EditText etMinPrice, etMaxPrice;
    private Button btnApplyFilters;
    private ListView listViewSearchResults;
    private HelperDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        db = new HelperDB(this);
        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        spinnerFilterCategory = findViewById(R.id.spinnerFilterCategory);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);
        etMinPrice = findViewById(R.id.etMinPrice);
        etMaxPrice = findViewById(R.id.etMaxPrice);
        btnApplyFilters = findViewById(R.id.btnApplyFilters);
        listViewSearchResults = findViewById(R.id.listViewSearchResults);

        ivMoreOptions.setOnClickListener(v -> showNavigationMenu(v));

        String[] categories = {"All", "Food & Dining", "Transportation", "Shopping", "Entertainment", "Bills & Utilities", "Other"};
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerFilterCategory.setAdapter(catAdapter);

        String[] sortOptions = {"Date (Newest First)", "Date (Oldest First)", "Amount (Highest)", "Amount (Lowest)"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortOptions);
        spinnerSortBy.setAdapter(sortAdapter);

        btnApplyFilters.setOnClickListener(v -> applyFiltersAndSort());
    }

    private void applyFiltersAndSort() {
        String cat = spinnerFilterCategory.getSelectedItem().toString();
        String sort = spinnerSortBy.getSelectedItem().toString();
        String min = etMinPrice.getText().toString();
        String max = etMaxPrice.getText().toString();

        ArrayList<Expense> results = db.getFilteredExpenses(min, max, cat, sort);

        ArrayAdapter<Expense> resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        listViewSearchResults.setAdapter(resultAdapter);

        if (results.isEmpty()) {
            Toast.makeText(this, "No expenses found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Home"); popup.getMenu().add("Display");
        popup.getMenu().add("Input"); popup.getMenu().add("Credits");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Home")) startActivity(new Intent(this, MainActivity.class));
            else if (title.equals("Display")) startActivity(new Intent(this, Display.class));
            else if (title.equals("Input")) startActivity(new Intent(this, Input.class));
            else if (title.equals("Credits")) startActivity(new Intent(this, Credits.class));
            return true;
        });
        popup.show();
    }
}