package fivetrue.restapi.user;


import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fivetrue.db.DBMessage;

import fivetrue.Constants;
import fivetrue.database.manager.GcmManager;
import fivetrue.database.manager.UsersManager;
import fivetrue.database.tables.Gcm;
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
	public static final String GCM = "gcm";
	public static final String EXTERNAL = "external";
	public static final String LAST_CONN = "last_conn"; 
	public static final String AUTO_LOGIN = "auto_login";

	public static final int ERROR_CODE_DUPLICATED_EMAIL = 2000;
	public static final int ERROR_CODE_DUPLICATED_NICKNAME = 2001;
	public static final int ERROR_CODE_INVALID_LOGIN_INFO = 2002;


	public UserApiHandler(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}

	public void registerUser(){
		if(checkRequestValidation()){
			String email = getParameter(EMAIL);
			String password = getParameter(PASSWORD);
			String nickname = getParameter(NICKNAME);
			boolean validation = Boolean.valueOf(getParameter(EXTERNAL));

			boolean isValidEmail = UsersManager.isValidEmail(validation, email);
			boolean isValidPassword = UsersManager.isValidPassword(validation, password);
			boolean isValidNickname = nickname != null && nickname.length() > 0;

			Result result = new Result();

			if(isValidEmail && isValidPassword && isValidNickname){
				boolean checkEmail = UsersManager.getInstance().checkUserEmail(validation, email);
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
				user.setImageurl(Constants.DEFAULT_USER_IMAGE_THUNBNAIL);
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
			String gcm = getParameter(GCM);
			String autoLogin = getParameter(AUTO_LOGIN);
			String lastConn = getParameter(LAST_CONN);
			boolean validation = Boolean.valueOf(getParameter(EXTERNAL));

			boolean isValidEmail = UsersManager.isValidEmail(validation, email);
			boolean isValidPassword = UsersManager.isValidPassword(validation, password);
			boolean isValidGcm = gcm != null && gcm.length() > 0;
			boolean bAUto = autoLogin != null && Boolean.parseBoolean(autoLogin);
			long lastConnection = lastConn != null && lastConn.length() > 0 ? Long.parseLong(lastConn) : 0;

			Result result = new Result();

			/**
			 * 자동 로그인.
			 */
			if(bAUto){
				if(isValidEmail && lastConnection > 0 && isValidGcm){
					Users user = UsersManager.getInstance().getUser(email);
					if(user != null){
						boolean checkLastConn = user.getLastconn() == lastConnection;
						if(checkLastConn){
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
							user.setGcm(gcm);
							DBMessage dbMsg = UsersManager.getInstance().updateObject(user);
							if(dbMsg.getRow() > 0){
								result.setMessage(Result.OK_MESSAGE);
								result.setErrorCode(Result.ERROR_CODE_OK);
								user.setPassword(null);
								user.setGcm(null);
								result.setResult(user);	
							}else{
								result.setMessage(dbMsg.getMessage());
								result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
								result.setResult(dbMsg);
							}

							Gcm gcmInfo = GcmManager.getInstance().getGcmByGcm(gcm);
							if(gcmInfo != null){
								gcmInfo.setEmail(email);
								GcmManager.getInstance().updateObject(gcmInfo);
							}
						}else{
							result.setErrorCode(ERROR_CODE_INVALID_LOGIN_INFO);
							result.setMessage("접속 정보가 올바르지 않습니다.");
						}
					}else{
						result.setMessage("아이디가 올바르지 않습니다.");
						result.setErrorCode(ERROR_CODE_INVALID_LOGIN_INFO);
					}

				}else{
					result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
					String message = null;
					if(!isValidEmail && !isValidGcm){
						message = "이메일 형식이 올바르지 않습니다.  GCM 정보가 없습니다.";
					}else if(lastConnection <= 0){
						message = "접속 정보가 올바르지 않습니다.";
					}else if(!isValidGcm){
						message = "GCM 정보가 없습니다.";
					}
					result.setMessage(message);
				}
			}
			/**
			 * 수동 로그인.
			 */
			else{
				if(isValidEmail && isValidPassword && isValidGcm){
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
						user.setGcm(gcm);
						DBMessage dbMsg = UsersManager.getInstance().updateObject(user);
						if(dbMsg.getRow() > 0){
							result.setMessage(Result.OK_MESSAGE);
							result.setErrorCode(Result.ERROR_CODE_OK);
							user.setPassword(null);
							user.setGcm(null);
							result.setResult(user);	
						}else{
							result.setMessage(dbMsg.getMessage());
							result.setErrorCode(Result.ERROR_CODE_DB_ERROR);
							result.setResult(dbMsg);
						}

						Gcm gcmInfo = GcmManager.getInstance().getGcmByGcm(gcm);
						if(gcmInfo != null){
							gcmInfo.setEmail(email);
							GcmManager.getInstance().updateObject(gcmInfo);
						}
					}else{
						result.setMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
						result.setErrorCode(ERROR_CODE_INVALID_LOGIN_INFO);
					}
				}else{
					result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
					String message = null;

					if(!isValidEmail && !isValidPassword && !isValidGcm){
						message = "이메일 형식이 올바르지 않습니다. 비밀번호는 8자리 이상 16자리 미만입니다. 닉네임이 올바르지 않습니다. GCM 정보가 없습니다.";
					}else if(!isValidEmail){
						message = "이메일 형식이 올바르지 않습니다.";
					}else if(!isValidPassword){
						message = "비밀번호는 8자리 이상 16자리 미만입니다.";
					}else if(!isValidGcm){
						message = "GCM 정보가 없습니다.";
					}
					result.setMessage(message);
				}
			}
			result.makeResponseTime();
			writeObject(result);
		}
	}


	public void registerGcm(){
		if(checkRequestValidation()){
			String id = getParameter(GCM);
			String device = getParameter(DEVICE);
			boolean isValidGcm = id != null && id.length() > 0;
			boolean isValidDevice = device != null && device.length() > 0;
			Result result = new Result();

			if(isValidGcm && isValidDevice){
				Gcm gcm = GcmManager.getInstance().getGcmByGcm(id);
				if(gcm == null){
					gcm = new Gcm();
					gcm.setGcm(id);
					gcm.setDevice(device);
					DBMessage msg = GcmManager.getInstance().insertObject(gcm);
					result.setErrorCode(Result.ERROR_CODE_OK);
					result.setMessage(Result.OK_MESSAGE);
					result.setResult(msg);
				}else{
					gcm.setDevice(device);
					GcmManager.getInstance().updateObject(gcm);
					DBMessage msg = GcmManager.getInstance().updateObject(gcm);
					result.setErrorCode(Result.ERROR_CODE_OK);
					result.setMessage(Result.OK_MESSAGE);
					result.setResult(msg);
				}
			}else{
				result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
				result.setMessage("Gcm Id 또는 Device 정보가 올바르지 않습니다.");
			}

			result.makeResponseTime();
			writeObject(result);
		}
	}

}
