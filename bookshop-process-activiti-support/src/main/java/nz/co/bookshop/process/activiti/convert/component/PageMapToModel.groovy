package nz.co.bookshop.process.activiti.convert.component

import java.lang.invoke.MethodHandleImpl.BindCaller.T

import nz.co.bookshop.process.model.Page

import com.google.common.base.Function

class PageMapToModel implements Function<Map<String,String>, Page<T>> {

	@Override
	Page<T> apply(final Map<String, String> inputMap) {
		int pageSize = Integer.valueOf(inputMap['size'])
		int pageOffset = Integer.valueOf(inputMap['start'])
		long totalCount = Long.valueOf(inputMap['total'])
		return new Page(pageSize:pageSize,pageOffset:pageOffset,totalCount:totalCount)
	}
}
