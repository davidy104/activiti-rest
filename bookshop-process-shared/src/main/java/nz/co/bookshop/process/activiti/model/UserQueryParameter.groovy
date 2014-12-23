package nz.co.bookshop.process.activiti.model

import nz.co.bookshop.process.AbstractEnumQueryParameter

enum UserQueryParameter implements AbstractEnumQueryParameter{
	id,firstName,lastName,email,firstNameLike,lastNameLike,emailLike,memberOfGroup,potentialStarter,sort
}
