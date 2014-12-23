package nz.co.bookshop.process.activiti.ds.impl
import static com.google.common.base.Preconditions.checkArgument

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.OperationType
import nz.co.bookshop.process.activiti.convert.GroupConverter
import nz.co.bookshop.process.activiti.ds.GroupDS
import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.model.activiti.Group
import nz.co.bookshop.process.model.activiti.MemberShip
import nz.co.bookshop.process.util.RestClientExecuteCallback

import org.apache.commons.lang3.StringUtils

import com.google.inject.Inject
import com.google.inject.name.Named
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

class GroupDSImpl implements GroupDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	@Inject
	GroupConverter groupConverter
	
	final static String GROUP_PATH="/identity/groups/"

	@Override
	Group createGroup(final Group group) {
		checkArgument(!StringUtils.isEmpty(group.id),"group id can not be null.")
		final String groupCreateJson = groupConverter.groupToJson(group, OperationType.CREATION)
		return groupConverter.jsonToGroup(activitiRestClientAccessor.process(GROUP_PATH,ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,groupCreateJson)
			}
		}))
	}

	@Override
	Group getGroupById(final String groupId)  {
		return groupConverter.jsonToGroup(activitiRestClientAccessor.process(GROUP_PATH+groupId,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	Group getGroupByUserId(final String userId)  {
		return groupConverter.jsonToGroups(activitiRestClientAccessor.process(GROUP_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("member", userId).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		})).first()
	}

	@Override
	Page paginateGroup(final int pageOffset,final int pageSize) {
		return groupConverter.jsonToGroupPage(activitiRestClientAccessor.process(GROUP_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("start", pageOffset).queryParam("size", pageSize).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	Page paginateGroupByType(final int pageOffset,final int pageSize,final String type) {
		return groupConverter.jsonToGroupPage(activitiRestClientAccessor.process(GROUP_PATH,ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("start", pageOffset).queryParam("size", pageSize).queryParam("type", type).accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	void deleteGroup(final String groupId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		activitiRestClientAccessor.process(GROUP_PATH+groupId,ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class)
					}
				})
	}

	@Override
	MemberShip createMemberToGroup(final String groupId,final String userId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		checkArgument(!StringUtils.isEmpty(userId),"userId can not be null.")
		final String requestBody ="{\"userId\":\""+userId+"\"}"
		return groupConverter.jsonToMemberShip(activitiRestClientAccessor.process(GROUP_PATH+groupId+"/members",ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,requestBody)
			}
		}))
	}

	@Override
	void deleteMemberFromGroup(final String groupId,final String userId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		checkArgument(!StringUtils.isEmpty(userId),"userId can not be null.")
		activitiRestClientAccessor.process(GROUP_PATH+groupId+"/members"+userId,ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class)
			}
		})
	}
}
