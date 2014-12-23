package nz.co.bookshop.process.activiti.ds.impl

import groovy.util.logging.Slf4j
import nz.co.bookshop.process.activiti.ActivitiRestClientAccessor
import nz.co.bookshop.process.activiti.Family
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
		return null
	}

	@Override
	ProcessDefinition createProcessDefinition(ProcessDefinition addProcessDefinition) throws Exception {
		return null
	}

	@Override
	ProcessDefinition updateCategory(String processDefinitionId, String category) throws Exception {
		return null
	}

	@Override
	ProcessDefinition suspendProcessDefinition(String processDefinitionId, boolean includeProcessInstances, Date effectiveDate) throws Exception {
		return null
	}

	@Override
	public ProcessDefinition activeProcessDefinition(String processDefinitionId, boolean includeProcessInstances, Date effectiveDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Identity> getAllIdentities(String processDefinitionId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Identity addIdentity(String processDefinitionId, Family family, String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteIdentity(String processDefinitionId, Family family, String identityId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Identity getIdentity(String processDefinitionId, Family family, String identityId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
