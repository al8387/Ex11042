package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private HelperDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new HelperDB(this);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        FloatingActionButton fabAddExpense = findViewById(R.id.fabAddExpense);

        ivMoreOptions.setOnClickListener(v -> showNavigationMenu(v));
        fabAddExpense.setOnClickListener(v -> startActivity(new Intent(this, Input.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        double total = db.getTotalAmount();
        tvTotalAmount.setText("$" + String.format("%.2f", total));
    }

    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Display"); popup.getMenu().add("Search"); popup.getMenu().add("Credits");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Display")) startActivity(new Intent(this, Display.class));
            else if (title.equals("Search")) startActivity(new Intent(this, Search.class));
            else if (title.equals("Credits")) startActivity(new Intent(this, Credits.class));
            return true;
        });
        popup.show();
    }
}