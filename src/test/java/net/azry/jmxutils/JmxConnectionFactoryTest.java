package net.azry.jmxutils;

import org.junit.Test;

import javax.management.MBeanServerConnection;

import static org.junit.Assert.*;

public class JmxConnectionFactoryTest {

	@Test
	public void createLocal() {
		assert JmxConnectionFactory.createLocal() != null;
	}
}