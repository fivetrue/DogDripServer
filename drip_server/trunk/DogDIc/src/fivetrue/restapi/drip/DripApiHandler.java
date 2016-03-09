package fivetrue.restapi.drip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fivetrue.db.DBMessage;

import fivetrue.database.manager.DripsManager;
import fivetrue.database.tables.Drips;
import fivetrue.restapi.BaseApiHandler;
import fivetrue.restapi.Result;

public class DripApiHandler extends BaseApiHandler{


	public static final String ID = "id";
	public static final String DRIP = "drip";
	public static final String AUTHOR = "author";

	public DripApiHandler(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		// TODO Auto-generated constructor stub
	}


	public void readDrips(){
		String id = getParameter(ID);
		String drip = getParameter(DRIP);
		String author = getParameter(AUTHOR);

		List<Drips> drips = DripsManager.getInstance().getAllDrips();

		if(drips != null){
			Result result = Result.makeOkResult();
			result.setResult(drips);
			result.makeResponseTime();
			writeObject(result);
		}
	}
	
	public void putDrip(){
		
		String drip = getParameter(DRIP);
		String author = getParameter(AUTHOR);
		
		Result result = new Result();
		int errorCode = Result.ERROR_CODE_OK;
		String errorMessage = null;
		if(drip == null || author == null || drip.length() == 0 || author.length() == 0){
			errorCode = Result.ERROR_CODE_REQUEST_ERROR;
			if((drip == null && author == null) || (drip.length() == 0 && author.length() == 0)){
				errorMessage = "드립과 작성자가 입력되지 않았습니다."; 
			}else if(drip == null || drip.length() == 0){
				errorMessage = "드립이 입력되지 않았습니다.";
			}else if(author == null || author.length() == 0){
				errorMessage = "작성자가 입력되지 않았습니다.";
			}else{
				errorMessage = "비정상적인 요청입니다.";
			}
		}else{
			errorMessage = Result.OK_MESSAGE;
		}
		
		result.setErrorCode(errorCode);
		result.setMessage(errorMessage);
		if(errorCode == result.ERROR_CODE_OK){
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
			
		}
		result.makeResponseTime();
		writeObject(result);
	}

}
