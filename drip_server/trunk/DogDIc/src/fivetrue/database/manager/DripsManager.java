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
		return getDrips(null, null, null);
	}
	
	public List<Drips> getDripsByAuthor(String author){
		return getDrips(null, author, null);
	}
	
	public Drips getDripById(String id){
		Drips drip = null;
		if(id != null && id.length() > 0){
			List<Drips> drips = getDrips(id, null, null);
			if(drips != null && drips.size() > 0){
				drip = drips.get(0);
			}
		}
		return drip;
	}
	
	public List<Drips> getDrips(String id, String author, String drip){
		String where = null;
		if(id != null && id.length() > 0){
			where = "drips.id=" + id;
		}
		
		if(author != null && author.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " drips.author='" + author + "'";
		}
		
		if(drip != null && drip.length() > 0){
			if(where != null && where.length() > 0){
				where += " and";
			}else{
				where = "";
			}
			where += " drips.drip='" + drip + "'";
		}
		
		return getSelectQueryData(where);
	}
	
	public boolean checkDrip(String drip){
		boolean check = true;
		if(drip != null && drip.length() > 0){
			List<Drips> data = getSelectQueryData("drips.drip='" + drip +"'");
			if(data != null && data.size() > 0){
				check = false;
			}
		}else{
			check = false;
		}
		
		return check;
	}

}
