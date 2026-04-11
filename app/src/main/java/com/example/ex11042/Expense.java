package com.example.ex11042;

/**
 * Represents a single expense entry in the tracker application.
 * This class holds data about an expense including description, amount, category, date, and recurrence status.
 *
 * @Adam
 * @version 1.0
 */
public class Expense {
    private int id;
    private String description;
    private double amount;
    private String category;
    private String date;
    private boolean isRecurring;

    /**
     * Constructs a new Expense object.
     *
     * @param id The unique identifier for the expense in the database.
     * @param description A short description of what the expense was for.
     * @param amount The monetary value of the expense.
     * @param category The category classification (e.g., Food, Transportation).
     * @param date The date the expense occurred (typically in YYYY-MM-DD format).
     * @param isRecurring Whether the expense repeats regularly.
     */
    public Expense(int id, String description, double amount, String category, String date, boolean isRecurring) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.isRecurring = isRecurring;
    }

    /**
     * @return the unique ID of the expense.
     */
    public int getId() { return id; }

    /**
     * @return the description of the expense.
     */
    public String getDescription() { return description; }

    /**
     * @return the amount of the expense.
     */
    public double getAmount() { return amount; }

    /**
     * @return the category of the expense.
     */
    public String getCategory() { return category; }

    /**
     * @return the date of the expense.
     */
    public String getDate() { return date; }

    /**
     * @return true if the expense is recurring, false otherwise.
     */
    public boolean isRecurring() { return isRecurring; }

    /**
     * Returns a string representation of the expense for display purposes.
     *
     * @return A formatted string containing date, description, amount, and category.
     */
    @Override
    public String toString() {
        return date + " | " + description + " - $" + String.format("%.2f", amount) + "\n(" + category + ")";
    }
}