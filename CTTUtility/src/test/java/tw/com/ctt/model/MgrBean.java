package tw.com.ctt.model;

public class MgrBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3493545495346730927L;
	private java.sql.Timestamp datetime;
	public java.sql.Timestamp getDatetime() {
		return datetime;
	}
	public void setDatetime(java.sql.Timestamp datetime) {
		this.datetime = datetime;
	}
}
