package fivetrue.database.tables;

import org.apache.catalina.User;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.PrimaryKey;

public class LikeDrips extends DatabaseObject{
	@PrimaryKey
	@AutoIncrement
	private int likeid = 0;
	
	@ForeignKey(Drips.class)
	private int dripid = 0;
	
	@ForeignKey(User.class)
	private String userid = null;
	
	private long likedate = 0;
	
	@MemberVariable
	private Drips drip = null;
	
	private Users likeUser = null;

	public int getLikeid() {
		return likeid;
	}

	public void setLikeid(int likeid) {
		this.likeid = likeid;
	}

	public int getDripid() {
		return dripid;
	}

	public void setDripid(int dripid) {
		this.dripid = dripid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public long getLikedate() {
		return likedate;
	}

	public void setLikedate(long likedate) {
		this.likedate = likedate;
	}

	public Drips getDrip() {
		return drip;
	}

	public void setDrip(Drips drip) {
		this.drip = drip;
	}

	public Users getLikeUser() {
		return likeUser;
	}

	public void setLikeUser(Users likeUser) {
		this.likeUser = likeUser;
	}

	@Override
	public String toString() {
		return "LikeDrips [likeid=" + likeid + ", dripid=" + dripid + ", userid=" + userid + ", likedate=" + likedate
				+ ", drip=" + drip + ", likeUser=" + likeUser + "]";
	}
	
}
