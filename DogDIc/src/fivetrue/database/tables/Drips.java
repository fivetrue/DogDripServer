package fivetrue.database.tables;

import com.fivetrue.db.DatabaseObject;
import com.fivetrue.db.annotation.AutoIncrement;
import com.fivetrue.db.annotation.ForeignKey;
import com.fivetrue.db.annotation.MemberVariable;
import com.fivetrue.db.annotation.PrimaryKey;

public class Drips extends DatabaseObject{
	
	@PrimaryKey
	@AutoIncrement
	private int id = 0;
	private String drip = null;
	private String imageurl = null;
	
	@ForeignKey(Users.class)
	private String author = null;
	private long createdate = 0;
	private int heartcount = 0;
	
	@MemberVariable
	private Users user = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDrip() {
		return drip;
	}
	public void setDrip(String drip) {
		this.drip = drip;
	}
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
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
		return "Drips [id=" + id + ", drip=" + drip + ", imageurl=" + imageurl + ", author=" + author + ", createdate="
				+ createdate + ", heartcount=" + heartcount + ", user=" + user + "]";
	}
}
