package nz.co.bookshop.process.activiti.convert.component

import nz.co.bookshop.process.model.activiti.Group

import com.google.common.base.Function

class GroupMapToModel implements Function<Map<String,String>, Group> {

	@Override
	Group apply(final Map<String, String> inputMap) {
		return new Group(id:inputMap['id'],name:inputMap['name'],type:inputMap['type'],url:inputMap['url'])
	}
}
