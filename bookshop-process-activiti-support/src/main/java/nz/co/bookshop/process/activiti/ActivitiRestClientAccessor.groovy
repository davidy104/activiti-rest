package nz.co.bookshop.process.activiti

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static nz.co.bookshop.process.util.JerseyClientUtil.getResponsePayload
import groovy.util.logging.Slf4j

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.AbstractEnumQueryParameter
import nz.co.bookshop.process.util.GeneralRestClientAccessor
import nz.co.bookshop.process.util.RestClientExecuteCallback

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

@Slf4j
class ActivitiRestClientAccessor extends GeneralRestClientAccessor {

	ActivitiRestClientAccessor(Client jerseyClient,final String actvitiRestHostUri) {
		super(jerseyClient,actvitiRestHostUri)
	}


	String query(final String path, Map<? extends AbstractEnumQueryParameter,String> emunQueryParameters){
		return process(path, emunQueryParameters, ClientResponse.Status.OK.code,new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		})
	}

	String get(final String path){
		return process(path, ClientResponse.Status.OK.code,new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		})
	}

	void delete(final String path){
		process(path,ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.delete(ClientResponse.class)
					}
				})
	}

	String paginate(final String path, Map<? extends AbstractEnumQueryParameter,String> emunQueryParameters,final Integer pageOffset,final Integer pageSize){
		if(pageOffset){
			emunQueryParameters.put(PagingAndSortingParameter.start, String.valueOf(pageOffset))
		}
		if(pageSize){
			emunQueryParameters.put(PagingAndSortingParameter.size, String.valueOf(pageSize))
		}

		return process(path, emunQueryParameters, ClientResponse.Status.OK.code,new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		})
	}

	String create(final String path,final String jsonBody){
		return process(path,ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,jsonBody)
			}
		})
	}
}
