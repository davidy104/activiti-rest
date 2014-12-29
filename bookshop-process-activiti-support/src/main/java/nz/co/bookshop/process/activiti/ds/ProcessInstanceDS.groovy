package nz.co.bookshop.process.activiti.ds;

import nz.co.bookshop.process.activiti.IdentityType
import nz.co.bookshop.process.activiti.Variable
import nz.co.bookshop.process.activiti.model.Identity
import nz.co.bookshop.process.activiti.model.ProcessInstance
import nz.co.bookshop.process.activiti.model.ProcessInstanceQueryParameter
import nz.co.bookshop.process.model.Page


interface ProcessInstanceDS {
	ProcessInstance startProcessByProcessDefinitionId(String processDefinitionId, String businessKey,Map<String, Object> variables) throws Exception
	ProcessInstance startProcessByProcessDefinitionKey(String processDefinitionKey, String businessKey,Map<String, Object> variables) throws Exception
	ProcessInstance getProcessInstance(String processInstanceId)throws Exception
	void getLegacyProcessInstance(String processInstanceId)throws Exception
	void deleteProcessInstance(String processInstanceId) throws Exception
	ProcessInstance suspendProcessInstance(String processInstanceId)throws Exception
	ProcessInstance activeProcessInstance(String processInstanceId)throws Exception
	Page<ProcessInstance> paginateProcessInstances(Map<ProcessInstanceQueryParameter, String> processInstanceQueryParameters,Integer pageOffset,Integer pageSize)
	Set<Identity> getInvolvedPeopleForProcessInstance(String processInstanceId)

	/**
	 *
	 * @param processInstanceId
	 * @param user
	 * @param identityType
	 *            see@IdentityLinkType
	 *            ["assignee","candidate","owner","participant","starter"]
	 * @return
	 * @throws Exception
	 */
	Identity addInvolvedPeopleToProcess(String processInstanceId, String user,IdentityType identityType) throws Exception

	/**
	 *
	 * @param processInstanceId
	 * @param user
	 * @param identityType
	 *            see@IdentityLinkType
	 * @throws Exception
	 */
	void removeInvolvedPeopleFromProcess(String processInstanceId, String user,IdentityType identityType) throws Exception
	Set<Variable> getVariablesFromProcess(String processInstanceId)
	Variable getVariableFromProcess(String processInstanceId,String variableName) throws Exception
	Set<Variable> createVariablesForProcess(String processInstanceId,Set<Variable> addVariables)
	Variable updateVariableForProcess(String processInstanceId,String variableName, Variable updateVariable) throws Exception
}
