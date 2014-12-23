package nz.co.bookshop.process.model.activiti

import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
class MemberShip implements Serializable{
	String userId
	String groupId
	String url
}
