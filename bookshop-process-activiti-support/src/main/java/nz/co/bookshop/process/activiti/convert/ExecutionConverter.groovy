package nz.co.bookshop.process.activiti.convert

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import com.google.inject.Inject

class ExecutionConverter {

	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder
	
}
