package fivetrue.restapi.user;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fivetrue.db.DBMessage;

import fivetrue.database.manager.UsersManager;
import fivetrue.database.tables.Users;
import fivetrue.restapi.BaseApiHandler;
import fivetrue.restapi.Result;

public class UserApiHandler extends BaseApiHandler{

/**
 * 유저 errorcode 정보
 * 2000 = 중복된 이메일
 * 3000 = 중복된 닉네임
 * 400 = 파라메터 에러
 * 
 */
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";
	public static final String DEVICE = "device";
	
	public static final int ERROR_CODE_DUPLICATED_EMAIL = 2000;
	public static final int ERROR_CODE_DUPLICATED_NICKNAME = 2001;
	public static final int ERROR_CODE_INVALID_LOGIN_INFO = 2002;
	

	public UserApiHandler(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
		// TODO Auto-generated constructor stub
	}
	
	public void registerUser(){
		if(checkRequestValidation()){
			String email = getParameter(EMAIL);
			String password = getParameter(PASSWORD);
			String nickname = getParameter(NICKNAME);
			
			boolean isValidEmail = UsersManager.isValidEmail(email);
			boolean isValidPassword = UsersManager.isValidPassword(password);
			boolean isValidNickname = nickname != null && nickname.length() > 0;
			
			Result result = new Result();
			
			if(isValidEmail && isValidPassword && isValidNickname){
				boolean checkEmail = UsersManager.getInstance().checkUserEmail(email);
				if(!checkEmail){
					result.setErrorCode(ERROR_CODE_DUPLICATED_EMAIL);
					result.setMessage("중복된 이메일 입니다.");
					result.makeResponseTime();
					writeObject(result);
					return;
				}
				
				boolean checkNickname = UsersManager.getInstance().checkUserNickname(nickname);
				if(!checkNickname){
					result.setErrorCode(ERROR_CODE_DUPLICATED_NICKNAME);
					result.setMessage("중복된 닉네임 입니다.");
					result.makeResponseTime();
					writeObject(result);
					return;
				}
				
				Users user = new Users();
				user.setCreatedate(System.currentTimeMillis());
				user.setEmail(email);
				user.setNickname(nickname);
				user.setPassword(password);
				user.setPoint(0);
				DBMessage dbMsg = UsersManager.getInstance().insertObject(user);
				
				
				if(dbMsg.getRow() > 0){
					result.setMessage(Result.OK_MESSAGE);
					result.setErrorCode(Result.ERROR_CODE_OK);
					result.setResult(user);	
				}else{
					result.setMessage(dbMsg.getMessage());
					result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
					result.setResult(user);
				}
				result.makeResponseTime();
				writeObject(result);
				
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				String message = null;
				
				if(!isValidEmail && !isValidPassword && isValidNickname){
					message = "이메일 형식이 올바르지 않습니다. 비밀번호는 8자리 이상 16자리 미만입니다. 닉네임이 올바르지 않습니다.";
				}else if(!isValidEmail){
					message = "이메일 형식이 올바르지 않습니다.";
				}else if(!isValidPassword){
					message = "비밀번호는 8자리 이상 16자리 미만입니다.";
				}else if(!isValidNickname){
					message = "닉네임이 올바르지 않습니다.";
				}
				result.setMessage(message);
				result.makeResponseTime();
				writeObject(result);
			}
		}
	}
	
	public void loginUser(){
		if(checkRequestValidation()){
			String email = getParameter(EMAIL);
			String password = getParameter(PASSWORD);
			String device = getParameter(DEVICE);
			
			boolean isValidEmail = UsersManager.isValidEmail(email);
			boolean isValidPassword = UsersManager.isValidPassword(password);
			boolean isValidDevice = device != null && device.length() > 0;
			
			Result result = new Result();
			
			if(isValidEmail && isValidPassword && isValidDevice){
				Users user = UsersManager.getInstance().checkUser(email, password);
				if(user != null){
					/**
					 * 일일 첫 방문 시 포인트 증가 로직.
					 */
					long currentConnTimestamp = System.currentTimeMillis();
					long lastDays =  TimeUnit.MILLISECONDS.toDays(user.getLastconn());
					long currentDays = TimeUnit.MILLISECONDS.toDays(currentConnTimestamp);
					
					if(currentDays > lastDays){
						user.setPoint(user.getPoint() + 1);
					}
					
					user.setLastconn(currentConnTimestamp);
					user.setDevice(device);
					DBMessage dbMsg = UsersManager.getInstance().updateObject(user);
					if(dbMsg.getRow() > 0){
						result.setMessage(Result.OK_MESSAGE);
						result.setErrorCode(Result.ERROR_CODE_OK);
						user.setPassword(null);
						user.setDevice(null);
						user.setGcm(null);
						result.setResult(user);	
					}else{
						result.setMessage(dbMsg.getMessage());
						result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
						result.setResult(dbMsg);
					}
				}else{
					result.setMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
					result.setErrorCode(ERROR_CODE_INVALID_LOGIN_INFO);
				}
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				String message = null;
				
				if(!isValidEmail && !isValidPassword && isValidDevice){
					message = "이메일 형식이 올바르지 않습니다. 비밀번호는 8자리 이상 16자리 미만입니다. 닉네임이 올바르지 않습니다. 디바이스 정보가 없습니다.";
				}else if(!isValidEmail){
					message = "이메일 형식이 올바르지 않습니다.";
				}else if(!isValidPassword){
					message = "비밀번호는 8자리 이상 16자리 미만입니다.";
				}else if(!isValidDevice){
					message = "디바이스 정보가 없습니다.";
				}
				result.setMessage(message);
			}
			result.makeResponseTime();
			writeObject(result);
		}
	}
	

}
