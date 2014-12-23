package nz.co.bookshop.process.activiti.ds.impl;

import static com.google.common.base.Preconditions.checkArgument

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.OperationType
import nz.co.bookshop.process.activiti.convert.UserConverter
import nz.co.bookshop.process.activiti.ds.UserDS
import nz.co.bookshop.process.activiti.model.User;
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
		return userConverter.jsonToUser(activitiRestClientAccessor.process(USER_PATH+userId,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	User getUserByEmail(final String email) {
		return userConverter.jsonToUsers(activitiRestClientAccessor.process(USER_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("emailLike", email).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		})).first()
	}

	@Override
	User createUser(final User addUser)  {
		checkArgument(!StringUtils.isEmpty(addUser.id),"user id can not be null.")
		final String userCreationJson = userConverter.userToJson(addUser, OperationType.CREATION)
		return userConverter.jsonToUser(activitiRestClientAccessor.process(USER_PATH,ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,userCreationJson)
			}
		}))
	}

	@Override
	User updateUser(final String userId,final User updateUser)  {
		checkArgument(!StringUtils.isEmpty(userId),"user id can not be null.")
		final String userUpdateJson = userConverter.userToJson(updateUser, OperationType.UPDATE)
		return userConverter.jsonToUser(activitiRestClientAccessor.process(USER_PATH+userId,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).put(ClientResponse.class,userUpdateJson)
			}
		}))
	}

	@Override
	void deleteUser(final String userId) {
		checkArgument(!StringUtils.isEmpty(userId),"user id can not be null.")
		activitiRestClientAccessor.process(USER_PATH+userId,ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class)
					}
				})
	}

	@Override
	Set<User> getUsersByName(final String firstName,final String lastName) {
		return userConverter.jsonToUsers(activitiRestClientAccessor.process(USER_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("firstName", firstName).queryParam("lastName", lastName).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	Page paginatingUsersByGroupId(final int pageOffset,final int pageSize,
			final String groupId) {
		return userConverter.jsonToUserPage(activitiRestClientAccessor.process(USER_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("memberOfGroup", groupId).queryParam("start", pageOffset).queryParam("size", pageSize).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	Page paginatingUsers(final int pageOffset,final int pageSize) {
		return userConverter.jsonToUserPage(activitiRestClientAccessor.process(USER_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("start", pageOffset).queryParam("size", pageSize).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
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
