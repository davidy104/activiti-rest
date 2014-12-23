package nz.co.bookshop.process.activiti.convert

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.activiti.convert.component.PageMapToModel
import nz.co.bookshop.process.activiti.convert.component.ProcessDefinitionMapToModel
import nz.co.bookshop.process.activiti.model.ProcessDefinition
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject

class ProcessDefinitionConverter {
	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	ProcessDefinitionMapToModel processDefinitionMapToModel

	@Inject
	PageMapToModel pageMapToModel

	ProcessDefinition jsonToProcessDefinition(final String jsonText){
		return processDefinitionMapToModel.apply(jsonSlurper.parseText(jsonText))
	}

	Set<ProcessDefinition> jsonToProcessDefinitions(final String jsonText){
		Set<ProcessDefinition> processDefinitionSet = []
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		List resultList = (List)resultMap['data']
		if(resultList){
			resultList.each {
				processDefinitionSet << processDefinitionMapToModel.apply((Map)it)
			}
		}
		return processDefinitionSet
	}

	Page<ProcessDefinition> jsonToprocessDefinitionPage(final String jsonText){
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		Page<ProcessDefinition> page = pageMapToModel.apply(resultMap)
		List resultList = (List)resultMap['data']
		if(resultList){
			resultList.each {
				page.content << processDefinitionMapToModel.apply((Map)it)
			}
		}
		return page
	}
}
