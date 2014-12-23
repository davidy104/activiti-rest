package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter


enum DeploymentQueryParameter implements AbstractEnumQueryParameter{
	name,nameLike,category,categoryNotEquals,tenantId,tenantIdLike,withoutTenantId,sort
}
