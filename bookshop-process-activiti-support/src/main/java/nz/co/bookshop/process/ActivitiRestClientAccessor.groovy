package nz.co.bookshop.process

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static nz.co.bookshop.process.util.JerseyClientUtil.getResponsePayload
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.config.ActivitiRestConfig
import nz.co.bookshop.process.util.AbstractRestClientResponse
import nz.co.bookshop.process.util.RestClientAccessor
import nz.co.bookshop.process.util.RestClientExecuteCallback

import org.apache.commons.lang3.StringUtils

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter

@Slf4j
class ActivitiRestClientAccessor implements RestClientAccessor {

	Client jerseyClient
	String actvitiRestHostUri
	JsonSlurper jsonSlurper

	public ActivitiRestClientAccessor(Client jerseyClient,final ActivitiRestConfig activitiRestConfig,final JsonSlurper jsonSlurper) {
		this.jerseyClient = jerseyClient
		this.jerseyClient.addFilter(new HTTPBasicAuthFilter(activitiRestConfig.authUserId, activitiRestConfig.authPassword))
		this.actvitiRestHostUri = activitiRestConfig.restHostUri
		this.jsonSlurper = jsonSlurper
	}

	@Override
	public AbstractRestClientResponse process(final String path,final RestClientExecuteCallback restClientCallback,final int expectedStatus,final String[]... queryParameters) {
		checkArgument(!StringUtils.isEmpty(path),"path can not be null")
		checkArgument(restClientCallback != null,"restClientCallback can not be null")
		WebResource webResource = jerseyClient.resource(actvitiRestHostUri).path(path)
		if(queryParameters){
			queryParameters.each {
				if(it.length == 2){
					webResource = webResource.queryParam(it[0],it[1])
				}
			}
		}
		ClientResponse clientResponse = restClientCallback.execute(webResource)
		int statusCode = clientResponse.getStatus()
		String respStr = getResponsePayload(clientResponse)
		checkState(statusCode == expectedStatus,respStr)
		def meta = jsonSlurper.parseText(respStr)
		return new AbstractRestClientResponse(statusCode:statusCode,responseMetaData:meta)
	}

}
