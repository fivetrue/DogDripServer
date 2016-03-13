package fivetrue.restapi.drip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fivetrue.db.DBMessage;

import fivetrue.database.manager.DripsManager;
import fivetrue.database.manager.UsersManager;
import fivetrue.database.tables.Drips;
import fivetrue.database.tables.Users;
import fivetrue.restapi.BaseApiHandler;
import fivetrue.restapi.Result;

public class DripApiHandler extends BaseApiHandler{


	public static final String ID = "id";
	public static final String DRIP = "drip";
	public static final String AUTHOR = "author";
	public static final String USER = "user";

	public static final int ERROR_CODE_INVALID_AUTHOR = 1000;
	public static final int ERROR_CODE_DUPLICATED_DRIP = 1001;
	

	public DripApiHandler(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		// TODO Auto-generated constructor stub
	}

	public void likeDrip(){
		if(checkRequestValidation()){
			String id = getParameter(ID);
			String author = getParameter(AUTHOR);
			String user = getParameter(USER);
			
			boolean isValidId = id != null && id.length() > 0;
			boolean isValidAuthor = author != null && author.length() > 0;
			boolean isValidUser = user != null && user.length() > 0;
			Result result = new Result();
			if(isValidAuthor && isValidId && isValidUser){
				Users authorUser = UsersManager.getInstance().getUser(author);
				Users requestUser = UsersManager.getInstance().getUser(user);
				
				
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				result.setMessage("작성자 계정 또는 유저 계정 또는 드립 ID가 올바르지 않습니다.");
			}
			result.makeResponseTime();
			writeObject(result);
		}
	}

	public void readDrips(){
		if(checkRequestValidation()){
			String id = getParameter(ID);
			String drip = getParameter(DRIP);
			String author = getParameter(AUTHOR);
			
			List<Drips> drips = DripsManager.getInstance().getDrips(id, author, drip);
			Result result = new Result();
			result.setErrorCode(Result.ERROR_CODE_OK);
			result.setResult(drips);
			result.makeResponseTime();
			writeObject(result);
		}
	}
	
	public void putDrip(){
		if(checkRequestValidation()){
			String drip = getParameter(DRIP);
			String author = getParameter(AUTHOR);
			
			Result result = new Result();
			
			boolean isValidDrip = drip != null && drip.length() > 0;
			boolean isValidAuthor = author != null && author.length() > 0;
			
			if(isValidDrip && isValidAuthor){
				
				/**
				 * 작성자가회원인지 체크.
				 */
				if(UsersManager.getInstance().isValidUser(author)){
					/**
					 * 중복된 드립인 체크.
					 */
					if(DripsManager.getInstance().checkDrip(drip)){
						Drips drips = new Drips();
						drips.setCreatedate(System.currentTimeMillis());
						drips.setDrip(drip);
						drips.setAuthor(author);
						drips.setHeartcount(0);
						DBMessage dbMsg = DripsManager.getInstance().insertObject(drips);
						if(dbMsg.getRow() > 0){
							result.setErrorCode(Result.ERROR_CODE_OK);
							result.setResult(drips);		
						}else{
							result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
							result.setMessage(dbMsg.getMessage());
							result.setResult(dbMsg);
						}
					}else{
						result.setErrorCode(ERROR_CODE_DUPLICATED_DRIP);
						result.setMessage("중복된 드립입니다.");
					}
				}else{
					result.setErrorCode(ERROR_CODE_INVALID_AUTHOR);
					result.setMessage("작성자가 존재하지 않는 회원입니다.");
				}
				
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				if(!isValidAuthor && !isValidDrip){
					result.setMessage("드립과 작성자가 입력되지 않았습니다.");
				}else if(!isValidAuthor){
					result.setMessage("작성자가 입력되지 않았습니다.");
				}else if(!isValidDrip){
					result.setMessage("드립이 입력되지 않았습니다.");
				}
			}
			result.makeResponseTime();
			writeObject(result);
		}
	}

}
