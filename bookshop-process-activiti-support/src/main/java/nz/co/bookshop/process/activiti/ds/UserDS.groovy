package nz.co.bookshop.process.activiti.ds;

import nz.co.bookshop.process.activiti.model.User
import nz.co.bookshop.process.activiti.model.UserQueryParameter
import nz.co.bookshop.process.model.Page


interface UserDS {
	User getUserById(String userId)
	Set<User> getUserByEmail(String email)
	Set<User> getUsersByName(String firstName,String lastName)
	User createUser(User addUser) throws Exception
	User updateUser(String userId, User updateUser) throws Exception
	void deleteUser(String userId) throws Exception
	Page paginatingUsers(Map<UserQueryParameter, String> userQueryParameters,Integer pageOffset,Integer pageSize)
	void updateUsersPicture(String userId, InputStream picStream) throws Exception
}
