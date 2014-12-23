package nz.co.bookshop.process.activiti.convert

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import nz.co.bookshop.process.activiti.OperationType
import nz.co.bookshop.process.activiti.convert.component.GroupMapToModel
import nz.co.bookshop.process.activiti.convert.component.MembershipMapToModel
import nz.co.bookshop.process.activiti.convert.component.PageMapToModel
import nz.co.bookshop.process.activiti.model.Group;
import nz.co.bookshop.process.activiti.model.MemberShip;
import nz.co.bookshop.process.model.Page

import com.google.inject.Inject

class GroupConverter {
	@Inject
	JsonSlurper jsonSlurper

	@Inject
	JsonBuilder jsonBuilder

	@Inject
	GroupMapToModel groupMapToModel

	@Inject
	PageMapToModel pageMapToModel

	@Inject
	MembershipMapToModel MembershipMapToModel

	MemberShip jsonToMemberShip(final String jsonText){
		return MembershipMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Group jsonToGroup(final String jsonText){
		return groupMapToModel.apply((Map)jsonSlurper.parseText(jsonText))
	}

	Set<Group> jsonToGroups(final String jsonText){
		Set<Group> groups = []
		List resultList = (List)((Map)jsonSlurper.parseText(jsonText))['data']
		if(resultList){
			resultList.each {
				groups << groupMapToModel.apply((Map)it)
			}
		}
		return groups
	}

	Page jsonToGroupPage(final String jsonText){
		Map resultMap = (Map)jsonSlurper.parseText(jsonText)
		Page page = pageMapToModel.apply(resultMap)
		List resultList = resultMap['data']
		if(resultList){
			resultList.each {
				page.content << groupMapToModel.apply((Map)it)
			}
		}
		return page
	}

	String groupToJson(final Group group,final OperationType operationType){
		jsonBuilder{
			if(operationType == OperationType.CREATION){
				id group.id
			}
			name group.name
			type group.type
		}
		return jsonBuilder.toString()
	}
}
