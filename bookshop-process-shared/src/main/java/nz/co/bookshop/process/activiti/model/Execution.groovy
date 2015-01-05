package nz.co.bookshop.process.activiti.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["id","url","parentId","processInstanceId","tenantId"])
class Execution {

	String id
	String url
	String parentId
	String parentUrl
	String processInstanceId
	String processInstanceUrl
	boolean suspended
	String activityId
	String tenantId
}
