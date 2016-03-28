package gcm.sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

import fivetrue.Constants;
import gcm.data.MessageData;

public class SendManager {
	
	private Sender mSender = null;
	private Gson mGson = null;

	private static SendManager sInstance = null;

	public static SendManager getInstance(){
		if(sInstance == null){
			sInstance = new SendManager();
		}
		return sInstance;
	}
	
	private SendManager(){
		mSender = new Sender(Constants.GCM.GCM_KEY);
		mGson = new Gson();
	}
	
	public void sendMessage(String deviceId, MessageData data){
		ArrayList<String> list = new ArrayList<>();
		list.add(deviceId);
		sendMessage(list, data);
	}
	
	/**
	 * 
	 * @param deviceIds 1~1000 개의 단말만 가능.
	 * @param data
	 */
	public void sendMessage(List<String> deviceIds, MessageData data){
		Message message = new Message.Builder().
				addData("msg", mGson.toJson(data))
				.build();
		MulticastResult multiResult = null;
		try {
			multiResult = mSender.send(message, deviceIds, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(multiResult != null){
			List<Result> results = multiResult.getResults();
			for(Result result : results){
				System.out.println("GCM result  ===> " + result.toString());
			}
		}
	}
}
