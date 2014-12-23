package nz.co.bookshop.process.activiti.ds.impl

import groovy.util.logging.Slf4j
import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.Family
import nz.co.bookshop.process.activiti.ProcessAction
import nz.co.bookshop.process.activiti.convert.GeneralModelConverter
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

	@Inject
	GeneralModelConverter generalModelConverter

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
		return generalModelConverter.jsonToIdentities(activitiRestClientAccessor.get(PROCESS_DEFINITION_PATH + processDefinitionId+ "/identitylinks"))
	}

	@Override
	Identity addIdentity(final String processDefinitionId,final Family family,final String name) {
		final String requestBody = "{\"" + family.name() + "\" : \"" + name+ "\"}"
		return generalModelConverter.jsonToIdentity(activitiRestClientAccessor.create(PROCESS_DEFINITION_PATH + processDefinitionId+ "/identitylinks", requestBody))
	}

	@Override
	void deleteIdentity(final String processDefinitionId,final Family family,final String identityId) {
		activitiRestClientAccessor.delete(PROCESS_DEFINITION_PATH + processDefinitionId+ "/identitylinks/"+family.name+"/"+identityId)
	}

	@Override
	Identity getIdentity(final String processDefinitionId,final Family family,final String identityId)  {
		return generalModelConverter.jsonToIdentity(activitiRestClientAccessor.get(PROCESS_DEFINITION_PATH + processDefinitionId+ "/identitylinks/"+family.name+"/"+identityId))
	}
}
