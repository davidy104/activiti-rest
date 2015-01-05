package nz.co.bookshop.process.activiti.model

import groovy.transform.ToString
import nz.co.bookshop.process.activiti.Variable
@ToString(includeNames = true, includeFields=true)
class ExecutionActionRequest {

	ExecutionAction action
	//Notifies the execution that a signal event has been received, requires a signalName parameter
	String signalName
	//Notifies the execution that a message event has been received, requires a messageName parameter.
	String messageName
	Set<Variable> variables = []
}
