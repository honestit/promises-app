package honestit.projects.promises.simple.friends;

public interface FriendService {
	
	MakeFriendResponse makeFriend(MakeFriendRequest request);
	
	CheckFriendResponse checkFriend(CheckFriendRequest request);
}
