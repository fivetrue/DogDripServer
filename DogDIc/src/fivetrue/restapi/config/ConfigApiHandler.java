package fivetrue.restapi.config;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fivetrue.Constants;
import fivetrue.restapi.BaseApiHandler;
import fivetrue.restapi.Result;

public class ConfigApiHandler extends BaseApiHandler{


	public ConfigApiHandler(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		super(context, request, response);
		// TODO Auto-generated constructor stub
	}

	public void getAppConfig(){
		if(checkRequestValidation()){
			Result result = new Result();
			result.setErrorCode(Result.ERROR_CODE_OK);
			AppConfig config = new AppConfig();
			config.appId = Constants.APP_ID;
			config.appLatestVersion = Constants.APP_LATEST_VERSION;
			config.appVersionName = Constants.APP_VERSION_NAME;
			config.appMarketUrl = Constants.APP_MARKET_URL;
			config.senderId = Constants.GCM.GCM_SENDER_ID;
			config.cognitoPoolId = Constants.AWS.COGNITO_POOL_ID;
			config.buketName = Constants.AWS.BUCKET_NAME;
			result.setResult(config);
			writeObject(result);
		}
	}
	
	public static class AppConfig{
		public String appId = null;
		public String appSercureKey = null;
		public String appLatestVersion = null;
		public String appVersionName = null;
		public String appMarketUrl = null;
		public String senderId = null;
		public String cognitoPoolId = null;
	    public String buketName = null;
	}
}
