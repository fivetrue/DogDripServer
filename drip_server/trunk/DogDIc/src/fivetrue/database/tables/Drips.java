package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.PrimaryKey;

public class Drips extends DatabaseObject{
	
	@PrimaryKey
	@AutoIncrement
	private int dripid = -1;
	private String drip = null;
	private String dripimage = null;
	
	@ForeignKey(Users.class)
	private String userid = null;
	
	private long createdate = 0;
	private int heartcount = 0;
	
	
	@MemberVariable
	private Users user = null;
	
	
	
	public int getDripid() {
		return dripid;
	}



	public void setDripid(int dripid) {
		this.dripid = dripid;
	}



	public String getDrip() {
		return drip;
	}



	public void setDrip(String drip) {
		this.drip = drip;
	}



	public String getDripimage() {
		return dripimage;
	}



	public void setDripimage(String dripimage) {
		this.dripimage = dripimage;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public long getCreatedate() {
		return createdate;
	}



	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}



	public int getHeartcount() {
		return heartcount;
	}



	public void setHeartcount(int heartcount) {
		this.heartcount = heartcount;
	}



	public Users getUser() {
		return user;
	}



	public void setUser(Users user) {
		this.user = user;
	}



	@Override
	public String toString() {
		return "Drips [dripid=" + dripid + ", drip=" + drip + ", dripimage=" + dripimage + ", userid=" + userid
				+ ", createdate=" + createdate + ", heartcount=" + heartcount + ", user=" + user + "]";
	}
}
