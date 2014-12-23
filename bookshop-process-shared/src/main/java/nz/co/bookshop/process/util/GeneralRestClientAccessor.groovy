package nz.co.bookshop.process.util
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static nz.co.bookshop.process.util.JerseyClientUtil.getResponsePayload
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.AbstractEnumQueryParameter
import nz.co.bookshop.process.ConflictException
import nz.co.bookshop.process.NotFoundException

import org.apache.commons.lang3.StringUtils

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

@Slf4j
class GeneralRestClientAccessor {

	protected Client jerseyClient
	protected String hostUri

	public GeneralRestClientAccessor(final Client jerseyClient,final String hostUri) {
		this.jerseyClient = jerseyClient;
		this.hostUri = hostUri;
	}

	protected String process(String path,final Map<? extends AbstractEnumQueryParameter,String> emunQueryParameters, final int expectedStatus,final RestClientExecuteCallback restClientCallback,final RestClientCustomErrorHandler... customErrorHandlers)  {
		checkArgument(!StringUtils.isEmpty(path),"path can not be null")
		checkArgument(restClientCallback != null,"restClientCallback can not be null")
		WebResource webResource = jerseyClient.resource(hostUri)
		if(emunQueryParameters){
			emunQueryParameters.each{ k,v->
				webResource = webResource.queryParam(k.name(), v)
			}
		}
		if(path.lastIndexOf('/') == path.length()-1){
			path = path.substring(0, path.lastIndexOf('/'))
		}
		webResource = webResource.path(path)
		final ClientResponse clientResponse = restClientCallback.execute(webResource)
		final int statusCode = clientResponse.getStatus()
		final String respStr = getResponsePayload(clientResponse)
		if(statusCode != expectedStatus){
			this.doErrorHandle(statusCode, respStr, customErrorHandlers)
		}
		return respStr
	}

	protected String process(final String path,final int expectedStatus,final RestClientExecuteCallback restClientCallback,final RestClientCustomErrorHandler... customErrorHandlers)  {
		checkArgument(!StringUtils.isEmpty(path),"path can not be null")
		checkArgument(restClientCallback != null,"restClientCallback can not be null")
		WebResource webResource = jerseyClient.resource(hostUri).path(path)
		final ClientResponse clientResponse = restClientCallback.execute(webResource)
		final int statusCode = clientResponse.getStatus()
		final String respStr = getResponsePayload(clientResponse)
		if(statusCode != expectedStatus){
			this.doErrorHandle(statusCode, respStr, customErrorHandlers)
		}
		return respStr
	}

	void doErrorHandle(final int statusCode,final String responseString,final RestClientCustomErrorHandler... customErrorHandlers){
		if(customErrorHandlers){
			customErrorHandlers.each {
				it.handle(statusCode, responseString)
			}
		} else {
			switch (statusCode) {
				case ClientResponse.Status.NOT_FOUND.code:
					throw new NotFoundException(responseString)
					break
				case ClientResponse.Status.CONFLICT.code:
					throw new ConflictException(responseString)
					break
				default:
					throw new IllegalStateException(responseString)
					break
			}
		}
	}
}
