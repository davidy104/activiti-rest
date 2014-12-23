package nz.co.bookshop.process.activiti.ds.impl

import groovy.util.logging.Slf4j
import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.Family
import nz.co.bookshop.process.activiti.ProcessAction
import nz.co.bookshop.process.activiti.convert.ProcessDefinitionConverter
import nz.co.bookshop.process.activiti.ds.ProcessDefinitionDS
import nz.co.bookshop.process.activiti.model.Identity
import nz.co.bookshop.process.activiti.model.ProcessDefinition
import nz.co.bookshop.process.activiti.model.ProcessDefinitionQueryParameter
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject
import com.google.inject.name.Named

@Slf4j
class ProcessDefinitionDSImpl implements ProcessDefinitionDS{

	@Inject
	@Named("activitiRestClientAccessor")
	ActivitiRestClientAccessor activitiRestClientAccessor

	final static String PROCESS_DEFINITION_PATH="/repository/process-definitions/"

	@Inject
	ProcessDefinitionConverter processDefinitionConverter

	@Override
	Page<ProcessDefinition> paginateProcessDefinition(final Map<ProcessDefinitionQueryParameter, String> processQueryParameters,final Integer pageOffset,final Integer pageSize) {
		return processDefinitionConverter.jsonToprocessDefinitionPage(activitiRestClientAccessor.paginate(PROCESS_DEFINITION_PATH,processQueryParameters,pageOffset,pageSize))
	}

	@Override
	ProcessDefinition updateCategory(final String processDefinitionId,final String category) {
		final String requestEntity = "{\"category\" : \"" + category + "\"}"
		return processDefinitionConverter.jsonToProcessDefinition(activitiRestClientAccessor.update(PROCESS_DEFINITION_PATH+processDefinitionId, requestEntity))
	}

	@Override
	ProcessDefinition suspendProcessDefinition(final String processDefinitionId,final boolean includeProcessInstances,final Date effectiveDate) {
		final String requestEntity = processDefinitionConverter.toProcessActionJson(ProcessAction.suspend,includeProcessInstances, effectiveDate)
		return processDefinitionConverter.jsonToProcessDefinition(activitiRestClientAccessor.update(PROCESS_DEFINITION_PATH, requestEntity))
	}

	@Override
	ProcessDefinition activeProcessDefinition(final String processDefinitionId,final boolean includeProcessInstances,final Date effectiveDate)  {
		final String requestEntity = processDefinitionConverter.toProcessActionJson(ProcessAction.activate,includeProcessInstances, effectiveDate)
		return processDefinitionConverter.jsonToProcessDefinition(activitiRestClientAccessor.update(PROCESS_DEFINITION_PATH, requestEntity))
	}

	@Override
	Set<Identity> getAllIdentities(final String processDefinitionId)  {
		return null
	}

	@Override
	Identity addIdentity(String processDefinitionId, Family family, String name) {
		return null
	}

	@Override
	void deleteIdentity(String processDefinitionId, Family family, String identityId) {
	}

	@Override
	Identity getIdentity(String processDefinitionId, Family family, String identityId)  {
		return null
	}
}
