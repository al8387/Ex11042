package com.example.ex11042;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Input extends AppCompatActivity {

    private EditText etExpenseDesc, etExpenseAmount, etExpenseDate;
    private Spinner spinnerInputCategory;
    private CheckBox cbRecurring;
    private Button btnSaveExpense;
    private HelperDB db;
    private int updateId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        db = new HelperDB(this);

        ImageView ivMoreOptions = findViewById(R.id.ivMoreOptions);
        etExpenseDesc = findViewById(R.id.etExpenseDesc);
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etExpenseDate = findViewById(R.id.etExpenseDate);
        spinnerInputCategory = findViewById(R.id.spinnerInputCategory);
        cbRecurring = findViewById(R.id.cbRecurring);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        ivMoreOptions.setOnClickListener(v -> showNavigationMenu(v));

        String[] categories = {"Food & Dining", "Transportation", "Shopping", "Entertainment", "Bills & Utilities", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerInputCategory.setAdapter(adapter);

        updateId = getIntent().getIntExtra("EXPENSE_ID", -1);

        if (updateId != -1) {
            Expense existingExpense = db.getExpenseById(updateId);
            if (existingExpense != null) {
                etExpenseDesc.setText(existingExpense.getDescription());
                etExpenseAmount.setText(String.valueOf(existingExpense.getAmount()));
                etExpenseDate.setText(existingExpense.getDate());
                cbRecurring.setChecked(existingExpense.isRecurring());
                btnSaveExpense.setText("Update Expense");

                for (int i = 0; i < categories.length; i++) {
                    if (categories[i].equals(existingExpense.getCategory())) {
                        spinnerInputCategory.setSelection(i);
                        break;
                    }
                }
            }
        } else {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            etExpenseDate.setText(today);
        }

        btnSaveExpense.setOnClickListener(v -> {
            String desc = etExpenseDesc.getText().toString().trim();
            String amountStr = etExpenseAmount.getText().toString().trim();
            String date = etExpenseDate.getText().toString().trim();
            String category = spinnerInputCategory.getSelectedItem().toString();
            int recurring = cbRecurring.isChecked() ? 1 : 0;

            if (desc.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(Input.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);

            if (updateId != -1) {
                db.updateExpense(updateId, desc, amount, category, date, recurring);
                Toast.makeText(Input.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                db.addExpense(desc, amount, category, date, recurring);
                Toast.makeText(Input.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(Input.this, Display.class));
            finish();
        });
    }

    private void showNavigationMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenu().add("Home"); popup.getMenu().add("Display");
        popup.getMenu().add("Search"); popup.getMenu().add("Credits");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Home")) startActivity(new Intent(this, MainActivity.class));
            else if (title.equals("Display")) startActivity(new Intent(this, Display.class));
            else if (title.equals("Search")) startActivity(new Intent(this, Search.class));
            else if (title.equals("Credits")) startActivity(new Intent(this, Credits.class));
            return true;
        });
        popup.show();
    }
}