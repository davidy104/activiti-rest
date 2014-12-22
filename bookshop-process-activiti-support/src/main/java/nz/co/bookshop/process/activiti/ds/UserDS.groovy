package nz.co.bookshop.process.activiti.ds;

import nz.co.bookshop.process.model.Page

import com.amazonaws.services.identitymanagement.model.User

public interface UserDS {
	User getUserById(String userId)
	User getUserByEmail(String email)
	User createUser(User addUser) throws Exception
	User updateUser(String userId, User updateUser) throws Exception
	void deleteUser(String userId) throws Exception
	Set<User> getUsersByName(String firstName,String lastName)
	Page paginatingUsersByGroupId(int pageOffset,int pageSize,String groupId)
	Page paginatingUsers(int pageOffset,int pageSize)
	void updateUsersPicture(String userId, InputStream pictureStream) throws Exception
}
