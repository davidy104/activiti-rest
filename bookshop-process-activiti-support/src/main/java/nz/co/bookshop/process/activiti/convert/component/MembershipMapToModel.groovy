package nz.co.bookshop.process.activiti.convert.component

import nz.co.bookshop.process.model.activiti.MemberShip

import com.google.common.base.Function

class MembershipMapToModel implements Function<Map<String,String>, MemberShip> {

	@Override
	MemberShip apply(final Map<String, String> inputMap) {
		return new MemberShip(userId:inputMap['userId'],groupId:inputMap['groupId'],url:inputMap['url'])
	}
}
