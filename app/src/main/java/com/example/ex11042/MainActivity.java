package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/**
 * The main dashboard Activity of the Expense Tracker application.
 * Displays the total balance and recent transaction history.
 *
 * @Adam
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private ListView listViewRecent;
    private HelperDB db;

    /**
     * Initializes the dashboard, sets up the database connection, and registers UI event listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new HelperDB(this);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        listViewRecent = findViewById(R.id.listViewRecent);
        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        FloatingActionButton fabAddExpense = findViewById(R.id.fabAddExpense);

        ivMoreOptions.setOnClickListener(v -> showNavigationMenu(v));
        fabAddExpense.setOnClickListener(v -> startActivity(new Intent(this, Input.class)));
    }

    /**
     * Refreshes the total amount and recent expenses list every time the activity returns to the foreground.
     */
    @Override
    protected void onResume() {
        super.onResume();
        double total = db.getTotalAmount();
        tvTotalAmount.setText("$" + String.format("%.2f", total));

        ArrayList<Expense> recentList = db.getRecentExpensesLastWeek();
        ArrayAdapter<Expense> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentList);
        if (listViewRecent != null) {
            listViewRecent.setAdapter(adapter);
        }
    }

    /**
     * Displays a popup menu for navigating to different screens of the application.
     *
     * @param view The view that triggered the menu (overflow icon).
     */
    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Input");
        popup.getMenu().add("Display");
        popup.getMenu().add("Search");
        popup.getMenu().add("Credits");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Input")) startActivity(new Intent(this, Input.class));
            else if (title.equals("Display")) startActivity(new Intent(this, Display.class));
            else if (title.equals("Search")) startActivity(new Intent(this, Search.class));
            else if (title.equals("Credits")) startActivity(new Intent(this, Credits.class));
            return true;
        });
        popup.show();
    }
}