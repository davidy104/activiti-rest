package nz.co.bookshop.process.util

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["statusCode","responseString"])
class AbstractRestClientResponse {
	int statusCode
	def responseMetaData
}
