package nz.co.bookshop.process.activiti.convert;

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.activiti.convert.component.UserMapToModel

import com.amazonaws.services.identitymanagement.model.User
import com.google.inject.Inject

class UserConverter {

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	UserMapToModel userMapToModel

	User jsonToUser(final String jsonText){
		return userMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<User> jsonToUsers(final String jsonText){
		
	}
}
