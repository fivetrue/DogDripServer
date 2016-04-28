package fivetrue;

public class Constants {
	
	public static final String DB_SERVER = "localhost";
	public static final String DB_NAME = "drip";
	public static final String DB_ID = "root";
	public static final String DB_PASS = "qudwja123";

	public static final String KEY_APP_ID = "Application-ID";
	public static final String KEY_APP_KEY = "Application-Key";

	public static final String APP_ID = "com.organic.dogdrip";
	public static final String APP_KEY = "com.fivetrue";
	
	public static final String APP_LATEST_VERSION = "39";
	public static final String APP_VERSION_NAME = "2016031901";
	public static final String APP_MARKET_URL = "http://play.google.com/";
	
	public static final String DEFAULT_USER_IMAGE_THUNBNAIL = "http://52.76.24.8:8080/dogdic/resource/images/default_user.png";
	
	public static class GCM{
		public static String GCM_KEY = "AIzaSyA-k1Qnh4jYpxVvYyX9KF3YCHQPB4cWx8o";
		public static String GCM_SENDER_ID = "110238963961";
	}
	
	public static class AWS{
		/*
	     * You should replace these values with your own. See the README for details
	     * on what to fill in.
	     */
	    public static final String COGNITO_POOL_ID = "ap-northeast-1:2d84bbbe-e8aa-40bb-9cef-00963a7c2847";

	    /*
	     * Note, you must first create a bucket using the S3 console before running
	     * the sample (https://console.aws.amazon.com/s3/). After creating a bucket,
	     * put it's name in the field below.
	     */
	    public static final String BUCKET_NAME = "fivetrue-drip";
	}
	

}
