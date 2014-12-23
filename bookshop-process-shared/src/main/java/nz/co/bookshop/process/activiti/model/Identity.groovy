package nz.co.bookshop.process.activiti.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import nz.co.bookshop.process.activiti.IdentityType

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["url","user","group","type"])
class Identity {
	String url
	String user
	String group
	IdentityType type = IdentityType.candidate
}
