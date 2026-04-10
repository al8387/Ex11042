package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {
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
                    startActivity(new Intent(Credits.this, Search.class));
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }
}