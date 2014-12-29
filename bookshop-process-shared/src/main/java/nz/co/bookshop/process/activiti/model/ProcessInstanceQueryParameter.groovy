package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter

enum ProcessInstanceQueryParameter implements AbstractEnumQueryParameter{
	id,
	processDefinitionKey,
	processDefinitionId,
	businessKey,
	involvedUser,
	suspended,
	superProcessInstanceId,
	subProcessInstanceId,
	excludeSubprocesses,
	includeProcessVariables,
	sort,
	tenantId,
	tenantIdLike,
	withoutTenantId,
}
