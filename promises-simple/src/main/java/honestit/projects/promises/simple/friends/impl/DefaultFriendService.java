package honestit.projects.promises.simple.friends.impl;

import honestit.projects.promises.simple.friends.domain.Relation;
import honestit.projects.promises.simple.users.domain.User;
import honestit.projects.promises.simple.users.domain.UserRepository;
import org.springframework.stereotype.Service;

import honestit.projects.promises.simple.friends.CheckFriendRequest;
import honestit.projects.promises.simple.friends.CheckFriendResponse;
import honestit.projects.promises.simple.friends.FriendService;
import honestit.projects.promises.simple.friends.MakeFriendRequest;
import honestit.projects.promises.simple.friends.MakeFriendResponse;
import honestit.projects.promises.simple.friends.domain.Friend;
import honestit.projects.promises.simple.friends.domain.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j @RequiredArgsConstructor
public class DefaultFriendService implements FriendService {

	private final FriendRepository friendRepository;
	private final UserRepository userRepository;

	@Override
	public MakeFriendResponse makeFriend(MakeFriendRequest request) {

		if(friendRepository.existsByNameAndOwnerUsername(request.getName(), request.getUsername())){
			throw new IllegalStateException("Friend with this name: " + request.getName() + "for this username: " +
				request.getUsername() + " already exists.");
		}

		User user = userRepository.getByUsername(request.getUsername());

		Friend friend = new Friend();
		friend.setName(request.getName());
		friend.setUsername(null);
		friend.setOwner(user);
		friend.setRelation(Relation.UNSPECIFIED);

		log.debug("Friend to save: {}", friend);
		friendRepository.save(friend);
		log.debug("Saved friend: {}", friend);

		return new MakeFriendResponse(friend.getId());
	}

	@Override
	public CheckFriendResponse checkFriend(CheckFriendRequest request) {
		return null;
	}
}
