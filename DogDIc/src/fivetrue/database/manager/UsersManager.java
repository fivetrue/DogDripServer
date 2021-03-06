package fivetrue.database.manager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fivetrue.db.manager.DatabaseManagerImpl;

import fivetrue.Constants;
import fivetrue.database.tables.Users;

public class UsersManager extends DatabaseManagerImpl<Users>{

	private static UsersManager sInstance = null;
    
	 
	private static final String[] DEFAULT_SELECTION = {
			"userid",
			"nickname",
			"joindate",
			"lastconn",
	};

	public static UsersManager getInstance(){
		if(sInstance == null){
			sInstance = new UsersManager();
		}
		return sInstance;
	}

	protected UsersManager() {
		super(Constants.DB_SERVER, Constants.DB_NAME, Constants.DB_ID, Constants.DB_PASS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<? extends Users> getDatabaseObjectClass() {
		// TODO Auto-generated method stub
		return Users.class;
	}
	
	public boolean isValidUser(String email){
		boolean b = false;
		List<Users> data = getSelectQueryData(null, "userid='" + email +"'");
		if(data != null && data.size() > 0){
			b = true;
		}
		return b;
	}

	public Users checkUserEmail(boolean validation, String email){
		Users users = null;
		if(isValidEmail(validation, email)){
			List<Users> data = getSelectQueryData(null, "userid='" + email +"'");
			if(data != null && data.size() > 0){
				users = data.get(0);
			}
		}
		return users;
	}
	
	public boolean checkUserNickname(String nickname){
		boolean b = true;
		if(nickname != null && nickname.length() > 0){
			List<Users> data = getSelectQueryData(null, "nickname='" + nickname +"'");
			if(data != null && data.size() > 0){
				b = false;
			}
		}else{
			b = false;
		}
		return b;
	}
	
	public Users checkUser(String email, String password){
		Users user = null;
		if(email != null && password != null){
			
			List<Users> data = getSelectQueryData(null, "userid='" + email +"' and password=password('" + password +"')");
			if(data != null && data.size() > 0){
				user = data.get(0);
			}
		}
		return user;
	}
	
	public Users getUser(String email){
		Users user = null;
		if(email != null){
			List<Users> data = getSelectQueryData(null, "userid='" + email +"'");
			if(data != null && data.size() > 0){
				user = data.get(0);
			}
		}
		return user;
	}

	public static boolean isValidEmail(boolean validation, String email) {
		if(validation){
			boolean err = false;
			if(email != null && email.length() > 0){
				String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";   
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(email);
				if(m.matches()) {
					err = true; 
				}
			}
			return err;
		}else{
			return true;
		}
	}
	
	public static boolean isValidPassword(boolean validation, String password){
		if(validation){
			boolean err = false;
			if(password != null && password.length() >= 8 && password.length() <= 16){
				err = true;
			}
			return err;
		}else{
			return true;
		}
	}

}
