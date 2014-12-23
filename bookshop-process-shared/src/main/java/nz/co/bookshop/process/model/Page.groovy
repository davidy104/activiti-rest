package nz.co.bookshop.process.model

import groovy.transform.ToString

import com.fasterxml.jackson.annotation.JsonIgnore

@ToString(includeNames = true, includeFields=true)
class Page {
	long totalCount
	int totalPages
	int pageOffset
	int pageSize
	List content =[]
	@JsonIgnore
	def metaContent
}
