package nz.co.bookshop.process.config

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["authUserId","authPassword","restHostUri"])
class ActivitiRestConfig {
	String authUserId
	String authPassword
	String restHostUri
}
