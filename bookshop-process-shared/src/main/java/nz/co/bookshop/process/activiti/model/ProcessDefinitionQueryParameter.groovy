package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter

enum ProcessDefinitionQueryParameter implements AbstractEnumQueryParameter{
	version,name,nameLike,key,keyLike,resourceName,resourceNameLike,category,categoryLike,categoryNotEquals,
	deploymentId,startableByUser,latest,suspended,sort
}
