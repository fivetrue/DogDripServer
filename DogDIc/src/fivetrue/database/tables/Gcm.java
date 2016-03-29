package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.PrimaryKey;

public class Gcm extends DatabaseObject {
	
	@PrimaryKey
	private String gcm = null;
	private String email = null;
	private String device = null;
	public String getGcm() {
		return gcm;
	}
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	@Override
	public String toString() {
		return "Gcm [gcm=" + gcm + ", email=" + email + ", device=" + device + "]";
	}
}
