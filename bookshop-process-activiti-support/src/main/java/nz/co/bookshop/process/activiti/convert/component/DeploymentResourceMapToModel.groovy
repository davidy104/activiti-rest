package nz.co.bookshop.process.activiti.convert.component

import groovy.util.logging.Slf4j
import nz.co.bookshop.process.model.activiti.DeploymentResource

import com.google.common.base.Function

@Slf4j
class DeploymentResourceMapToModel implements Function<Map<String,String>, DeploymentResource> {

	@Override
	public DeploymentResource apply(final Map<String, String> inputMap) {
		return new DeploymentResource(id:inputMap["id"],
		url:inputMap["url"],
		dataUrl:inputMap['dataUrl'],
		mediaType:inputMap['mediaType'],
		type:inputMap['type']
		)
	}
}
