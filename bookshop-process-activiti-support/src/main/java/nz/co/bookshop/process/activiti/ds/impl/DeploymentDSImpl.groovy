package nz.co.bookshop.process.activiti.ds.impl

import groovy.util.logging.Slf4j

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.convert.DeploymentConverter
import nz.co.bookshop.process.activiti.ds.DeploymentDS
import nz.co.bookshop.process.model.activiti.Deployment
import nz.co.bookshop.process.model.activiti.DeploymentResource
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

	@Inject
	DeploymentConverter deploymentConverter

	@Override
	Deployment deployment(final String name,final String category,final File uploadFile) {
		String tenantId = "${name}:${category}"
		String fileName = uploadFile.getName()
		String extension = fileName.substring(fileName.indexOf("."), fileName.length())
		String uploadName = "${tenantId}${extension}"
		final FormDataMultiPart multiPart = new FormDataMultiPart()
		multiPart.field("tenantId", tenantId)
		multiPart.bodyPart(new FileDataBodyPart(uploadName, uploadFile))

		String jsonResponse = activitiRestClientAccessor.process("/repository/deployments",ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.type(MediaType.MULTIPART_FORM_DATA)
								.accept(MediaType.APPLICATION_JSON)
								.post(ClientResponse.class, multiPart)
					}
				})

		return deploymentConverter.jsonToDeployment(jsonResponse)
	}

	@Override
	Deployment getDeployment(final String deployName,final String deployCategory) {
		String jsonResponse = activitiRestClientAccessor.process("/repository/deployments",ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.queryParam("tenantId", "$deployName:$deployCategory").accept(MediaType.APPLICATION_JSON)
								.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
					}
				})
		return deploymentConverter.jsonToDeployments(jsonResponse).first()
	}

	@Override
	Deployment getDeployment(final String deploymentId){
		return deploymentConverter.jsonToDeployment(activitiRestClientAccessor.process('/repository/deployments/'+deploymentId, ClientResponse.Status.OK.code,new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.accept(MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
			}
		}))
	}

	@Override
	void unDeployment(final String deploymentId) {
		activitiRestClientAccessor.process('/repository/deployments/'+deploymentId,ClientResponse.Status.NO_CONTENT.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.delete(ClientResponse.class)
					}
				})
	}

	@Override
	Set<DeploymentResource> getDeploymentResource(final String deploymentId) {
		String jsonResponse = activitiRestClientAccessor.process('/repository/deployments/'+deploymentId+'/resources',ClientResponse.Status.OK.code, new RestClientExecuteCallback(){
					@Override
					ClientResponse execute(WebResource webResource) {
						webResource.accept(MediaType.APPLICATION_JSON)
								.type(MediaType.APPLICATION_JSON).get(ClientResponse.class)
					}
				})
		return deploymentConverter.jsonToDeploymentResources(jsonResponse)
	}
}
