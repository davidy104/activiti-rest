package nz.co.bookshop.process.util
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static nz.co.bookshop.process.util.JerseyClientUtil.getResponsePayload
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.ConflictException
import nz.co.bookshop.process.NotFoundException

import org.apache.commons.lang3.StringUtils

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

@Slf4j
abstract class GeneralRestClientAccessor implements RestClientAccessor {

	protected Client jerseyClient
	protected String hostUri

	public GeneralRestClientAccessor(final Client jerseyClient,final String hostUri) {
		this.jerseyClient = jerseyClient;
		this.hostUri = hostUri;
	}

	@Override
	String process(final String path,final int expectedStatus,final RestClientExecuteCallback restClientCallback,final RestClientCustomErrorHandler... customErrorHandlers)  {
		checkArgument(!StringUtils.isEmpty(path),"path can not be null")
		checkArgument(restClientCallback != null,"restClientCallback can not be null")
		def meta
		WebResource webResource = jerseyClient.resource(hostUri).path(path)
		ClientResponse clientResponse = restClientCallback.execute(webResource)
		int statusCode = clientResponse.getStatus()
		String respStr = getResponsePayload(clientResponse)
		if(statusCode != expectedStatus){
			if(customErrorHandlers){
				customErrorHandlers.each {
					it.handle(statusCode, respStr)
				}
			} else {
				switch (statusCode) {
					case ClientResponse.Status.NOT_FOUND.code:
						throw new NotFoundException(respStr)
						break
					case ClientResponse.Status.CONFLICT.code:
						throw new ConflictException(respStr)
						break
					default:
						throw new IllegalStateException(respStr)
						break
				}
			}
		}
		return respStr
	}
}
