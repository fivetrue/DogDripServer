package fivetrue.restapi;

public class Result {
	
	public static final int INVALID_VALUE = -1;
	
	public static final int ERROR_CODE_OK = 0;
	public static final int ERROR_CODE_REQUEST_ERROR = 400;
	public static final int ERROR_CODE_DB_ERROR = 500;
	
	public static final String OK_MESSAGE = "OK";
	

	private String message = null;
	private int errorCode = INVALID_VALUE;
	private long responseTime = 0;
	private long duration = 0;
	private Object result = null;
	
	public static Result makeOkResult(){
		Result result = new Result();
		result.errorCode = ERROR_CODE_OK;
		result.message = OK_MESSAGE;
		return result;
	}
	
	public Result(){
		responseTime = System.currentTimeMillis();
	}
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	public void makeResponseTime(){
		long mill = System.currentTimeMillis();
		setDuration(mill - responseTime);
		setResponseTime(mill);
	}
	
	@Override
	public String toString() {
		return "Result [message=" + message + ", errorCode=" + errorCode + ", responseTime=" + responseTime
				+ ", duration=" + duration + ", result=" + result + "]";
	}
}
