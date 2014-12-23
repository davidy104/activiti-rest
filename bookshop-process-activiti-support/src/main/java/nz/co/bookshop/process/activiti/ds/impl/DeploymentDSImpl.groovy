package nz.co.bookshop.process.activiti.ds.impl

import groovy.util.logging.Slf4j

import javax.ws.rs.core.MediaType

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.convert.DeploymentConverter
import nz.co.bookshop.process.activiti.ds.DeploymentDS
import nz.co.bookshop.process.activiti.model.Deployment
import nz.co.bookshop.process.activiti.model.DeploymentQueryParameter
import nz.co.bookshop.process.activiti.model.DeploymentResource
import nz.co.bookshop.process.model.Page
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

	final static String DEPLOYMENT_PATH="/repository/deployments/"

	@Override
	Deployment deployment(final String name,final String category,final File uploadFile) {
		String tenantId = "${name}:${category}"
		String fileName = uploadFile.getName()
		String extension = fileName.substring(fileName.indexOf("."), fileName.length())
		String uploadName = "${tenantId}${extension}"
		final FormDataMultiPart multiPart = new FormDataMultiPart()
		multiPart.field("tenantId", tenantId)
		multiPart.bodyPart(new FileDataBodyPart(uploadName, uploadFile))
		return deploymentConverter.jsonToDeployment(activitiRestClientAccessor.process(DEPLOYMENT_PATH,ClientResponse.Status.CREATED.code, new RestClientExecuteCallback(){
			@Override
			ClientResponse execute(WebResource webResource) {
				webResource.type(MediaType.MULTIPART_FORM_DATA)
						.accept(MediaType.APPLICATION_JSON)
						.post(ClientResponse.class, multiPart)
			}
		}))
	}

	@Override
	Deployment getDeployment(final String deployName,final String deployCategory) {
		Map<DeploymentQueryParameter, String> deploymentQueryParameters = [:]
		deploymentQueryParameters.put(DeploymentQueryParameter.tenantId, "$deployName:$deployCategory")
		return deploymentConverter.jsonToDeployments(activitiRestClientAccessor.query(DEPLOYMENT_PATH, deploymentQueryParameters)).first()
	}

	@Override
	Deployment getDeployment(final String deploymentId){
		return deploymentConverter.jsonToDeployment(activitiRestClientAccessor.get(DEPLOYMENT_PATH+deploymentId))
	}

	@Override
	void unDeployment(final String deploymentId) {
		activitiRestClientAccessor.delete(DEPLOYMENT_PATH+deploymentId)
	}

	@Override
	Set<DeploymentResource> getDeploymentResource(final String deploymentId) {
		return deploymentConverter.jsonToDeploymentResources(activitiRestClientAccessor.get(DEPLOYMENT_PATH+deploymentId+'/resources'))
	}

	@Override
	Page<Deployment> paginateDeployment(final Map<DeploymentQueryParameter, String> deploymentQueryParameters,final Integer pageOffset,final Integer pageSize) {
		return deploymentConverter.jsonToDeploymentPage(activitiRestClientAccessor.paginate(DEPLOYMENT_PATH, deploymentQueryParameters, pageOffset, pageSize))
	}
}
