package classes;

public class School {
	public String SchoolName;
	public String SchoolAddress;
    public String SchoolAccountName;
    public String SchoolAccountNumber;
    public String SchoolId;
    public String BankId;
    public String SchoolCurrentFees;
    public String SchoolImage;
	
	public School(String SchoolName, String SchoolAddress, String SchoolAccountName, String SchoolAccountNumber, String SchoolId
			, String BankId, String SchoolCurrentFees, String SchoolImage) {
		this.SchoolName = SchoolName;
		this.SchoolAddress = SchoolAddress;
		this.SchoolAccountName = SchoolAccountName;
		this.SchoolAccountNumber = SchoolAccountNumber;
		this.SchoolId = SchoolId;
		this.BankId = BankId;
		this.SchoolCurrentFees = SchoolCurrentFees;
        this.SchoolImage = SchoolImage;
		
	}

}
