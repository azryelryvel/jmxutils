package net.azry.jmxutils;

import javax.management.*;
import java.io.IOException;
import java.util.*;

class JmxHelper {
	private MBeanServerConnection mbs;

	JmxHelper() {
		this.mbs = JmxConnectionFactory.createLocal();
	}

	JmxHelper(String server, int port, String username, String password) throws IOException {
		this.mbs = JmxConnectionFactory.createRemote(server, port, username, password);
	}

	Set<ObjectName> getMBeansList() throws IOException {
		return new TreeSet<>(mbs.queryNames(null, null));
	}

	Map<ObjectName, List<Attribute>> getMbeansAttributesAssociation() throws IOException, IntrospectionException, InstanceNotFoundException, ReflectionException {
		Map<ObjectName, List<Attribute>> out = new HashMap<>();
		Set<ObjectName> names = new TreeSet<>(mbs.queryNames(null, null));

		for (ObjectName name : names) {
			MBeanInfo info = this.mbs.getMBeanInfo(name);

			String[] attributesNames = Arrays.stream(info.getAttributes())
					.map(MBeanAttributeInfo::getName)
					.toArray(String[]::new);

			List<Attribute> attributes = this.mbs.getAttributes(name, attributesNames).asList();
			out.put(name, attributes);
		}
		return out;
	}
}
