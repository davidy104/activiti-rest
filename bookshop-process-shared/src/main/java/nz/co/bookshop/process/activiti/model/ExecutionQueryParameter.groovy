package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter

enum ExecutionQueryParameter implements AbstractEnumQueryParameter{
	id,processDefinitionKey,processDefinitionId,processInstanceId,messageEventSubscriptionName,signalEventSubscriptionName,parentId,
	tenantId,tenantIdLike,withoutTenantId,sort
}
