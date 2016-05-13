package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.Password;
import com.fivetrue.db.annotation.PrimaryKey;

public class Users extends DatabaseObject{
	
	@PrimaryKey
	private String userid = null;
	private String nickname = null;

	@Password
	private String password = null;
	/**
	 * status는 json 형태의 데이터이다.
	 */
	private String userimage = null;
	private long joindate = 0;
	private long lastconn = 0;
	private int point = 0;
	private String gcm = null;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getUserimage() {
		return userimage;
	}

	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}

	public long getJoindate() {
		return joindate;
	}

	public void setJoindate(long joindate) {
		this.joindate = joindate;
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

	public static void removePrivateInfo(Users u){
		if(u != null){
			u.setPassword(null);
			u.setGcm(null);
		}
	}
	
	@Override
	public String toString() {
		return "Users [userid=" + userid + ", nickname=" + nickname + ", password=" + password + ", userimage="
				+ userimage + ", joindate=" + joindate + ", lastconn=" + lastconn + ", point=" + point + ", gcm=" + gcm
				+ "]";
	}

}
