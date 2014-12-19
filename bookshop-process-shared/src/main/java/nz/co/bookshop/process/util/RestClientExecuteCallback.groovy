package nz.co.bookshop.process.util;

import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource

interface RestClientExecuteCallback {
	ClientResponse execute(WebResource webResource) throws Exception
}
