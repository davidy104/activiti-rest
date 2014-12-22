package nz.co.bookshop.process.activiti.convert

import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.activiti.convert.component.DeploymentMapToModel
import nz.co.bookshop.process.activiti.convert.component.DeploymentResourceMapToModel
import nz.co.bookshop.process.model.activiti.Deployment
import nz.co.bookshop.process.model.activiti.DeploymentResource

import com.google.inject.Inject
import com.google.inject.name.Named

@Slf4j
class DeploymentConverter {

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	@Named("deploymentMapToModel")
	DeploymentMapToModel deploymentMapToModel

	@Inject
	@Named("deploymentResourceMapToModel")
	DeploymentResourceMapToModel deploymentResourceMapToModel

	Deployment jsonToDeployment(final String jsonText){
		return deploymentMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	DeploymentResource jsonToDeploymentResource(final String jsonText){
		return deploymentResourceMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<Deployment> jsonToDeployments(final String jsonText) {
		Set<Deployment> deployments = []
		List metaList = (List)(((Map)jsonSlurper.parseText(jsonText))['data'])
		metaList.each {
			deployments << deploymentMapToModel.apply((Map)it)
		}
		return deployments
	}

	Set<DeploymentResource> jsonToDeploymentResources(final String jsonText){
		Set<DeploymentResource> deployResources = []
		List resultList = (List)jsonSlurper.parseText(jsonText)
		if(resultList){
			resultList.each {
				deployResources << deploymentResourceMapToModel.apply((Map)it)
			}
		}
		return deployResources
	}
}
