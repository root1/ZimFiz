package adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classes.Bank;
import classes.School;
import datamodel.NotificationModel;
import datamodel.TransactionModel;

public class SchoolsDatabaseAdapter{
	
	SchoolsDatabase schoolsDatabase;
	
	public SchoolsDatabaseAdapter(Context context){
		schoolsDatabase = new SchoolsDatabase(context);
	}
	
public long insertData(List<School> schoolsArray){
    deleteAll();
	    SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

    for(int i = 0; i < schoolsArray.size(); i++) {
        School currentSchool = schoolsArray.get(i);
        contentValues.put(schoolsDatabase.SCHOOL_ACCOUNT_NAME, currentSchool.SchoolAccountName);
        contentValues.put(schoolsDatabase.SCHOOL_ACCOUNT_NUMBER, currentSchool.SchoolAccountNumber);
        contentValues.put(schoolsDatabase.SCHOOL_ADDRESS, currentSchool.SchoolAddress);
        contentValues.put(schoolsDatabase.BANKIDFK, currentSchool.BankId);
        contentValues.put(schoolsDatabase.SCHOOL_CURRENT_FEES, currentSchool.SchoolCurrentFees);
        contentValues.put(schoolsDatabase.SCHOOL_IMAGE, currentSchool.SchoolImage);
        contentValues.put(schoolsDatabase.SCHOOL_NAME, currentSchool.SchoolName);
        contentValues.put(schoolsDatabase.SCHOOLID, currentSchool.SchoolId);
        db.insert(schoolsDatabase.SCHOOLS_TABLE, null, contentValues);
    }

		db.close();
		return 0;
	}

    public long insertBank(String BankId, String BankName,String BankAgentCode){
        SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(schoolsDatabase.BANKID, BankId);
        contentValues.put(schoolsDatabase.BANK_NAME, BankName);
        contentValues.put(schoolsDatabase.BANK_AGENT_CODE, BankAgentCode);
        long id = db.insert(schoolsDatabase.BANKS_TABLE, null, contentValues);
        Log.d("data insert","data");
        db.close();
        return id;
    }
    public long insertNotification(String NotificationId, String SchoolId,String NotificationSubject, String NotificationDetail, String NotificationDate){
        SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(schoolsDatabase.NOTIFICATIONID, NotificationId);
        contentValues.put(schoolsDatabase.SCHOOLIDFK, SchoolId);
        contentValues.put(schoolsDatabase.NOTIFICATION_SUBJECT, NotificationSubject);
        contentValues.put(schoolsDatabase.NOTIFICATION_DETAIL, NotificationDetail);
        contentValues.put(schoolsDatabase.NOTIFICATION_DATE, NotificationDate);
        long id = db.insert(schoolsDatabase.NOTIFICATIONS_TABLE, null, contentValues);
        db.close();
        return id;
    }

