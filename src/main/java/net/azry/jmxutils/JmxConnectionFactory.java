package net.azry.jmxutils;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

class JmxConnectionFactory {
	static MBeanServerConnection createLocal() {
		return ManagementFactory.getPlatformMBeanServer();
	}
	static MBeanServerConnection createRemote(String hostname, int port, String username, String password) throws IOException {
		Map<String, String[]> credentials = new HashMap<>();
		credentials.put(JMXConnector.CREDENTIALS, new String[]{username, password});
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + hostname + ":" + port + "/jmxrmi");
		return JMXConnectorFactory.connect(url, credentials).getMBeanServerConnection();
	}
}
