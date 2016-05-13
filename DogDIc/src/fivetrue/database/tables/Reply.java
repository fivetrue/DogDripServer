package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.PrimaryKey;

public class Reply extends DatabaseObject{
	@PrimaryKey
	@AutoIncrement
	private int replyid = -1;
	
	@ForeignKey(Drips.class)
	private int dripid = -1;
	
	private String comment = null;
	private String userid = null;
	private long replydate = 0;
	
	@MemberVariable
	private Drips drip = null;
	
	
	public int getReplyid() {
		return replyid;
	}



	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}



	public int getDripid() {
		return dripid;
	}



	public void setDripid(int dripid) {
		this.dripid = dripid;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public long getReplydate() {
		return replydate;
	}



	public void setReplydate(long replydate) {
		this.replydate = replydate;
	}



	public Drips getDrip() {
		return drip;
	}



	public void setDrip(Drips drip) {
		this.drip = drip;
	}

	@Override
	public String toString() {
		return "Reply [replyid=" + replyid + ", dripid=" + dripid + ", comment=" + comment + ", userid=" + userid
				+ ", replydate=" + replydate + ", drip=" + drip + "]";
	}
}
