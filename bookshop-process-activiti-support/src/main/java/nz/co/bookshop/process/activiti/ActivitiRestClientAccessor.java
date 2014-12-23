package nz.co.bookshop.process.activiti;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import nz.co.bookshop.process.AbstractEnumQueryParameter;
import nz.co.bookshop.process.util.GeneralRestClientAccessor;
import nz.co.bookshop.process.util.RestClientExecuteCallback;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ActivitiRestClientAccessor extends GeneralRestClientAccessor {

	public ActivitiRestClientAccessor(Client jerseyClient, final String actvitiRestHostUri) {
		super(jerseyClient, actvitiRestHostUri);
	}

	public String query(final String path, Map<? extends AbstractEnumQueryParameter, String> emunQueryParameters) throws Exception {
		return process(path, emunQueryParameters, ClientResponse.Status.OK.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			}
		});
	}

	public String get(final String path) throws Exception {
		return process(path, ClientResponse.Status.OK.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(final WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			}
		});
	}

	public void delete(final String path) throws Exception {
		process(path, ClientResponse.Status.NO_CONTENT.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.delete(ClientResponse.class);
			}
		});
	}

	public String paginate(final String path, Map<AbstractEnumQueryParameter, String> emunQueryParameters, final Integer pageOffset, final Integer pageSize) throws Exception {
		if (pageOffset != null) {
			emunQueryParameters.put(PagingAndSortingParameter.start, String.valueOf(pageOffset));
		}
		if (pageSize != null) {
			emunQueryParameters.put(PagingAndSortingParameter.size, String.valueOf(pageSize));
		}
		return process(path, emunQueryParameters, ClientResponse.Status.OK.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(final WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			}
		});
	}

	public String create(final String path, final String jsonBody) throws Exception {
		return process(path, ClientResponse.Status.CREATED.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(final WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonBody);
			}
		});
	}

	public String update(final String path, final String updateJsonBody) throws Exception {
		return process(path, ClientResponse.Status.OK.getStatusCode(), new RestClientExecuteCallback() {
			@Override
			public ClientResponse execute(final WebResource webResource) {
				return webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, updateJsonBody);
			}
		});
	}
}
