package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter

enum GroupQueryParameter implements AbstractEnumQueryParameter{
	id,name,type,nameLike,member,potentialStarter,sort
}
