package nz.co.bookshop.process.test.activitids;

import static org.hamcrest.CoreMatchers.is
import static org.junit.Assert.*
import groovy.util.logging.Slf4j
import nz.co.bookshop.process.SharedModule
import nz.co.bookshop.process.activiti.ActivitiSupportModule
import nz.co.bookshop.process.activiti.ds.UserDS
import nz.co.bookshop.process.activiti.model.User;
import nz.co.bookshop.process.config.ConfigurationServiceModule
import nz.co.bookshop.process.test.GuiceJUnitRunner
import nz.co.bookshop.process.test.GuiceJUnitRunner.GuiceModules

import org.junit.AfterClass
import org.junit.Assume
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

import com.google.inject.Inject
@RunWith(GuiceJUnitRunner.class)
@GuiceModules([ConfigurationServiceModule.class, SharedModule.class, ActivitiSupportModule.class ])
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserDSIntegrationTest {

	@Inject
	UserDS userDs

	static String testUserId

	static final String USER_PIC="/david.jpg"

	static InputStream picStream

	@BeforeClass
	static void setUp() {
		picStream = UserDSIntegrationTest.class.getResourceAsStream(USER_PIC)
	}

	@AfterClass
	static void clear(){
		if(picStream){
			picStream.close()
		}
	}

	@Test
	void create() {
		User addUser = userDs.createUser(new User(id:'david',firstName:'yuan',lastName:'david',password:'123456',email:'david.yuan@gmail.com'))
		assertNotNull(addUser)
		assertThat(addUser.firstName, is('yuan'))
		assertThat(addUser.lastName, is('david'))
		assertThat(addUser.email, is('david.yuan@gmail.com'))
		testUserId = addUser.id
	}

	@Test
	void getUserById(){
		Assume.assumeNotNull(testUserId)
		User foundUser = userDs.getUserById(testUserId)
		assertNotNull(foundUser)
		assertThat(foundUser.firstName, is('yuan'))
		assertThat(foundUser.lastName, is('david'))
		assertThat(foundUser.email, is('david.yuan@gmail.com'))
	}

	@Test
	void importPicForUser(){
		assertNotNull(picStream)
		userDs.updateUsersPicture("david", picStream)
	}

	@Test
	void remove(){
		Assume.assumeNotNull(testUserId)
		userDs.deleteUser(testUserId)
	}
}
