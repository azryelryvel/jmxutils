package net.azry.jmxutils;

import org.junit.Test;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.ReflectionException;
import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class PrometheusExporterTest {
	private static final String MBEAN_TO_FIND = "java.lang:type=Memory";
	private static final String ATTRIBUTE_TO_FIND = "HeapMemoryUsage";
	private static final String SUBATTRIBUTE_TO_FIND = "committed";


	@Test
	public void export() throws IntrospectionException, ReflectionException, InstanceNotFoundException, IOException {
		JmxHelper helper = new JmxHelper();
		PrometheusExporter exporter = new PrometheusExporter(helper);
		String export = exporter.export();

		String hostname = InetAddress.getLocalHost().getHostName();
		String wanted = "jmx{hostname=\"" + hostname + "\",mbean=\"" + MBEAN_TO_FIND + "\",attribute=\"" + ATTRIBUTE_TO_FIND + "\",subattribute=\"" + SUBATTRIBUTE_TO_FIND + "\"} ";
		assert export.contains(wanted);
	}
}