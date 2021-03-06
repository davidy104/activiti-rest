package nz.co.bookshop.process.activiti.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["id","url"])
class DeploymentResource implements Serializable{
	String id
	String url
	String dataUrl
	String mediaType
	String type
}
