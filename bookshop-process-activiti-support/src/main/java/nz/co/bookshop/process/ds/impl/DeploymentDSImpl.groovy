package nz.co.bookshop.process.ds.impl

import groovy.util.logging.Slf4j

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.ActivitiRestClientAccessor
import nz.co.bookshop.process.ds.DeploymentDS
import nz.co.bookshop.process.util.RestClientExecuteCallback

import com.google.inject.Inject
import com.google.inject.name.Named
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.WebResource
import com.sun.jersey.multipart.FormDataMultiPart
import com.sun.jersey.multipart.file.FileDataBodyPart

@Slf4j
class DeploymentDSImpl implements DeploymentDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	@Override
	Map deployment(final String name,final String category,final File uploadFile) {
		String tenantId = "${name}:${category}"
		String fileName = uploadFile.getName()
		String extension = fileName.substring(fileName.indexOf("."), fileName.length())
		String uploadName = "${tenantId}${extension}"
		final FormDataMultiPart multiPart = new FormDataMultiPart()
		multiPart.field("tenantId", tenantId)
		multiPart.bodyPart(new FileDataBodyPart(uploadName, uploadFile))

		return (Map)activitiRestClientAccessor.process("/repository/deployments", new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.type(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.APPLICATION_JSON)
						.post(ClientResponse.class, multiPart)
			}
		},ClientResponse.Status.CREATED.code).responseMetaData
	}

	@Override
	Map getDeployment(final String deployName,final String deployCategory) {
		return (Map)activitiRestClientAccessor.process("/repository/deployments", new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.queryParam("tenantId", "$deployName:$deployCategory").accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		},ClientResponse.Status.OK.code).responseMetaData
	}

	@Override
	Map getDeployment(final String deploymentId){
		return (Map)activitiRestClientAccessor.process('/repository/deployments/'+deploymentId, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		},ClientResponse.Status.OK.code).responseMetaData
	}

	@Override
	void unDeployment(final String deploymentId) {
		activitiRestClientAccessor.process('/repository/deployments/'+deploymentId, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.delete(ClientResponse.class)
					}
				},ClientResponse.Status.NO_CONTENT.code)
	}

	@Override
	List getDeploymentResource(final String deploymentId) {
		return (List)activitiRestClientAccessor.process('/repository/deployments/'+deploymentId+'/resources', new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		},ClientResponse.Status.OK.code).responseMetaData
	}
}
