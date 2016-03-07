package tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.PrimaryKey;

public class Users extends DatabaseObject{
	
	@PrimaryKey
	private String email = null;
	
	private String password = null;
	/**
	 * status는 json 형태의 데이터이다.
	 */
	private String status = null;
	private String session = null;
	private long createdate = 0;
	private long lastconn = 0;
	private int point = 0;
	private String gcm = null;
	private String device = null;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}
	public long getLastconn() {
		return lastconn;
	}
	public void setLastconn(long lastconn) {
		this.lastconn = lastconn;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getGcm() {
		return gcm;
	}
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	@Override
	public String toString() {
		return "Users [email=" + email + ", password=" + password + ", status=" + status + ", session=" + session
				+ ", createdate=" + createdate + ", lastconn=" + lastconn + ", point=" + point + ", gcm=" + gcm
				+ ", device=" + device + "]";
	}

}
