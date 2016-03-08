package fivetrue.database.manager;

import java.util.List;

import com.fivetrue.db.manager.DatabaseManagerImpl;

import fivetrue.Constants;
import fivetrue.database.tables.Drips;

public class DripsManager extends DatabaseManagerImpl<Drips>{
	
	private static DripsManager sInstance = null;
	
	public static DripsManager getInstance(){
		if(sInstance == null){
			sInstance = new DripsManager();
		}
		return sInstance;
	}

	protected DripsManager() {
		super(Constants.DB_SERVER, Constants.DB_NAME, Constants.DB_ID, Constants.DB_PASS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends Drips> getDatabaseObjectClass() {
		// TODO Auto-generated method stub
		return Drips.class;
	}
	
	public List<Drips> getAllDrips(){
		return getSelectQueryData(null, null);
	}

}
