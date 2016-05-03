package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.PrimaryKey;

public class LikeDrips extends DatabaseObject{
	@PrimaryKey
	@AutoIncrement
	private int id = 0;
	
	private int dripid = 0;
	private String user = null;
	private String author = null;
	private long createdate = 0;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
	@Override
	public String toString() {
		return "LikeDrips [id=" + id + ", dripid=" + dripid + ", user=" + user + ", author=" + author + ", createdate="
				+ createdate + "]";
	}
}
