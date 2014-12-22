package nz.co.bookshop.process.activiti.convert.component;

import groovy.util.logging.Slf4j;

import java.util.Map;

import com.amazonaws.services.identitymanagement.model.User
import com.google.common.base.Function

@Slf4j
public class UserMapToModel implements Function<Map<String,String>, User> {

	@Override
	User apply(final Map<String, String> inputMap) {
		return new User(
		id:inputMap['id'],
		url:inputMap['url'],
		firstName:inputMap['firstName'],
		lastName:inputMap['lastName'],
		email:inputMap['email']
		)
	}
}
