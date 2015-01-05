package nz.co.bookshop.process.activiti.ds.impl

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.Variable
import nz.co.bookshop.process.activiti.VariableScope
import nz.co.bookshop.process.activiti.ds.ExecutionDS
import nz.co.bookshop.process.activiti.model.Execution
import nz.co.bookshop.process.activiti.model.ExecutionActionRequest
import nz.co.bookshop.process.activiti.model.ExecutionQueryParameter
import nz.co.bookshop.process.activiti.model.Group
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject
import com.google.inject.name.Named

class ExecutionDSImpl implements ExecutionDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	final static String EXECUTION_PATH="/runtime/executions/"


	@Override
	Execution getExecutionById(final String executionId) {
		return null
	}

	@Override
	Execution actionOnExecution(String executionId, ExecutionActionRequest executionActionRequest) {
		return null
	}

	@Override
	String[] getActiveActivities(String executionId) {
		return null
	}

	@Override
	Page<Group> paginateGroup(Map<ExecutionQueryParameter, String> executionQueryParameters, Integer pageOffset, Integer pageSize) {
		return null
	}

	@Override
	Set<Variable> getVariablesOnExecution(String executionId, VariableScope scope)  {
		return null
	}

	@Override
	Variable getVariableOnExecution(String executionId, String variableName, VariableScope scope) {
		return null
	}

	@Override
	Set<Variable> createVariablesOnExecution(String executionId, Set<Variable> addVariables)  {
		return null
	}

	@Override
	Variable updateVariableOnExecution(String executionId, String variableName, Variable updateVariable)  {
		return null
	}
}
