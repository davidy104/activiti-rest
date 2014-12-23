package nz.co.bookshop.process.activiti.convert.component

import nz.co.bookshop.process.activiti.Variable

import com.google.common.base.Function

class VariableMapToModel implements Function<Map<String,String>, Variable> {

	@Override
	Variable apply(final Map<String, String> inputMap) {
		return new Variable(name:inputMap['name'],scope:inputMap['scope'],value:inputMap['value'],type:inputMap['type'])
	}
}
