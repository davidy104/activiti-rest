package nz.co.bookshop.process.model.activiti

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["id","url","email"])
class User {
	String id
	String firstName
	String lastName
	String url
	String email
	String password
}
