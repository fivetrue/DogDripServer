package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.PrimaryKey;

public class Reply extends DatabaseObject{
	@PrimaryKey
	@AutoIncrement
	private int id = 0;
	
	@ForeignKey(Drips.class)
	private int dripid = 0;
	private String comment = null;
	@ForeignKey(Users.class)
	private String author = null;
	private long createdate = 0;
	
	@MemberVariable
	private Drips drip = null;
	@MemberVariable
	private Users user = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}
	
	public Drips getDrip() {
		return drip;
	}
	
	public void setDrip(Drips drip) {
		this.drip = drip;
	}
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Reply [id=" + id + ", dripid=" + dripid + ", comment=" + comment + ", author=" + author
				+ ", createdate=" + createdate + ", drip=" + drip + ", user=" + user + "]";
	}
}
