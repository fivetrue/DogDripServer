package tables;

import java.util.List;

/**
 * 
 * @author kwonojin
 * UserStatus 는 users table에 json 형태로 들어간다.
 */
public class UserStatus {
	
	private List<String> dripIds = null;
	private List<String> follower = null;
	private List<String> following = null;
	public List<String> getDripIds() {
		return dripIds;
	}
	public void setDripIds(List<String> dripIds) {
		this.dripIds = dripIds;
	}
	public List<String> getFollower() {
		return follower;
	}
	public void setFollower(List<String> follower) {
		this.follower = follower;
	}
	public List<String> getFollowing() {
		return following;
	}
	public void setFollowing(List<String> following) {
		this.following = following;
	}
	@Override
	public String toString() {
		return "UserStatus [dripIds=" + dripIds + ", follower=" + follower + ", following=" + following + "]";
	}

}
