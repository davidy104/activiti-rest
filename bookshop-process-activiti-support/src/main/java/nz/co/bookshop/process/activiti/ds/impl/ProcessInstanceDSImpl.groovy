package nz.co.bookshop.process.activiti.ds.impl;

import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.IdentityType
import nz.co.bookshop.process.activiti.Variable
import nz.co.bookshop.process.activiti.convert.GeneralModelConverter
import nz.co.bookshop.process.activiti.convert.ProcessInstanceConverter
import nz.co.bookshop.process.activiti.ds.ProcessInstanceDS
import nz.co.bookshop.process.activiti.model.Identity
import nz.co.bookshop.process.activiti.model.ProcessInstance
import nz.co.bookshop.process.activiti.model.ProcessInstanceQueryParameter
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject
import com.google.inject.name.Named

class ProcessInstanceDSImpl implements ProcessInstanceDS {

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	final static String PROCESS_INSTANCE_PATH="/runtime/process-instances／"

	@Inject
	ProcessInstanceConverter processInstanceConverter

	@Inject
	GeneralModelConverter generalModelConverter

	@Override
	ProcessInstance startProcessByProcessDefinitionId(final String processDefinitionId,final String businessKey,final Map<String, Object> variables)  {
		final String jsonbody = processInstanceConverter.toProcessInstanceJson(processDefinitionId, null, businessKey, variables)
		return processInstanceConverter.jsonToProcessInstance(activitiRestClientAccessor.create(PROCESS_INSTANCE_PATH, jsonbody))
	}

	@Override
	ProcessInstance startProcessByProcessDefinitionKey(final String processDefinitionKey,final String businessKey,final Map<String, Object> variables) {
		final String jsonbody = processInstanceConverter.toProcessInstanceJson(null, processDefinitionKey, businessKey, variables)
		return processInstanceConverter.jsonToProcessInstance(activitiRestClientAccessor.create(PROCESS_INSTANCE_PATH, jsonbody))
	}
	@Override
	ProcessInstance getProcessInstance(final String processInstanceId) {
		return processInstanceConverter.jsonToProcessInstance(activitiRestClientAccessor.get(PROCESS_INSTANCE_PATH+processInstanceId))
	}

	@Override
	void deleteProcessInstance(final String processInstanceId)  {
		activitiRestClientAccessor.delete(PROCESS_INSTANCE_PATH+processInstanceId)
	}

	@Override
	ProcessInstance suspendProcessInstance(final String processInstanceId) {
		return processInstanceConverter.jsonToProcessInstance(activitiRestClientAccessor.update(PROCESS_INSTANCE_PATH+processInstanceId, "{\"action\":\"suspend\"}"))
	}

	@Override
	ProcessInstance activeProcessInstance(final String processInstanceId) {
		return processInstanceConverter.jsonToProcessInstance(activitiRestClientAccessor.update(PROCESS_INSTANCE_PATH+processInstanceId, "{\"action\":\"activate\"}"))
	}

	@Override
	Page<ProcessInstance> paginateProcessInstances(final Map<ProcessInstanceQueryParameter, String> processInstanceQueryParameters,final Integer pageOffset,final Integer pageSize) {
		return processInstanceConverter.jsonToProcessInstancePage(activitiRestClientAccessor.paginate(PROCESS_INSTANCE_PATH, processInstanceQueryParameters, pageOffset, pageSize))
	}

	@Override
	Set<Identity> getInvolvedPeopleForProcessInstance(final String processInstanceId) {
		return generalModelConverter.jsonToIdentities(activitiRestClientAccessor.get(PROCESS_INSTANCE_PATH+ processInstanceId+ "/identitylinks"))
	}

	@Override
	Identity addInvolvedPeopleToProcess(final String processInstanceId,final String user,final IdentityType identityType)  {
		return null
	}

	@Override
	void removeInvolvedPeopleFromProcess(final String processInstanceId,final String user,final IdentityType identityType)  {
	}

	@Override
	Set<Variable> getVariablesFromProcess(final String processInstanceId) {
		return null
	}

	@Override
	Variable getVariableFromProcess(final String processInstanceId,final String variableName) {
		return null
	}

	@Override
	Set<Variable> createVariablesForProcess(final String processInstanceId,final Set<Variable> addVariables) {
		return null
	}

	@Override
	Variable updateVariableForProcess(final String processInstanceId,
			final String variableName,final Variable updateVariable) {
		return null
	}
}
