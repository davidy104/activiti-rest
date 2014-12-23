package nz.co.bookshop.process.activiti.ds.impl
import static com.google.common.base.Preconditions.checkArgument
import nz.co.bookshop.process.OperationType;
import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.convert.GroupConverter
import nz.co.bookshop.process.activiti.ds.GroupDS
import nz.co.bookshop.process.activiti.model.Group
import nz.co.bookshop.process.activiti.model.GroupQueryParameter
import nz.co.bookshop.process.activiti.model.MemberShip
import nz.co.bookshop.process.model.Page

import org.apache.commons.lang3.StringUtils

import com.google.inject.Inject
import com.google.inject.name.Named

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
		return groupConverter.jsonToGroup(activitiRestClientAccessor.create(GROUP_PATH, groupCreateJson))
	}

	@Override
	Group getGroupById(final String groupId)  {
		return groupConverter.jsonToGroup(activitiRestClientAccessor.get(GROUP_PATH+groupId))
	}

	@Override
	Group getGroupByUserId(final String userId)  {
		Map<GroupQueryParameter,String> groupQueryParameters = [:]
		groupQueryParameters.put(GroupQueryParameter.member, userId)
		return groupConverter.jsonToGroups(activitiRestClientAccessor.query(GROUP_PATH, groupQueryParameters)).first()
	}

	@Override
	Page<Group> paginateGroup(final Map<GroupQueryParameter,String> groupQueryParameters,final Integer pageOffset,final Integer pageSize) {
		return groupConverter.jsonToGroupPage(activitiRestClientAccessor.paginate(GROUP_PATH,groupQueryParameters,pageOffset,pageSize))
	}


	@Override
	void deleteGroup(final String groupId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		activitiRestClientAccessor.delete(GROUP_PATH+groupId)
	}

	@Override
	MemberShip createMemberToGroup(final String groupId,final String userId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		checkArgument(!StringUtils.isEmpty(userId),"userId can not be null.")
		final String requestBody ="{\"userId\":\""+userId+"\"}"
		return groupConverter.jsonToMemberShip(activitiRestClientAccessor.create(GROUP_PATH+groupId+"/members", requestBody))
	}

	@Override
	void deleteMemberFromGroup(final String groupId,final String userId) {
		checkArgument(!StringUtils.isEmpty(groupId),"groupId can not be null.")
		checkArgument(!StringUtils.isEmpty(userId),"userId can not be null.")
		activitiRestClientAccessor.delete(GROUP_PATH+groupId+"/members"+userId)
	}
}