    public long inserttTransaction(String TransactionId, String TransactionState, String TransactionDate, String StudentName, String SchoolId,
                                   String BankId, String Class, String AmountPaid, String PaidBy, String PaidVia, String sDate){
        SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(schoolsDatabase.TRANSACTIONID, TransactionId);
        contentValues.put(schoolsDatabase.TRANSACTIONSTATE, TransactionState);
        contentValues.put(schoolsDatabase.TRANSACTIONDATE, TransactionDate);
        contentValues.put(schoolsDatabase.STUDENTNAME, StudentName);
        contentValues.put(schoolsDatabase.TRANSACTIONSHOOLIDFK, SchoolId);
        contentValues.put(schoolsDatabase.TRANSACTIONBANKIDFK, BankId);
        contentValues.put(schoolsDatabase.STUDENTCLASS, Class);
        contentValues.put(schoolsDatabase.AMOUNTPAID, AmountPaid);
        contentValues.put(schoolsDatabase.PAIDBY, PaidBy);
        contentValues.put(schoolsDatabase.PAIDVIA, PaidVia);
        contentValues.put(schoolsDatabase.DATERECIEVED, sDate);

        long id = db.insert(schoolsDatabase.TRANSACTIONS_TABLE, null, contentValues);
        db.close();
        return id;
    }
public List<TransactionModel> getAllTransactions(){
    List<TransactionModel> transactions = new ArrayList<TransactionModel>();
    SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM "+schoolsDatabase.TRANSACTIONS_TABLE+" AS transactions INNER JOIN "+schoolsDatabase.SCHOOLS_TABLE+" AS schools ON transactions."+schoolsDatabase.TRANSACTIONSHOOLIDFK+"=schools."+schoolsDatabase.SCHOOLID+" INNER JOIN "+schoolsDatabase.BANKS_TABLE+" as banks ON transactions."+schoolsDatabase.TRANSACTIONBANKIDFK+" = banks."+schoolsDatabase.BANKID+"", null);

    while(cursor.moveToNext()){
        int TransactionIdIndex = cursor.getColumnIndex(schoolsDatabase.TRANSACTIONID);
        int TransactionStateIndex = cursor.getColumnIndex(schoolsDatabase.TRANSACTIONSTATE);
        int TransactionDateIndex = cursor.getColumnIndex(schoolsDatabase.TRANSACTIONDATE);
        int StudentNameIndex = cursor.getColumnIndex(schoolsDatabase.STUDENTNAME);
        int SchoolNameIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_NAME);
        int ClassIndex = cursor.getColumnIndex(schoolsDatabase.STUDENTCLASS);
        int BankNameIndex = cursor.getColumnIndex(schoolsDatabase.BANK_NAME);
        int AmountPaidIndex = cursor.getColumnIndex(schoolsDatabase.AMOUNTPAID);
        int PaidByIndex = cursor.getColumnIndex(schoolsDatabase.PAIDBY);
        int PaidViaIndex = cursor.getColumnIndex(schoolsDatabase.PAIDVIA);
        int sDateIndex = cursor.getColumnIndex(schoolsDatabase.DATERECIEVED);
        int AccountNameIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_ACCOUNT_NAME);
        int AccountNumberIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_ACCOUNT_NUMBER);
        int SchoolImageIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_IMAGE);

        String TransactionId = cursor.getString(TransactionIdIndex);
        String TransactionState = cursor.getString(TransactionStateIndex);
        String TransactionDate = cursor.getString(TransactionDateIndex);
        String StudentName = cursor.getString(StudentNameIndex);
        String SchoolName = cursor.getString(SchoolNameIndex);
        String Class = cursor.getString(ClassIndex);
        String BankName = cursor.getString(BankNameIndex);
        String AmountPaid = cursor.getString(AmountPaidIndex);
        String PaidBy = cursor.getString(PaidByIndex);
        String PaidVia = cursor.getString(PaidViaIndex);
        String sDate = cursor.getString(sDateIndex);
        String AccountName = cursor.getString(AccountNameIndex);
        String AccountNumber = cursor.getString(AccountNumberIndex);
        String SchoolImage = cursor.getString(SchoolImageIndex);
        transactions.add(new TransactionModel(TransactionId, TransactionState, TransactionDate, StudentName, SchoolName, Class, BankName, AmountPaid, PaidBy, PaidVia, sDate, AccountName, AccountNumber, SchoolImage));

    }
    return transactions;
}
    public List<NotificationModel> getAllNotifications(){
        List<NotificationModel> notifications = new ArrayList<NotificationModel>();
        SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+schoolsDatabase.NOTIFICATIONS_TABLE+" AS notifications INNER JOIN "+schoolsDatabase.SCHOOLS_TABLE+" AS schools ON notifications."+schoolsDatabase.SCHOOLIDFK+"=schools."+schoolsDatabase.SCHOOLID+"", null);
        while(cursor.moveToNext()){
            int NotificationIdIndex = cursor.getColumnIndex(schoolsDatabase.NOTIFICATIONID);
            int SchoolNameIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_NAME);
            int NotificationSubjectIndex = cursor.getColumnIndex(schoolsDatabase.NOTIFICATION_SUBJECT);
            int NotificationDetailIndex = cursor.getColumnIndex(schoolsDatabase.NOTIFICATION_DETAIL);
            int NotificationDateIndex = cursor.getColumnIndex(schoolsDatabase.NOTIFICATION_DATE);
            int SchoolIdIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOLID);
            int SchoolImageIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_IMAGE);

            String NotificationId = cursor.getString(NotificationIdIndex);
            String NotificationSubject = cursor.getString(NotificationSubjectIndex);
            String NotificationDetail = cursor.getString(NotificationDetailIndex);
            String SchoolName = cursor.getString(SchoolNameIndex);
            String NotificationDate = cursor.getString(NotificationDateIndex);
            String SchoolId = cursor.getString(SchoolIdIndex);
            String SchoolImage = cursor.getString(SchoolImageIndex);

            notifications.add(new NotificationModel(NotificationId, SchoolId, SchoolName, SchoolImage, NotificationSubject, NotificationDetail, NotificationDate));
        }
        return  notifications;
    }
    public List<Bank> getAllBanks(){
        List<Bank> banks = new ArrayList<Bank>();
        SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM my_banks_table", null);
        while(cursor.moveToNext()){
            int BankIdIndex = cursor.getColumnIndex(schoolsDatabase.BANKID);
            int BankNameIndex = cursor.getColumnIndex(schoolsDatabase.BANK_NAME);
            int BankAgentCodeIndex = cursor.getColumnIndex(schoolsDatabase.BANK_AGENT_CODE);

            String BankId = cursor.getString(BankIdIndex);
            String BankName = cursor.getString(BankNameIndex);
            String BankAgentCode = cursor.getString(BankAgentCodeIndex);

            banks.add(new Bank(BankId, BankName, BankAgentCode));
        }
        db.close();
        return banks;
    }
