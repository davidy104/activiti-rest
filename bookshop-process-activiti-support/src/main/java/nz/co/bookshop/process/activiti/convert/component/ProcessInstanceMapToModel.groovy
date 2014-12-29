package nz.co.bookshop.process.activiti.convert.component;

import nz.co.bookshop.process.activiti.model.ProcessInstance

import com.google.common.base.Function

class ProcessInstanceMapToModel implements Function<Map<String,String>, ProcessInstance> {

	@Override
	ProcessInstance apply(final Map<String, String> inputMap) {
		ProcessInstance processInstance = new ProcessInstance(id:inputMap['id'],url:inputMap['url'],processDefinitionUrl:inputMap['processDefinitionUrl'],businessKey:inputMap['businessKey'])
		processInstance.activityId = inputMap['activityId']
		processInstance.tenantId = inputMap['tenantId']
		if(inputMap['suspended']){
			processInstance.suspended = Boolean.valueOf(inputMap['suspended'])
		}
		return processInstance
	}
}

