package com.example.ex11042;

public class Expense {
    private int id;
    private String description;
    private double amount;
    private String category;
    private String date;
    private boolean isRecurring;

    public Expense(int id, String description, double amount, String category, String date, boolean isRecurring) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.isRecurring = isRecurring;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public boolean isRecurring() { return isRecurring; }

    @Override
    public String toString() {
        return date + " | " + description + " - $" + String.format("%.2f", amount) + "\n(" + category + ")";
    }
}