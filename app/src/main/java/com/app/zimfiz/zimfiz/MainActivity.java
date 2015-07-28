package com.app.zimfiz.zimfiz;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;


import adapter.SchoolsDatabaseAdapter;
import fragments.AboutUs;
import fragments.Apply;
import fragments.AvailableSchools;
import fragments.Profile;
import fragments.RecentPayments;
import fragments.SchoolNotifications;
import fragments.TransactionCharges;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;
import mockedActivity.Settings;
import utils.Constants;

/**
 * Created by neokree on 18/01/15.
 */
public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener{
    private static final int JOB_ID = 100;
    private SchoolsDatabaseAdapter schoolsDatabase;
    MaterialAccount account;

    MaterialSection section1, section2, section3 , recorder, night, last , apply;
    @Override
    public void init(Bundle savedInstanceState) {

//        account = new MaterialAccount("Hopewell Mutanda","hopewell@gmail.com",new ColorDrawable(Color.parseColor("#9e9e9e")),this.getResources().getDrawable(R.drawable.bamboo));
//        this.addAccount(account);
        // add accounts
        SharedPreferences sharedPreferences = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", Constants.NOTAVAILABLE);
        String surname = sharedPreferences.getString("user_surname", Constants.NOTAVAILABLE);
        String phoneNumber = sharedPreferences.getString("phone_number", Constants.NOTAVAILABLE);
        if(name.equals(Constants.NOTAVAILABLE) || surname.equals(Constants.NOTAVAILABLE) || phoneNumber.equals(Constants.NOTAVAILABLE)){
            MaterialAccount account = new MaterialAccount(this.getResources(),"","",R.drawable.photo, R.drawable.mat2);
            this.addAccount(account);

        }else{
            MaterialAccount account = new MaterialAccount(this.getResources(),name+" "+surname,phoneNumber,R.drawable.photo, R.drawable.mat2);
            this.addAccount(account);
        }


        this.setAccountListener(this);

        // create sections

        section2 = this.newSection("Recent Payments",new RecentPayments()).setSectionColor(Color.parseColor("#2196f3"));
        section3 = this.newSection("Transaction Charges",new TransactionCharges()).setSectionColor(Color.parseColor("#2196f3"));

        recorder = this.newSection("Available Schools",this.getResources().getDrawable(R.drawable.ic_mic_white_24dp),new AvailableSchools()).setNotifications(10).setSectionColor(Color.parseColor("#2196f3"));

        night = this.newSection("School Notifications", this.getResources().getDrawable(R.drawable.ic_hotel_grey600_24dp), new SchoolNotifications())
                .setSectionColor(Color.parseColor("#2196f3")).setNotifications(150);

        apply = this.newSection("Apply", this.getResources().getDrawable(R.drawable.ic_hotel_grey600_24dp), new Apply())
                .setSectionColor(Color.parseColor("#2196f3"));

        last = this.newSection("About", new AboutUs()).setSectionColor(Color.parseColor("#2196f3"));
//        Intent logoutm = new Intent(this, Login.class);
//        logout = this.newSection("Logout", logoutm);

//        Intent i = new Intent(this, Settings.class);
//        settingsSection = this.newSection("Settings",this.getResources().getDrawable(R.drawable.ic_settings_black_24dp),i);

        Intent intent = new Intent(this, SelectInstition.class);
        section1 = this.newSection("Make Payment", intent);



        // add your sections to the drawer
        this.addSection(section2);
        this.addSection(section1);
        this.addSection(section3);
        this.addSubheader("Schools");
        this.addSection(recorder);
        this.addSection(night);
        this.addSection(apply);
        this.addDivisor();
        this.addSection(last);
//        this.addSection(logout);
        //this.addBottomSection(settingsSection);

        this.addMultiPaneSupport();

        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);


      //  schoolsDatabase = new SchoolsDatabaseAdapter(this);
        //JsonRequests.schoolsDataRequest(this);
//        schoolsDatabase.insertData("Z.R.P High School", "003", "Nyanga Mutare Zimbabwe", "SchoolImage",
//                "B0001B", "AccountName", "AccountNumber","CurrentFees");

//        schoolsDatabase.insertBank("B0001A", "Barclays Zimbabwe", "6372");
//        schoolsDatabase.insertNotification("P003", "H0002P", "Consultation Day of the fifth of August 2015", "This is the detail of the notification", "26-05-2015");
//        schoolsDatabase.inserttTransaction("001", "Successful", "28-06-2015", "Hopewell Mutanda", "H0002P",
//                "B0001A", "Form 2, Term 3", "600", "Hillary Mutanda", "Ecocash", "29-06-2015");
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {

    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        // set the indicator for child fragments
        // N.B. call this method AFTER the init() to leave the time to instantiate the ActionBarDrawerToggle
        this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public void onHomeAsUpSelected() {
        // when the back arrow is selected this method is called

    }
}
