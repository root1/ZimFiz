package classes;

/**
 * Created by Hopewell Mutanda on 6/27/2015.
 */
public class Transactions {
    public String TransactionId;
    public String TransactionState;
    public String TransactionDate;
    public String StudentName;
    public String SchoolId;
    public String Class;
    public String BankId;
    public String AmountPaid;
    public String PaidBy;
    public String PaidVia;
    public String sDate;


    public Transactions(String TransactionId, String TransactionState, String TransactionDate, String StudentName, String SchoolId,
                        String BankId, String Class, String AmountPaid, String PaidBy, String PaidVia, String sDate){
        this.TransactionId = TransactionId;
        this.TransactionState = TransactionState;
        this.TransactionDate = TransactionDate;
        this.StudentName = StudentName;
        this.SchoolId = SchoolId;
        this.Class = Class;
        this.BankId = BankId;
        this.AmountPaid = AmountPaid;
        this.PaidBy = PaidBy;
        this.PaidVia = PaidVia;
        this.sDate = sDate;

    }
}
