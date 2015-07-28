package datamodel;

/**
 * Created by Hopewell Mutanda on 6/27/2015.
 */
public class TransactionModel {
    public String TransactionId;
    public String TransactionState;
    public String TransactionDate;
    public String StudentName;
    public String SchoolName;
    public String Class;
    public String BankName;
    public String AmountPaid;
    public String PaidBy;
    public String PaidVia;
    public String sDate;
    public String AccountName;
    public String AccountNumber;
    public String SchoolImage;


    public TransactionModel(String TransactionId, String TransactionState, String TransactionDate, String StudentName, String SchoolName,
                            String BankName, String Class, String AmountPaid, String PaidBy, String PaidVia, String sDate, String AccountName, String AccountNumber, String SchoolImage){
        this.TransactionId = TransactionId;
        this.TransactionState = TransactionState;
        this.TransactionDate = TransactionDate;
        this.StudentName = StudentName;
        this.SchoolName = SchoolName;
        this.Class = Class;
        this.BankName = BankName;
        this.AmountPaid = AmountPaid;
        this.PaidBy = PaidBy;
        this.PaidVia = PaidVia;
        this.sDate = sDate;
        this.AccountName = AccountName;
        this.AccountNumber = AccountNumber;
        this.SchoolImage = SchoolImage;

    }
}
