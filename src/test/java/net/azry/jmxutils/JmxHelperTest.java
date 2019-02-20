/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package net.azry.jmxutils;

import org.junit.Test;

import javax.management.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class JmxHelperTest {
	private static final String MBEAN_TO_FIND = "java.lang:type=Memory";
	private static final String ATTRIBUTE_TO_FIND = "HeapMemoryUsage";

    @Test
    public void getMBeansList() throws IOException {
	    JmxHelper helper = new JmxHelper();
		assert helper.getMBeansList().stream()
				.map(ObjectName::toString)
				.collect(Collectors.toList())
				.contains(MBEAN_TO_FIND);
    }

	@Test
	public void getMbeansAttributesAssociation() throws IntrospectionException, ReflectionException, InstanceNotFoundException, IOException {
		JmxHelper helper = new JmxHelper();
		Map<ObjectName, List<Attribute>> mbeansAttributesAssociation = helper.getMbeansAttributesAssociation();

		Optional<ObjectName> mbean = mbeansAttributesAssociation.keySet().stream()
				.filter(currentMbean -> currentMbean.toString().equals(MBEAN_TO_FIND))
				.findFirst();
		assert mbean.isPresent();

		Optional<Attribute> attribute = mbeansAttributesAssociation.get(mbean.get()).stream()
				.filter(currentAttribute -> currentAttribute.getName().equals(ATTRIBUTE_TO_FIND))
				.findFirst();
		assert attribute.isPresent();
	}
}
