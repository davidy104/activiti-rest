package nz.co.bookshop.process.activiti.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["id","url","key","version","name"])
class ProcessDefinition implements Serializable {
	String id;
	String url;
	String key;
	int version;
	String name;
	String description;

	String deploymentId;
	String deploymentUrl;
	String resource;

	String diagramResource;
	String category;
	boolean graphicalNotationDefined;
	boolean suspended;
	boolean startFormDefined;
}
