package net.azry.jmxutils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PrometheusMetricTest {

	@Test
	public void toStringTest() {
		Map<String, String> labels = new HashMap<>();
		labels.put("test1", "1");
		labels.put("test2", "2");
		PrometheusMetric metric = new PrometheusMetric("jmx", labels, "10");
		String out = metric.toString();
		assert out.equals("jmx{test1=\"1\",test2=\"2\"} 10") || out.equals("jmx{test2=\"2\",test1=\"1\"} 10");
	}
}