package nz.co.bookshop.process.activiti.convert;

import java.util.Map;

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.activiti.convert.component.PageMapToModel
import nz.co.bookshop.process.activiti.convert.component.ProcessInstanceMapToModel
import nz.co.bookshop.process.activiti.model.ProcessInstance
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject

class ProcessInstanceConverter {

	@Inject
	ProcessInstanceMapToModel processInstanceMapToModel

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	PageMapToModel pageMapToModel

	String toProcessInstanceJson(final String processDefinitionId,final String processDefinitionKey,final String businessKey,final Map<String, Object> variables){
		jsonBuilder{
			if(processDefinitionId){
				processDefinitionId "${processDefinitionId}"
			}
			if(processDefinitionKey){
				processDefinitionKey "${processDefinitionKey}"
			}

			if(businessKey){
				businessKey "${businessKey}"
			}
			if(variables){
				variables(
						variables.collect {key, val ->
							[name:"${key}",value:"${val}"]
						}
						)
			}
		}
		return jsonBuilder.toString()
	}

	ProcessInstance jsonToProcessInstance(final String jsonText){
		return processInstanceMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<ProcessInstance> jsonToProcessInstances(final String jsonText){
		Set<ProcessInstance> resultSet = []
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		List dataList = (List)resultMap['data']
		dataList.each {
			resultSet <<  processInstanceMapToModel.apply((Map)it)
		}
		return resultSet
	}

	Page<ProcessInstance> jsonToProcessInstancePage(final String jsonText){
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		Page page = pageMapToModel.apply(resultMap)
		List resultList = resultMap['data']
		if(resultList){
			resultList.each {
				page.content << processInstanceMapToModel.apply((Map)it)
			}
		}
		return page
	}
}
