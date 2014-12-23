package nz.co.bookshop.process.activiti.ds

import nz.co.bookshop.process.activiti.Family
import nz.co.bookshop.process.activiti.model.Identity
import nz.co.bookshop.process.activiti.model.ProcessDefinition
import nz.co.bookshop.process.activiti.model.ProcessDefinitionQueryParameter
import nz.co.bookshop.process.model.Page

interface ProcessDefinitionDS {
	Page<ProcessDefinition> paginateProcessDefinition(Map<ProcessDefinitionQueryParameter,String> queryParameters,Integer pageOffset,Integer pageSize)
	ProcessDefinition createProcessDefinition(ProcessDefinition addProcessDefinition)throws Exception
	ProcessDefinition updateCategory(String processDefinitionId, String category) throws Exception
	ProcessDefinition suspendProcessDefinition(String processDefinitionId,boolean includeProcessInstances, Date effectiveDate) throws Exception
	ProcessDefinition activeProcessDefinition(String processDefinitionId,boolean includeProcessInstances, Date effectiveDate)throws Exception
	Set<Identity> getAllIdentities(String processDefinitionId) throws Exception
	Identity addIdentity(String processDefinitionId, Family family, String name) throws Exception

	/**
	 *
	 * @param processDefinitionId
	 * @param family
	 *            Either users or groups, depending on the type of identity
	 *            link.
	 * @param identityId
	 *            Either the userId or groupId of the identity to remove as
	 *            candidate starter
	 * @return
	 * @throws Exception
	 */
	void deleteIdentity(String processDefinitionId, Family family,String identityId) throws Exception
	Identity getIdentity(String processDefinitionId, Family family, String identityId) throws Exception
}
