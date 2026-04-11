package com.example.ex11042;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Display extends AppCompatActivity {

    private ListView listViewExpenses;
    private ArrayAdapter<Expense> adapter;
    private ArrayList<Expense> expensesList;
    private HelperDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        db = new HelperDB(this);
        listViewExpenses = findViewById(R.id.listViewExpenses);
        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        ivMoreOptions.setOnClickListener(v -> showNavigationMenu(v));

        loadData();

        listViewExpenses.setOnItemLongClickListener((parent, view, position, id) -> {
            showEditDeleteDialog(position);
            return true;
        });
    }

    private void loadData() {
        expensesList = db.getAllExpenses();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expensesList);
        listViewExpenses.setAdapter(adapter);
    }

    private void showEditDeleteDialog(int position) {
        String[] options = {"Update Expense", "Delete Expense"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(options, (dialog, which) -> {
            Expense expense = expensesList.get(position);

            if (which == 0) {
                Intent intent = new Intent(this, Input.class);
                intent.putExtra("EXPENSE_ID", expense.getId());
                startActivity(intent);
            } else if (which == 1) {
                db.deleteExpense(expense.getId());
                loadData();
                Toast.makeText(this, "Deleted: " + expense.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Home"); popup.getMenu().add("Input");
        popup.getMenu().add("Search"); popup.getMenu().add("Credits");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Home")) startActivity(new Intent(this, MainActivity.class));
            else if (title.equals("Input")) startActivity(new Intent(this, Input.class));
            else if (title.equals("Search")) startActivity(new Intent(this, Search.class));
            else if (title.equals("Credits")) startActivity(new Intent(this, Credits.class));
            return true;
        });
        popup.show();
    }
}