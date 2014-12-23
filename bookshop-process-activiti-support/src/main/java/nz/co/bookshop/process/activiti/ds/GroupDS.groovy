package nz.co.bookshop.process.activiti.ds

import nz.co.bookshop.process.model.Page
import nz.co.bookshop.process.model.activiti.Group
import nz.co.bookshop.process.model.activiti.MemberShip

interface GroupDS {
	Group createGroup(Group group) throws Exception
	Group getGroupById(String groupId)throws Exception
	Group getGroupByUserId(String userId)throws Exception
	Page paginateGroup(int pageOffset,int pageSize)
	Page paginateGroupByType(int pageOffset,int pageSize,String type)
	void deleteGroup(String groupId) throws Exception
	MemberShip createMemberToGroup(String groupId, String userId) throws Exception
	void deleteMemberFromGroup(String groupId, String userId) throws Exception
}