public void deleteAll(){
    SQLiteDatabase db = schoolsDatabase.getWritableDatabase();
    db.delete(schoolsDatabase.SCHOOLS_TABLE, null, null);
//    Cursor cursor = db.rawQuery("DELETE  FROM "+schoolsDatabase.SCHOOLS_TABLE+"", null);
//    while (cursor.moveToNext()){
//        Log.d("Sucess","Sucess");
//
//    }
    db.close();
}
	public List<School> getAllSchools(){
        List<School> schools = new ArrayList<School>();

		SQLiteDatabase db = schoolsDatabase.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+schoolsDatabase.SCHOOLS_TABLE+" AS schools INNER JOIN "+schoolsDatabase.BANKS_TABLE+" AS banks ON schools."+schoolsDatabase.BANKIDFK+"=banks."+schoolsDatabase.BANKID+"", null);

		while(cursor.moveToNext()){
			int SchoolNameIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_NAME);
			int AccountNumberIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_ACCOUNT_NUMBER);
			int SchoolAddressIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_ADDRESS);
            int SchoolImageIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_IMAGE);
            int SchoolAccountNameIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_ACCOUNT_NAME);
            int BankIdFKIndex = cursor.getColumnIndex(schoolsDatabase.BANKIDFK);
            int SchoolCurrentFeesIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOL_CURRENT_FEES);
            int SchoolIdIndex = cursor.getColumnIndex(schoolsDatabase.SCHOOLID);
            int BankNameIndex = cursor.getColumnIndex(schoolsDatabase.BANK_NAME);


			String SchoolName = cursor.getString(SchoolNameIndex);
			String AccountNumber = cursor.getString(AccountNumberIndex);
			String SchoolAddress = cursor.getString(SchoolAddressIndex);
            String SchoolImage = cursor.getString(SchoolImageIndex);
            String SchoolAccountName = cursor.getString(SchoolAccountNameIndex);
            String BankId = cursor.getString(BankIdFKIndex);
            String SchoolCurrentFees = cursor.getString(SchoolCurrentFeesIndex);
            String SchoolId = cursor.getString(SchoolIdIndex);
           String BankName = cursor.getString(BankNameIndex);
            schools.add(new School(SchoolName, SchoolAddress, SchoolAccountName, AccountNumber, SchoolId, BankName, SchoolCurrentFees, SchoolImage));

		}
		db.close();
        return schools;
	}
	static class SchoolsDatabase extends SQLiteOpenHelper{

		private static final String DATABASENAME = "schools";
        //tables
		private static final String SCHOOLS_TABLE = "schools_table";
        private static final String BANKS_TABLE = "my_banks_table";
        private static final String TRANSACTIONS_TABLE = "transactions";
        private static final String NOTIFICATIONS_TABLE = "notifications_table";

        //schools_table
		private static final String SCHOOLID = "school_id";
		private static final String SCHOOL_NAME = "school_name";
		private static final String SCHOOL_ADDRESS = "school_address";
		private static final String SCHOOL_IMAGE = "school_image";
		private static final String BANKIDFK = "bank_id_fk";
		private static final String SCHOOL_ACCOUNT_NAME = "account_name";
		private static final String SCHOOL_ACCOUNT_NUMBER = "account_nummber";
		private static final String SCHOOL_CURRENT_FEES = "current_fees";

        //banks_table
        private static final String BANKID = "bank_id";
        private static final String BANK_NAME = "bank_name";
        private static final String BANK_AGENT_CODE = "bank_agent_code";

        //notifications_table
        private static final String NOTIFICATIONID = "notification_id";
        private static final String SCHOOLIDFK = "school_id_fk";
        private static final String NOTIFICATION_SUBJECT = "notification_subject";
        private static final String NOTIFICATION_DETAIL = "notification_detail";
        private static final String NOTIFICATION_DATE = "notification_date";

        //transactions table
        private static final String TRANSACTIONID = "transaction_id";
        private static final String TRANSACTIONSTATE = "tranasaction_state";
        private static final String TRANSACTIONDATE = "transaction_date";
        private static final String STUDENTNAME = "student_name";
        private static final String TRANSACTIONSHOOLIDFK = "transaction_school_id_fk";
        private static final String STUDENTCLASS = "student_class";
        private static final String TRANSACTIONBANKIDFK = "transaction_bank_id_fk";
        private static final String AMOUNTPAID = "amount_paid";
        private static final String PAIDBY = "paid_by";
        private static final String PAIDVIA = "paid_via";
        private static final String DATERECIEVED = "date_recieved";

	    private static final int DATABASE_VERSION = 1;
		private static final String UID = "_id";
		private static final String DROP_SCHOOLS_TABLE = "DROP TABLE  IF EXISTS "+ SCHOOLS_TABLE +"";
        private static final String DROP_NOTIFICATIONS_TABLE = "DROP TABLE  IF EXISTS "+ NOTIFICATIONS_TABLE +"";
        private static final String DROP_BANKS_TABLE = "DROP TABLE  IF EXISTS "+ BANKS_TABLE +"";
        private static final String DROP_TRANSACTIONS_TABLE = "DROP TABLE  IF EXISTS "+TRANSACTIONS_TABLE+"";

		private static final String CREATE_SCHOOLS_TABLE = "CREATE TABLE "+ SCHOOLS_TABLE +"("+UID+" " +"INTEGER PRIMARY KEY AUTOINCREMENT,"+SCHOOLID+" VARCHAR(255), "+
                SCHOOL_NAME+" VARCHAR(255), "+SCHOOL_ADDRESS+" VARCHAR(255), "+
                ""+SCHOOL_IMAGE+" VARCHAR(255), "+ BANKIDFK +" VARCHAR(255), "+
                ""+SCHOOL_ACCOUNT_NAME+" VARCHAR(255), "+SCHOOL_ACCOUNT_NUMBER+
                " VARCHAR(255), "+SCHOOL_CURRENT_FEES+" VARCHAR(255));";
        private static final String CREATE_BANKS_TABLE = "CREATE TABLE "+ BANKS_TABLE +"("+UID+" "+"INTEGER PRIMARY KEY AUTOINCREMENT,"+BANKID+" VARCHAR(255), "+BANK_NAME+" VARCHAR(255), "+BANK_AGENT_CODE+" VARCHAR(255));";
        private static final String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE "+ NOTIFICATIONS_TABLE +"("+UID+" "+"INTEGER PRIMARY KEY AUTOINCREMENT,"+NOTIFICATIONID+" VARCHAR(255), "+NOTIFICATION_SUBJECT+" VARCHAR(255), "+NOTIFICATION_DETAIL+" VARCHAR(255), "+NOTIFICATION_DATE+" VARCHAR(255), "+SCHOOLIDFK+" VARCHAR(255));";
        private static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE "+TRANSACTIONS_TABLE+"("+UID+" "+"INTEGER PRIMARY KEY AUTOINCREMENT,"+TRANSACTIONID+" VARCHAR(255), "+TRANSACTIONSTATE+" VARCHAR(255), "+TRANSACTIONDATE+" VARCHAR(255), "+STUDENTNAME+" VARCHAR(255), "+TRANSACTIONSHOOLIDFK+" VARCHAR(255), "+STUDENTCLASS+" VARCHAR(255), "+TRANSACTIONBANKIDFK+" VARCHAR(255), "+AMOUNTPAID+" VARCHAR(255), "+PAIDBY+" VARCHAR(255), "+PAIDVIA+" VARCHAR(255), "+DATERECIEVED+" VARCHAR(255));";
		private Context context;

		public SchoolsDatabase(Context context) {
			super(context, DATABASENAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {


			try {
                db.execSQL(CREATE_BANKS_TABLE);
				db.execSQL(CREATE_SCHOOLS_TABLE);
                db.execSQL(CREATE_NOTIFICATIONS_TABLE);
                db.execSQL(CREATE_TRANSACTIONS_TABLE);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
            Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_SCHOOLS_TABLE);
                db.execSQL(DROP_BANKS_TABLE);
                db.execSQL(DROP_NOTIFICATIONS_TABLE);
                db.execSQL(DROP_TRANSACTIONS_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		

	}
	
}
