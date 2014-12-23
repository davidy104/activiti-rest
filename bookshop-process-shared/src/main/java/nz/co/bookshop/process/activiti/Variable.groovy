package nz.co.bookshop.process.activiti

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["name","type","scope","valueUrl"])
class Variable {
	String name
	String type
	String value
	String valueUrl
	String scope
}
