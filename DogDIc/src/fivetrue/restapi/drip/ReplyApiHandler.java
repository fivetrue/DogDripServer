package fivetrue.restapi.drip;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fivetrue.db.DBMessage;

import fivetrue.database.manager.DripsManager;
import fivetrue.database.manager.GcmManager;
import fivetrue.database.manager.ReplyManager;
import fivetrue.database.manager.UsersManager;
import fivetrue.database.tables.Drips;
import fivetrue.database.tables.Gcm;
import fivetrue.database.tables.Reply;
import fivetrue.database.tables.Users;
import fivetrue.restapi.BaseApiHandler;
import fivetrue.restapi.Result;
import gcm.manager.NotificationManager;

public class ReplyApiHandler extends BaseApiHandler{

	public static final int MAX_REPLY_COUNT_PER_DRIP = 5;

	public static final String ID = "id";
	public static final String AUTHOR = "author";
	public static final String DRIP_ID = "dripId";
	public static final String COMMENT = "comment";

	public static final int ERROR_CODE_INVALID_AUTHOR = 1000;
	public static final int ERROR_CODE_INVALID_DRIP = 1002;
	public static final int ERROR_CODE_MAX_REPLY_USER = 2001;


	public ReplyApiHandler(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}


	public void readReply(){
		if(checkRequestValidation()){
			String id = getParameter(ID);
			String dripId = getParameter(DRIP_ID);
			String author = getParameter(AUTHOR);
			boolean isValidId = id != null && id.length() > 0;
			boolean isValidDripId = dripId != null && dripId.length() > 0;
			boolean isValidAuthor = author != null && author.length() > 0;
			Result result = new Result();
			
			if(isValidAuthor || isValidDripId || isValidId){
				List<Reply> replyList = ReplyManager.getInstance().getReplyList(id, author, dripId);
				for(Reply r : replyList){
					Users.removePrivateInfo(r.getDrip().getUser());
				}
				result.setErrorCode(Result.ERROR_CODE_OK);
				result.setResult(replyList);
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
			}
			writeObject(result);
		}
	}
	

	public void putReply(){
		if(checkRequestValidation()){

			String dripId = getParameter(DRIP_ID);
			String author = getParameter(AUTHOR);
			String comment = getParameter(COMMENT);

			Result result = new Result();
			boolean isValidDrip = dripId != null && dripId.length() > 0;
			boolean isValidAuthor = author != null && author.length() > 0;
			boolean isValidComment = comment != null && comment.length() > 0;
			if(isValidAuthor && isValidDrip && isValidComment){
				Drips drip = DripsManager.getInstance().getDripById(dripId);
				if(drip != null){
					Users authorUser = UsersManager.getInstance().getUser(author);
					if(authorUser != null){
						List<Reply> replies = ReplyManager.getInstance().getReplyListByDripIdAndAuthor(dripId, author);
						if(replies.size() <= MAX_REPLY_COUNT_PER_DRIP){
							Reply reply = new Reply();
							reply.setDripid(Integer.parseInt(dripId));
							reply.setCreatedate(System.currentTimeMillis());
							reply.setComment(comment);
							reply.setAuthor(author);
							reply.setDrip(drip);
							Users.removePrivateInfo(drip.getUser());
							DBMessage dbMsg = ReplyManager.getInstance().insertObject(reply);
							if(dbMsg.getRow() > 0){
								result.setErrorCode(Result.ERROR_CODE_OK);
								result.setResult(reply);	
							}else{
								result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
								result.setMessage(dbMsg.getMessage());
								result.setResult(dbMsg);
							}
						}else{
							result.setErrorCode(ERROR_CODE_MAX_REPLY_USER);
							result.setMessage("한 개의 드립에 6개 이상 댓글을 달 수 있습니다.");
						}
					}else{
						result.setErrorCode(ERROR_CODE_INVALID_AUTHOR);
						result.setMessage("작성자가 존재하지 않는 회원입니다.");
					}
				}else{
					result.setErrorCode(ERROR_CODE_INVALID_DRIP);
					result.setMessage("존재하지 않는 드입니다.");
				}
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				result.setMessage("잘못된 정보가 전될되었습니다.");
			}
			writeObject(result);
		}
	}

	public void deleteReply(){
		if(checkRequestValidation()){
			String id = getParameter(ID);
			Result result = new Result();
			Reply reply = new Reply();
			reply.setId(Integer.valueOf(id));
			DBMessage dbMsg = ReplyManager.getInstance().removeObject(reply);
			if(dbMsg.getRow() > 0){
				result.setErrorCode(Result.ERROR_CODE_OK);
			}else{
				result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
				result.setMessage(dbMsg.getMessage());
			}
			writeObject(result);
		}
	}

}
