package nz.co.bookshop.process.activiti.convert.component

import nz.co.bookshop.process.activiti.model.ProcessDefinition

import com.google.common.base.Function

class ProcessDefinitionMapToModel implements Function<Map<String,String>, ProcessDefinition> {

	@Override
	ProcessDefinition apply(final Map<String, String> inputMap) {
		ProcessDefinition processDefinition = new ProcessDefinition()
		processDefinition.id = inputMap['id']
		processDefinition.url = inputMap['url']
		processDefinition.key = inputMap['key']
		processDefinition.name = inputMap['name']
		if(inputMap['version']){
			processDefinition.version = Integer.valueOf(inputMap['version'])
		}
		processDefinition.description = inputMap['description']
		processDefinition.deploymentId = inputMap['deploymentId']
		processDefinition.deploymentUrl = inputMap['deploymentUrl']
		processDefinition.resource = inputMap['resource']
		processDefinition.diagramResource = inputMap['diagramResource']
		processDefinition.category = inputMap['category']
		if(inputMap['graphicalNotationDefined']){
			processDefinition.graphicalNotationDefined = Boolean.valueOf(inputMap['graphicalNotationDefined'])
		}
		if(inputMap['suspended']){
			processDefinition.suspended = Boolean.valueOf(inputMap['suspended'])
		}
		if(inputMap['startFormDefined']){
			processDefinition.startFormDefined = Boolean.valueOf(inputMap['startFormDefined'])
		}
		return processDefinition
	}
}
