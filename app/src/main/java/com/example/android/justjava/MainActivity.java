package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // added whipped cream?
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // added chocolate?
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        // added input name of the customer
        EditText nameText = (EditText) findViewById(R.id.name_field);
        String name = nameText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        //Don't display order summary on the screen
        //displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, name));

        //compose email with order summary
        String subject = "Just Java order for " + name;
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        composeEmail(subject, priceMessage);
    }

    /**
     * This method opens the email app and display subject and order summary.
     */
    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method creates order summary.
     *
     * @param price of the order
     * @param name of the customer
     * @param addWhippedCream is whether or not the user wants to add whipped cream or not
     * @param addChocolate is whether or not the user wants to add chocolate or not
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name + "\nAdd whipped cream? " + addWhippedCream +  "\nAdd chocolate? " + addChocolate + "\nQuantity: " + quantity + "\nTotal: " + price + "€" + "\nThank you!";
        return priceMessage;

    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream if user wants whipped cream as extra
     * @param addChocolate if user want chocolate as extra
     * @return the total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // initial price
        int basePrice = 5;

        //added 1 euro for whipped cream to base price
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //add 2 euro for chocolate to base price
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        //calculate the total price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        //quantity cannot go above 100 cups
        if (quantity >= 100) {
            //toast - display an error and finish the method
            Toast.makeText(this, "MAX no. of cups is 100", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }


    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        //quantity cannot go below 1 cup
        if (quantity <= 1) {
            //toast - display an error and finish the method
            Toast.makeText(this, "MIN no. of cups is 1", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


    /**
     * This method displays the given text on the screen.
     * DEPRECATED: don't display order summary on the screen

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
     */

}