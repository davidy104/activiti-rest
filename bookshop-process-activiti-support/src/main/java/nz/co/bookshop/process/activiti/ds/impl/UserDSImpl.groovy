package nz.co.bookshop.process.activiti.ds.impl;

import static com.google.common.base.Preconditions.checkArgument

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.OperationType;
import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.convert.UserConverter
import nz.co.bookshop.process.activiti.ds.UserDS
import nz.co.bookshop.process.activiti.model.User
import nz.co.bookshop.process.activiti.model.UserQueryParameter
import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.util.RestClientExecuteCallback

import org.apache.commons.lang3.StringUtils

import com.google.inject.Inject
import com.google.inject.name.Named
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.multipart.FormDataMultiPart
import com.sun.jersey.multipart.file.StreamDataBodyPart

class UserDSImpl implements UserDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	final static String USER_PATH="/identity/users/"

	@Inject
	UserConverter userConverter

	@Override
	User getUserById(final String userId) {
		return userConverter.jsonToUser(activitiRestClientAccessor.get(USER_PATH+userId))
	}

	@Override
	Set<User> getUserByEmail(final String email) {
		Map<UserQueryParameter, String> userQueryParameters = [:]
		userQueryParameters.put(UserQueryParameter.emailLike, email)
		return userConverter.jsonToUsers(activitiRestClientAccessor.query(USER_PATH, userQueryParameters))
	}

	@Override
	User createUser(final User addUser)  {
		checkArgument(!StringUtils.isEmpty(addUser.id),"user id can not be null.")
		final String userCreationJson = userConverter.userToJson(addUser, OperationType.CREATION)
		return userConverter.jsonToUser(activitiRestClientAccessor.create(USER_PATH, userCreationJson))
	}

	@Override
	User updateUser(final String userId,final User updateUser)  {
		checkArgument(!StringUtils.isEmpty(userId),"user id can not be null.")
		final String userUpdateJson = userConverter.userToJson(updateUser, OperationType.UPDATE)
		return userConverter.jsonToUser(activitiRestClientAccessor.update(USER_PATH+userId, userUpdateJson))
	}

	@Override
	void deleteUser(final String userId) {
		checkArgument(!StringUtils.isEmpty(userId),"user id can not be null.")
		activitiRestClientAccessor.delete(USER_PATH+userId)
	}

	@Override
	Set<User> getUsersByName(final String firstName,final String lastName) {
		Map<UserQueryParameter, String> userQueryParameters = [:]
		userQueryParameters.put(UserQueryParameter.firstName, firstName)
		userQueryParameters.put(UserQueryParameter.lastName, lastName)
		return userConverter.jsonToUsers(activitiRestClientAccessor.query(USER_PATH, userQueryParameters))
	}

	@Override
	Page paginatingUsers(final Map<UserQueryParameter, String> userQueryParameters,final Integer pageOffset,final Integer pageSize) {
		return userConverter.jsonToUserPage(activitiRestClientAccessor.paginate(USER_PATH,userQueryParameters,pageOffset,pageSize))
	}

	@Override
	void updateUsersPicture(final String userId,final InputStream picStream){
		checkArgument(!StringUtils.isEmpty(userId),"user id can not be null.")
		final FormDataMultiPart multiPart = new FormDataMultiPart()
		multiPart.bodyPart(new StreamDataBodyPart(userId+".jpg", picStream))
		activitiRestClientAccessor.process(USER_PATH+userId+"/picture",ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.type(MediaType.MULTIPART_FORM_DATA)
								.accept(MediaType.APPLICATION_JSON).put(ClientResponse.class,multiPart)
					}
				})
	}
}
