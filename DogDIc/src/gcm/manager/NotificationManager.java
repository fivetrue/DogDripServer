package gcm.manager;

import fivetrue.database.tables.Drips;
import gcm.data.MessageData;
import gcm.sender.SendManager;

public class NotificationManager {
	
	private static final int LIKE_DRIP_ID = 0x01;
	
	private static NotificationManager sInstance = null;
	
	public static NotificationManager getInstance(){
		if(sInstance == null){
			sInstance = new NotificationManager();
		}
		return sInstance;
	}
	
	private NotificationManager(){
		
	}
	
	public void sendLikeNotification(Drips drip, String gcmId){
		if(drip != null && gcmId != null){
			MessageData data = new MessageData();
			data.setId(LIKE_DRIP_ID);
			data.setCreatedate(System.currentTimeMillis());
			data.setTitle("추천 알림");
			data.setMessage("당신의 드립이 누군가에게 인정 되었습니다.");
			data.setUri(drip.toString());
			SendManager.getInstance().sendMessage(gcmId, data);
		}
	}

}
