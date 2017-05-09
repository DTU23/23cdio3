package main.java.dk.dtu.model.DTO;

public class OperatorDTO
{
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	private int oprId;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	private String oprName;                
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	private String ini;                 
	/** Operatoer cpr-nr 10 karakterer */
	private String cpr;                 
	/** Operatoer password min. 7 max. 8 karakterer */
	private String password;
	private boolean admin;
	private String role;
	
	public OperatorDTO(){}

	public OperatorDTO(int oprId, String oprName, String ini, String cpr, String password, boolean admin, String role)
	{
		this.oprId = oprId;
		this.oprName = oprName;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.admin = admin;
		this.role = role;
	}
	
    public OperatorDTO(OperatorDTO opr)
    {
    	this.oprId = opr.getOprId();
    	this.oprName = opr.getOprName();
    	this.ini = opr.getIni();
    	this.cpr = opr.getCpr();
    	this.password = opr.getPassword();
    	this.admin = opr.getAdmin();
    	this.role = opr.getRole();
    }

	public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String getOprName() { return oprName; }
	public void setOprNavn(String oprNavn) { this.oprName = oprNavn; }
	public String getIni() { return ini; }
	public void setIni(String ini) { this.ini = ini; }
	public String getCpr() { return cpr; }
	public void setCpr(String cpr) { this.cpr = cpr; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public boolean getAdmin() { return this.admin; }
	public void setAdmin(boolean admin) { this.admin = admin; }
    public String getRole() {return this.role; }
    public void setRole(String role) { this.role = role; }
	public String toString() { return oprId + "\t" + oprName + "\t" + ini + "\t" + cpr + "\t" + password + "\t" + admin + "\t" + role; }
}