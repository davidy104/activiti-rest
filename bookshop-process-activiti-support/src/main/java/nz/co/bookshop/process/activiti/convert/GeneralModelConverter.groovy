package nz.co.bookshop.process.activiti.convert

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.OperationType
import nz.co.bookshop.process.activiti.Variable
import nz.co.bookshop.process.activiti.convert.component.IdentityMapToModel
import nz.co.bookshop.process.activiti.convert.component.VariableMapToModel
import nz.co.bookshop.process.activiti.model.Identity

import com.google.inject.Inject

class GeneralModelConverter {

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	IdentityMapToModel identityMapToModel

	@Inject
	VariableMapToModel variableMapToModel

	Identity jsonToIdentity(final String jsonText){
		return identityMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<Identity> jsonToIdentities(final String jsonText){
		Set<Identity> identities = []
		List resultList = (List)jsonSlurper.parseText(jsonText)
		if(resultList){
			resultList.each {
				identities << identityMapToModel.apply((Map)it)
			}
		}
		return identities
	}

	Variable jsonToVariable(final String jsonText){
		return variableMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<Variable> jsonToVariables(final String jsonText){
		Set<Variable> variables = []
		List resultList = (List)jsonSlurper.parseText(jsonText)
		if(resultList){
			resultList.each {
				variables << variableMapToModel.apply((Map)it)
			}
		}
		return variables
	}

	String toVariablesJson(final Set<Variable> variables){
		jsonBuilder(
				variables.collect { variable->
					[
						name:variable.name,
						scope:variable.scope,
						value:variable.value,
						type:variable.type,
					]
				}
				)
		return jsonBuilder.toString()
	}

	String toVariableJson(final Variable variable,final OperationType operation){
		jsonBuilder{
			name variable.name
			scope variable.scope
			value variable.value
			if(operation != OperationType.UPDATE){
				type variable.type
			}
		}
		return jsonBuilder.toString()
	}
}
