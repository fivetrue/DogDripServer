package fivetrue.restapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fivetrue.Constants;

public abstract class BaseApiHandler {
	
	private HttpServletRequest mRequest = null;
	private HttpServletResponse mResponse = null;
	private ServletContext mContext = null;
	
	private Gson mGson = null;
	
	private SimpleDateFormat mSdf = null;
	
	
	public BaseApiHandler(ServletContext context, HttpServletRequest request, HttpServletResponse response){
		mContext = context;
		mRequest = request;
		mResponse = response;
		mGson = new Gson();
		mSdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		try {
			mRequest.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected boolean checkRequestValidation(){
		String appId = mRequest.getHeader(Constants.KEY_APP_ID);
		String appKey = mRequest.getHeader(Constants.KEY_APP_KEY);
		Date date = new Date(System.currentTimeMillis());
		String log = mSdf.format(date) + " / RemoteAddr : (" + mRequest.getRemoteAddr()  + ") / "
				+"Headers : (" + "ContentType : " + mRequest.getContentType() + ", "
				+ Constants.KEY_APP_ID + " : " + appId + ", "
				+ Constants.KEY_APP_KEY + " : " + appKey + ") / ";
		log += "Path : (" + mRequest.getServletPath() + ") / "; 		
		log += "Class : (" + getClass().getName() + ") / "; 		
		log += "Parameters : (";
		for(String key : mRequest.getParameterMap().keySet()){
			log += key + " : " + mRequest.getParameter(key) + ", ";
		}
		mContext.log(log);
//		System.out.println(log );
		boolean b = appId != null && appId.equals(Constants.APP_ID) && appKey != null && appKey.equals(Constants.APP_KEY);
		if(!b){
			Result result = new Result();
			result.setErrorCode(Result.ERROR_CODE_REQUEST_ERROR);
			result.setMessage("Invalid header values");
			result.makeResponseTime();
			writeObject(result);
		}
		return b;
	}
	
	protected Gson getGson(){
		return mGson;
	}
	
	protected String getParameter(String key){
		String param = null;
		if(mRequest != null){
			param = mRequest.getParameter(key);
		}
		return param;
	}
	
	protected void writeObject(Object obj){
		if(obj != null && mResponse != null){
			try {
				mResponse.getWriter().println(mGson.toJson(obj));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public HttpServletRequest getRequest(){
		return mRequest;
	}
}
