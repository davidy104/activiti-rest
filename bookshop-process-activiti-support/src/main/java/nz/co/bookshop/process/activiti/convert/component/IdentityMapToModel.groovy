package nz.co.bookshop.process.activiti.convert.component

import nz.co.bookshop.process.activiti.IdentityType
import nz.co.bookshop.process.activiti.model.Identity

import com.google.common.base.Function

class IdentityMapToModel implements Function<Map<String,String>, Identity> {

	@Override
	Identity apply(final Map<String, String> inputMap) {
		String type = inputMap['type']
		IdentityType identityType
		switch(type){
			case "assignee":
				identityType = IdentityType.assignee
				break
			case "owner":
				identityType = IdentityType.owner
				break
			case "participant":
				identityType = IdentityType.participant
				break
			case "starter":
				identityType = IdentityType.starter
				break
			default:
				identityType = IdentityType.candidate
		}
		return new Identity(url:inputMap['url'],user:inputMap['user'],group:inputMap['group'],type:identityType)
	}
}
