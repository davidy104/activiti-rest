package nz.co.bookshop.process.activiti.ds.impl;

import java.util.Map;
import java.util.Set;

import nz.co.bookshop.process.activiti.IdentityType;
import nz.co.bookshop.process.activiti.Variable;
import nz.co.bookshop.process.activiti.ds.ProcessInstanceDS
import nz.co.bookshop.process.activiti.model.Identity;
import nz.co.bookshop.process.activiti.model.ProcessInstance;
import nz.co.bookshop.process.activiti.model.ProcessInstanceQueryParameter;
import nz.co.bookshop.process.model.Page;

class ProcessInstanceDSImpl implements ProcessInstanceDS {

	@Override
	public ProcessInstance startProcessByProcessDefinitionId(
			String processDefinitionId, String businessKey,
			Map<String, Object> variables) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessInstance startProcessByProcessDefinitionKey(
			String processDefinitionKey, String businessKey,
			Map<String, Object> variables) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessInstance getProcessInstance(String processInstanceId)
	throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getLegacyProcessInstance(String processInstanceId)
	throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProcessInstance(String processInstanceId)
	throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ProcessInstance suspendProcessInstance(String processInstanceId)
	throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessInstance activeProcessInstance(String processInstanceId)
	throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProcessInstance> paginateProcessInstances(
			Map<ProcessInstanceQueryParameter, String> processInstanceQueryParameters,
			Integer pageOffset, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Identity> getInvolvedPeopleForProcessInstance(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identity addInvolvedPeopleToProcess(String processInstanceId,
			String user, IdentityType identityType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeInvolvedPeopleFromProcess(String processInstanceId,
			String user, IdentityType identityType) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Variable> getVariablesFromProcess(String processInstanceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable getVariableFromProcess(String processInstanceId,
			String variableName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Variable> createVariablesForProcess(String processInstanceId,
			Set<Variable> addVariables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable updateVariableForProcess(String processInstanceId,
			String variableName, Variable updateVariable) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
