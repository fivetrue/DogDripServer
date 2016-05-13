package fivetrue.database.manager;

import java.util.List;

import com.fivetrue.db.manager.DatabaseManagerImpl;

import fivetrue.Constants;
import fivetrue.database.tables.Gcm;

public class GcmManager extends DatabaseManagerImpl<Gcm>{

	private static GcmManager sInstance = null;
    
	 

	public static GcmManager getInstance(){
		if(sInstance == null){
			sInstance = new GcmManager();
		}
		return sInstance;
	}

	protected GcmManager() {
		super(Constants.DB_SERVER, Constants.DB_NAME, Constants.DB_ID, Constants.DB_PASS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends Gcm> getDatabaseObjectClass() {
		// TODO Auto-generated method stub
		return Gcm.class;
	}
	
	public boolean isValidGcm(String gcm){
		boolean b = false;
		List<Gcm> data = getSelectQueryData(null, "gcm='" + gcm +"'");
		if(data != null && data.size() > 0){
			b = true;
		}
		return b;
	}
	
	public Gcm getGcmByEmail(String email){
		Gcm gcm = null;
		List<Gcm> data = getSelectQueryData(null, "userid='" + email +"'");
		if(data != null && data.size() > 0){
			gcm = data.get(0);
		}
		return gcm;
	}
	
	public Gcm getGcmByGcm(String id){
		Gcm gcm = null;
		List<Gcm> data = getSelectQueryData(null, "gcm='" + id +"'");
		if(data != null && data.size() > 0){
			gcm = data.get(0);
		}
		return gcm;
	}

}
