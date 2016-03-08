package fivetrue.restapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public abstract class BaseApiHandler {
	
	private HttpServletRequest mRequest = null;
	private HttpServletResponse mResponse = null;
	
	private Gson mGson = null;
	
	
	public BaseApiHandler(HttpServletRequest request, HttpServletResponse response){
		mRequest = request;
		mResponse = response;
		mGson = new Gson();
		try {
			mRequest.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
