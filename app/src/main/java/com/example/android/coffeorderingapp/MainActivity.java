package com.example.android.coffeorderingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
//--------------------------------------global variables

    int quantity=2;


    //-------------------------------------------buttons to increment / decrement
    public void decrement(View v) {


        if(quantity<2)
        {
            quantity=1;
            Toast.makeText(this, "You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();

            return;
        }
        quantity--;


        displayQuantity(quantity);

    }


    public void increment(View v) {


        if (quantity >99) {
            quantity=100;
            Toast.makeText(this, "You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }


        quantity++;
        displayQuantity(quantity);
        ;

    }


    //-------------------------------------------Clear order
    public void clearOrder(View v) {

        displayQuantity(quantity = 0);


    }
    //--------------------------------------calculate price

    public int calculatePrice(boolean whipCream,boolean choclateCream)
    {

        int basePrice =  5;

        if(whipCream)
        {

            basePrice=basePrice+1;
        }

        if(choclateCream)
        {

            basePrice=basePrice+2;
        }

        basePrice=basePrice*quantity;

        return basePrice;
    }



    //-------------------------------------------BUTTON TO SUBMIT ORDER

    public void submitOrder(View view) {

        //Whipped_cream_checkbox
        CheckBox whippCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippCream = whippCreamCheckBox.isChecked();

        CheckBox choclateCheckBox = (CheckBox) findViewById(R.id.Choclate_checkbox);
        boolean  hasChoclate= choclateCheckBox.isChecked();


        EditText editText=(EditText) findViewById(R.id.enter_name_editText);
        String name=editText.getText().toString();

       // Log.v("MainActivity", "Has whiip cream " + hasWhippCream); used to ckeck output debug error


        int price = calculatePrice(hasWhippCream,hasChoclate);
       String priceMessage = createOrderSummary( price,hasWhippCream,hasChoclate,name);


        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee ordering system for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {

            startActivity(intent);
        }


        displayQuantity(quantity);


    }

//----------------------------------------------OUTPUT METHODS

    public void displayQuantity(int num) {
        TextView qtyTxtView = (TextView) findViewById(R.id.text_qty_view);
        qtyTxtView.setText(String.valueOf(num));//this works because "" it converts the number

        // qtyTxtView.setText(num);--this will give an error because it doesnt have conversion to string .valueof()
        //  qtyTxtView.setText(String.valueOf(num));


    }






    private String createOrderSummary(int price,boolean addWhippedCream ,boolean addChoclate,String getNameInput)
    {



        String priceMessage ="Name: " + getNameInput ;
        priceMessage = priceMessage +"\nAdd Whipped cream?: "+addWhippedCream;
        priceMessage = priceMessage +"\nAdd choclate cream?: "+addChoclate;
        priceMessage = priceMessage +"\nQuantity: "+quantity;
        priceMessage =priceMessage+ "\nTotal: " +  NumberFormat.getCurrencyInstance().format(price);
        priceMessage = priceMessage + "\nThank you!";







        return priceMessage;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.coffeorderingapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.coffeorderingapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
