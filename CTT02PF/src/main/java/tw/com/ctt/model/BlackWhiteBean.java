package tw.com.ctt.model;

public class BlackWhiteBean implements java.io.Serializable {

	private static final long serialVersionUID = 4733280186246462595L;
	private long id;
	/**
	 * 名稱
	 */
	private String name;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * ip1
	 */
	private int ip1;
	/**
	 * ip2
	 */
	private int ip2;
	/**
	 * ip3
	 */
	private int ip3;
	/**
	 * ip4
	 */
	private int ip4;
	/**
	 * 描述
	 */
	private String text;
	/**
	 * 類型 0 黑名單 1 白名單
	 */
	private boolean type;
	/**
	 * 地區
	 */
	private String area;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getIp1() {
		return ip1;
	}

	public void setIp1(int ip1) {
		this.ip1 = ip1;
	}

	public int getIp2() {
		return ip2;
	}

	public void setIp2(int ip2) {
		this.ip2 = ip2;
	}

	public int getIp3() {
		return ip3;
	}

	public void setIp3(int ip3) {
		this.ip3 = ip3;
	}

	public int getIp4() {
		return ip4;
	}

	public void setIp4(int ip4) {
		this.ip4 = ip4;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
