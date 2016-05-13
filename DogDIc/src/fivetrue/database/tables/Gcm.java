package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.PrimaryKey;

public class Gcm extends DatabaseObject {
	
	@PrimaryKey
	private String gcm = null;
	private String userid = null;
	private String device = null;
	
	public String getGcm() {
		return gcm;
	}
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	@Override
	public String toString() {
		return "Gcm [gcm=" + gcm + ", userid=" + userid + ", device=" + device + "]";
	}
	
}
