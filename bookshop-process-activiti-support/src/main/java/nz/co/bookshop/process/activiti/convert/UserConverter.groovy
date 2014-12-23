package nz.co.bookshop.process.activiti.convert;

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.activiti.OperationType
import nz.co.bookshop.process.activiti.convert.component.PageMapToModel
import nz.co.bookshop.process.activiti.convert.component.UserMapToModel
import nz.co.bookshop.process.activiti.model.User;
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject

class UserConverter {

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	UserMapToModel userMapToModel

	@Inject
	PageMapToModel pageMapToModel

	String userToJson(final User user,final OperationType operationType){
		jsonBuilder{
			if(operationType == OperationType.CREATION){
				id user.id
			}
			firstName user.firstName
			lastName user.lastName
			email user.email
			password user.password
		}
		return jsonBuilder.toString()
	}

	User jsonToUser(final String jsonText){
		return userMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<User> jsonToUsers(final String jsonText){
		Set<User> users = []
		Map resultMap =(Map)jsonSlurper.parseText(jsonText)
		List resultList = resultMap['data']
		if(resultList){
			resultList.each {
				users << userMapToModel.apply((Map)it)
			}
		}
		return users
	}

	Page jsonToUserPage(final String jsonText){
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		Page page = pageMapToModel.apply(resultMap)
		List resultList = resultMap['data']
		if(resultList){
			resultList.each {
				page.content << userMapToModel.apply((Map)it)
			}
		}
		return page
	}
}
