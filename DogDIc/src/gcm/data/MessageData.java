package gcm.data;

public class MessageData {
	
	private int id = 0;
	private String title = null;
	private String message = null;
	private String imageUrl = null;
	private String imageUrlLarge = null;
	private long createdate = 0;
	private String uri = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrlLarge() {
		return imageUrlLarge;
	}
	public void setImageUrlLarge(String imageUrlLarge) {
		this.imageUrlLarge = imageUrlLarge;
	}
	public long getCreatedate() {
		return createdate;
	}
	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	@Override
	public String toString() {
		return "MessageData [id=" + id + ", title=" + title + ", message=" + message + ", imageUrl=" + imageUrl
				+ ", imageUrlLarge=" + imageUrlLarge + ", createdate=" + createdate + ", uri=" + uri + "]";
	}
	
	

}
