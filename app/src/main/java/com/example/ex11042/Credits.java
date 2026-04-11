package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for displaying credits information.
 * This screen shows attribution and creator details for the application.
 *
 * @Adam
 * @version 1.0
 */
public class Credits extends AppCompatActivity {

    /**
     * Initializes the Activity and sets up the user interface.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        ivMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNavigationMenu(v);
            }
        });
    }

    /**
     * Displays a popup menu for navigation to other parts of the application.
     *
     * @param view The view that triggered the menu (typically the overflow icon).
     */
    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Home");
        popup.getMenu().add("Display");
        popup.getMenu().add("Search");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String title = item.getTitle().toString();
                if (title.equals("Home")) {
                    startActivity(new Intent(Credits.this, MainActivity.class));
                    return true;
                } else if (title.equals("Display")) {
                    startActivity(new Intent(Credits.this, Display.class));
                    return true;
                } else if (title.equals("Search")) {
                    startActivity(new Intent(Credits.this, Input.class));
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }
}