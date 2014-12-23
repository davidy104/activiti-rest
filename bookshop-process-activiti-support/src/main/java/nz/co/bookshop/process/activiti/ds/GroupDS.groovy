package nz.co.bookshop.process.activiti.ds

import nz.co.bookshop.process.activiti.model.Group
import nz.co.bookshop.process.activiti.model.GroupQueryParameter
import nz.co.bookshop.process.activiti.model.MemberShip
import nz.co.bookshop.process.model.Page

interface GroupDS {
	Group createGroup(Group group) throws Exception
	Group getGroupById(String groupId)throws Exception
	Group getGroupByUserId(String userId)throws Exception
	Page<Group> paginateGroup(Map<GroupQueryParameter,String> groupQueryParameters, Integer pageOffset,Integer pageSize)
	void deleteGroup(String groupId) throws Exception
	MemberShip createMemberToGroup(String groupId, String userId) throws Exception
	void deleteMemberFromGroup(String groupId, String userId) throws Exception
}
