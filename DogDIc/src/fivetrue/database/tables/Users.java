package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.Password;
import com.fivetrue.db.annotation.PrimaryKey;

public class Users extends DatabaseObject{
	
	@PrimaryKey
	private String email = null;
	private String nickname = null;

	@Password
	private String password = null;
	/**
	 * status는 json 형태의 데이터이다.
	 */
	private long createdate = 0;
	private long lastconn = 0;
	private String imageurl = null;
	private int point = 0;
	private String gcm = null;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	public String getGcm() {
		return gcm;
	}
	public void setGcm(String gcm) {
		this.gcm = gcm;
	}
	
	public static void removePrivateInfo(Users u){
		if(u != null){
			u.setPassword(null);
			u.setGcm(null);
		}
	}
	
	@Override
	public String toString() {
		return "Users [email=" + email + ", nickname=" + nickname + ", password=" + password + ", createdate="
				+ createdate + ", lastconn=" + lastconn + ", imageurl=" + imageurl + ", point=" + point + ", gcm=" + gcm
				+ "]";
	}

}
