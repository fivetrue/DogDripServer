package fivetrue.database.manager;

import java.util.List;

import com.fivetrue.db.manager.DatabaseManagerImpl;

import fivetrue.Constants;
import fivetrue.database.tables.Reply;

public class ReplyManager extends DatabaseManagerImpl<Reply>{
	
	private static ReplyManager sInstance = null;
	
	public static ReplyManager getInstance(){
		if(sInstance == null){
			sInstance = new ReplyManager();
		}
		return sInstance;
	}

	protected ReplyManager() {
		super(Constants.DB_SERVER, Constants.DB_NAME, Constants.DB_ID, Constants.DB_PASS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends Reply> getDatabaseObjectClass() {
		// TODO Auto-generated method stub
		return Reply.class;
	}
	
	public List<Reply> getAllReply(){
		return getReplyList(null, null, null);
	}
	
	public List<Reply> getReplyListByAuthor(String author){
		return getReplyList(null, author, null);
	}
	
	public List<Reply> getReplyListByDripId(String id){
		return getReplyList(null, null, id);
	}
	
	public List<Reply> getReplyListByDripIdAndAuthor(String id, String author){
		return getReplyList(null, author, id);
	}
	
	
	public List<Reply> getReplyList(String id, String author, String dripId){
		String where = null;
		if(id != null && id.length() > 0){
			where = "reply.id=" + id;
		}
		
		if(author != null && author.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " reply.author='" + author + "'";
		}
		
		if(dripId != null && dripId.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " reply.dripid=" + dripId;
		}
		
		return getSelectQueryData(null, where);
	}

}
