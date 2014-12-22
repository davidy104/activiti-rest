package nz.co.bookshop.process.activiti.convert.component

import groovy.util.logging.Slf4j

import java.text.SimpleDateFormat

import nz.co.bookshop.process.model.activiti.Deployment

import com.google.common.base.Function

@Slf4j
class DeploymentMapToModel implements Function<Map<String,String>, Deployment> {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

	@Override
	public Deployment apply(Map<String, String> inputMap) {
		Deployment deployment
		if(inputMap){
			deployment = new Deployment(id:inputMap['id'],
			name:inputMap['name'],
			url:inputMap['url'],
			tenantId:inputMap['tenantId'])

			if(inputMap['tenantId']){
				def tmpCategoryString = inputMap['tenantId']
				String[] tenantIdArray = tmpCategoryString.split(":")
				if(tenantIdArray.length == 2){
					deployment.category = tenantIdArray[1]
				}
			}

			if(inputMap['deploymentTime']){
				deployment.deploymentTime = sdf.parse(inputMap['deploymentTime'])
			}
		}
		return deployment
	}
}
