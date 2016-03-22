package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;

public class LikeDrips extends DatabaseObject{
	
	private int id = 0;
	private String user = null;
	private String author = null;
	private long createdate = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		return "LikeDrips [id=" + id + ", user=" + user + ", author=" + author + ", createdate=" + createdate + "]";
	}
}
