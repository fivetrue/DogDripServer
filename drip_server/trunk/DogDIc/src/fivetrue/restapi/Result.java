package fivetrue.restapi;

public class Result {
	
	public static final int INVALID_VALUE = -1;
	
	public static final int ERROR_CODE_OK = 0;
	public static final int ERROR_CODE_REQUEST_ERROR = 400;
	
	public static final String OK_MESSAGE = "OK";
	

	private String messae = null;
	private int errorCode = INVALID_VALUE;
	private long responseTime = 0;
	private long duration = 0;
	private Object result = null;
	
	public static Result makeOkResult(){
		Result result = new Result();
		result.errorCode = ERROR_CODE_OK;
		result.messae = OK_MESSAGE;
		return result;
	}
	
	public String getMessae() {
		return messae;
	}
	public void setMessae(String messae) {
		this.messae = messae;
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
	@Override
	public String toString() {
		return "Result [messae=" + messae + ", errorCode=" + errorCode + ", responseTime=" + responseTime
				+ ", duration=" + duration + ", result=" + result + "]";
	}
}
