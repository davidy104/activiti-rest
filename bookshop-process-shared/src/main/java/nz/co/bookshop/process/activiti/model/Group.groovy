package nz.co.bookshop.process.activiti.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["url","id","name","type"])
class Group implements Serializable{
	String id
	String url
	String name
	String type
}
