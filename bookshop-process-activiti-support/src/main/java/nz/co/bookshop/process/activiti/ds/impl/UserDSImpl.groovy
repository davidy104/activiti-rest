package nz.co.bookshop.process.activiti.ds.impl;

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.ds.UserDS
import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.util.RestClientExecuteCallback

import com.amazonaws.services.identitymanagement.model.User
import com.google.inject.Inject
import com.google.inject.name.Named
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

class UserDSImpl implements UserDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	final static String USER_PATH="/identity/users/"

	@Override
	User getUserById(final String userId) {
		String jsonResponse = activitiRestClientAccessor.process(USER_PATH+userId,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
					}
				})
		return null
	}

	@Override
	User getUserByEmail(final String email) {
		return null
	}

	@Override
	User createUser(User addUser)  {
		return null
	}

	@Override
	public User updateUser(String userId, User updateUser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<User> getUsersByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page paginatingUsersByGroupId(int pageOffset, int pageSize,
			String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page paginatingUsers(int pageOffset, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUsersPicture(String userId, InputStream pictureStream)
	throws Exception {
		// TODO Auto-generated method stub

	}
}
