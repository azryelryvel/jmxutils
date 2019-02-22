package net.azry.jmxutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrometheusMetric {
	private String name;
	private Map<String, String> labels;
	private String value;

	PrometheusMetric(String name, Map<String, String> labels, String value) {
		this.name = name;
		this.labels = labels;
		this.value = value;
	}

	private String asString() {
		List<String> labelsString = new ArrayList<>();
		for (String key : labels.keySet()) {
			String value = labels.get(key).replaceAll("\"", "\\\"");
			labelsString.add(key + "=\"" + value + "\"");
		}
		return name + "{" + String.join(",", labelsString) + "} " + value;
	}

	@Override
	public String toString() {
		return this.asString();
	}

}
