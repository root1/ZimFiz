package com.app.zimfiz.zimfiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by neokree on 24/11/14.
 */
public class TransactionHistory extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_history);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView bankName = (TextView)findViewById(R.id.bankName);
        TextView accountNumber = (TextView)findViewById(R.id.accountNumber);
        TextView accountName = (TextView)findViewById(R.id.accountName);
        TextView schoolName = (TextView)findViewById(R.id.schoolName);
        TextView studentName = (TextView)findViewById(R.id.studentName);
        TextView studentClass = (TextView)findViewById(R.id.studentClass);
        TextView amount = (TextView)findViewById(R.id.amountPaid);
        TextView paidVia = (TextView)findViewById(R.id.paymentMethod);

        TextView transactioId = (TextView)findViewById(R.id.id);

        TextView status = (TextView)findViewById(R.id.status);

//        sBankName = getIntent().getExtras().getString(sBankName);
//        sAccountNumber =  getIntent().getExtras().getString(sAccountNumber);
//        sAccountName =  getIntent().getExtras().getString(sAccountName);
//        sSelectSchool =  getIntent().getExtras().getString(sSelectSchool);
//        sStudentName =  getIntent().getExtras().getString(sStudentName);
//        sClass =  getIntent().getExtras().getString(sClass);
//        sAmount =  getIntent().getExtras().getString(sAmount);
//        sPaymentMethod =  getIntent().getExtras().getString(sPaymentMethod);
//        sPaidBy =  getIntent().getExtras().getString(sPaidBy);
//        sState=  getIntent().getExtras().getString(sState);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String sBankName = extras.getString("sBankName");
        String sAccountNumber = extras.getString("sAccountNumber");
        String sAccountName =  extras.getString("sAccountName");
        String sSelectSchool =  extras.getString("sSelectSchool");
        String sStudentName =  extras.getString("sStudentName");
        String sClass =  extras.getString("sClass");
        String sAmount =  extras.getString("sAmount");
        String sPaymentMethod =  extras.getString("sPaymentMethod");
        String sTransactionId =  extras.getString("sTransactionId");
        String sState=  extras.getString("sState");



        bankName.setText(sBankName);
        accountNumber.setText(sAccountNumber);
        accountName.setText(sAccountName);
        schoolName.setText(sSelectSchool);
        studentName.setText(sStudentName);
        studentClass.setText(sClass);
        amount.setText(sAmount);
        paidVia.setText(sPaymentMethod);
        transactioId.setText(sTransactionId);

        status.setText(sState);
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
