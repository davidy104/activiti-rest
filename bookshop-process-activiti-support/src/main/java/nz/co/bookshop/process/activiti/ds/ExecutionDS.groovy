package nz.co.bookshop.process.activiti.ds

import nz.co.bookshop.process.activiti.Variable
import nz.co.bookshop.process.activiti.VariableScope
import nz.co.bookshop.process.activiti.model.Execution
import nz.co.bookshop.process.activiti.model.ExecutionActionRequest
import nz.co.bookshop.process.activiti.model.ExecutionQueryParameter
import nz.co.bookshop.process.activiti.model.Group
import nz.co.bookshop.process.model.Page


interface ExecutionDS {
	Execution getExecutionById(String executionId) throws Exception

	Execution actionOnExecution(String executionId,ExecutionActionRequest executionActionRequest) throws Exception

	String[] getActiveActivities(String executionId)

	Page<Group> paginateGroup(Map<ExecutionQueryParameter,String> executionQueryParameters, Integer pageOffset,Integer pageSize)

	Set<Variable> getVariablesOnExecution(String executionId,VariableScope scope)

	Variable getVariableOnExecution(String executionId, String variableName,VariableScope scope) throws Exception

	Set<Variable> createVariablesOnExecution(String executionId,Set<Variable> addVariables)

	Variable updateVariableOnExecution(String executionId, String variableName,Variable updateVariable) throws Exception
}
