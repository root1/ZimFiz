package com.app.zimfiz.zimfiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.zimfiz.zimfiz.R;

import utils.Constants;

/**
 * Created by neokree on 24/11/14.
 */
public class ConfirmPayment extends ActionBarActivity {
    private Toolbar toolbar;
    String sBankName;
    String sAccountNumber;
    String sAccountName;
    String sSelectSchool;
    String sStudentName;
    String sForm;
    String sTerm;
    String sAmount;
    String sPaymentMethod;
    String sPaidBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_payment);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String sBankName = extras.getString("sBankName");
        String sAccountNumber =  extras.getString("sAccountNumber");
        String sAccountName =  extras.getString("sAccountName");
        String sSelectSchool =  extras.getString("sSelectSchool");
        String sStudentName =  extras.getString("sStudentName");
        String sForm =  extras.getString("sForm");
        String sTerm =  extras.getString("sTerm");
        String sAmount =  extras.getString("sAmount");
        String sPaymentMethod =  getIntent().getExtras().getString("sPaymentMethod");
        String sPaidBy =  getIntent().getExtras().getString("sPaidBy");

        Double amountTuition = Double.parseDouble(sAmount);
        Double amounCharge = amountTuition * Constants.CHARGE;
        Double amounPayablex = amountTuition + amounCharge;



        TextView bankName = (TextView)findViewById(R.id.bankName);
        TextView accountNumber = (TextView)findViewById(R.id.accountNumber);
        TextView accountName = (TextView)findViewById(R.id.accountName);
        TextView schoolName = (TextView)findViewById(R.id.schoolName);
        TextView studentName = (TextView)findViewById(R.id.studentName);
        TextView studentClass = (TextView)findViewById(R.id.studentClass);
        TextView amount = (TextView)findViewById(R.id.amountPaid);
        TextView paidVia = (TextView)findViewById(R.id.paymentMethod);
        TextView paidBy = (TextView)findViewById(R.id.paidBy);
        TextView charge = (TextView)findViewById(R.id.charge);
        TextView amountPayable = (TextView)findViewById(R.id.amountPayable);
        Button btcontinue = (Button)findViewById(R.id.btncontinue);

        Toast.makeText(getApplicationContext(), sForm +" "+ sTerm, Toast.LENGTH_SHORT).show();

        bankName.setText(sBankName);
        accountNumber.setText(sAccountNumber);
        accountName.setText(sAccountName);
        schoolName.setText(sSelectSchool);
        studentName.setText(sStudentName);
        studentClass.setText(sForm +" "+ sTerm);
        amount.setText(sAmount);
        paidVia.setText(sPaymentMethod);
        paidBy.setText(sPaidBy);



        charge.setText("$ "+String.valueOf(amounCharge));
        amountPayable.setText("$ "+String.valueOf(amounPayablex));

        btcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Send details to API", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);

    }
}
