package kr.ac.gwnu.cs.smartshoes.alarm;

public class Alarm {
	
	private int ID;
	private String TIME;
	private String REPEAT;
	private String CONTENT;
	private int REVIEW;
	
	public Alarm() {
		// TODO Auto-generated constructor stub
	}
	
	public Alarm(int ID, String TIME, String REPEAT, String CONTENT, int REVIEW) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.TIME = TIME;
		this.REPEAT = REPEAT;
		this.CONTENT = CONTENT;
		this.REVIEW = REVIEW;
	}
	
	public int getID() {return ID;}
	public String getTIME() {return TIME;}
	public String getREPEAT() {return REPEAT;}
	public String getCONTENT() {return CONTENT;}
	public int getREVIEW() {return REVIEW;}
	
	public void setID(int ID) {this.ID = ID;}
	public void setTIME(String TIME) {this.TIME = TIME;}
	public void setREPEAT(String REPEAT) {this.REPEAT = REPEAT;}
	public void setCONTENT(String CONTENT) {this.CONTENT = CONTENT;}
	public void setREVIEW(int REVIEW) {this.REVIEW = REVIEW;}
}
