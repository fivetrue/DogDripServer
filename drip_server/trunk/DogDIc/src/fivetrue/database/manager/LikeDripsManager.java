package fivetrue.database.manager;


import java.util.List;

import com.fivetrue.db.manager.DatabaseManagerImpl;

import fivetrue.Constants;
import fivetrue.database.tables.LikeDrips;

public class LikeDripsManager extends DatabaseManagerImpl<LikeDrips>{
	
	private static LikeDripsManager sInstance = null;
	
	public static LikeDripsManager getInstance(){
		if(sInstance == null){
			sInstance = new LikeDripsManager();
		}
		return sInstance;
	}

	protected LikeDripsManager() {
		super(Constants.DB_SERVER, Constants.DB_NAME, Constants.DB_ID, Constants.DB_PASS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends LikeDrips> getDatabaseObjectClass() {
		// TODO Auto-generated method stub
		return LikeDrips.class;
	}
	
	public List<LikeDrips> getDrips(String id, String dripid, String user){
		String where = null;
		if(id != null && id.length() > 0){
			where = "likeid=" + id;
		}
		
		if(dripid != null && dripid.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " dripid='" + dripid + "'";
		}
		
		if(user != null && user.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " user='" + user + "'";
		}
		
		return getSelectQueryData(null, where);
	}
}
