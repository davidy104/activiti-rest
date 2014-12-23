package nz.co.bookshop.process.test;

import org.junit.Test

import com.google.common.collect.Maps

public class EnumTest {

	@Test
	public void test() {
		Map<QueryParameters, String> enumQueryMap = Maps.<QueryParameters, String> newHashMap();
		enumQueryMap.put(DeploymentQueryParameter.categoryNotEquals, "test")
		
		enumQueryMap.each {k,v->
			println "${k.name()} -- $v"
		}
	}

	private static enum DeploymentQueryParameter implements QueryParameters {
		name, nameLike, category, categoryNotEquals, tenantId, tenantIdLike, withoutTenantId, sort
	}

	private interface QueryParameters {
		String name();
	}
}
