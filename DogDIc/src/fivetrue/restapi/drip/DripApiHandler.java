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
		long start = System.currentTimeMillis();
		String id = getParameter(ID);
		String drip = getParameter(DRIP);
		String author = getParameter(AUTHOR);

		List<Drips> drips = DripsManager.getInstance().getAllDrips();

		if(drips != null){
			long end = System.currentTimeMillis();
			Result result = Result.makeOkResult();
			result.setResponseTime(end);
			result.setResult(drips);
			result.setDuration(end - start);
			writeObject(result);
		}
	}
	
	public void putDrip(){
		
		long start = System.currentTimeMillis();
		
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
		result.setMessae(errorMessage);
		if(errorCode == result.ERROR_CODE_OK){
			Drips drips = new Drips();
			drips.setCreatedate(System.currentTimeMillis());
			drips.setDrip(drip);
			drips.setAuthor(author);
			drips.setHeartcount(0);
			DBMessage message = DripsManager.getInstance().insertObject(drips);
			result.setResult(message);
		}
		long end = System.currentTimeMillis();
		result.setResponseTime(end);
		result.setDuration(end - start);
		writeObject(result);
	}

}
