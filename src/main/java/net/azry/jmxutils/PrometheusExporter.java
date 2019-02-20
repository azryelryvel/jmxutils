package net.azry.jmxutils;


import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

class PrometheusExporter {
	private JmxHelper helper;

	PrometheusExporter(JmxHelper helper) {
		this.helper = helper;
	}

	String export() throws IntrospectionException, ReflectionException, InstanceNotFoundException, IOException {
		Map<ObjectName, List<Attribute>> associations = helper.getMbeansAttributesAssociation();

		List<PrometheusMetric> metrics = new ArrayList<>();

		for (ObjectName objectName : associations.keySet()) {
			for (Attribute attribute : associations.get(objectName)) {
				if (attribute.getValue() != null) {
					Map<String, String> labels = new HashMap<>();
					labels.put("hostname", InetAddress.getLocalHost().getHostName());
					labels.put("mbean", objectName.toString());
					labels.put("attribute", attribute.getName());

					if (attribute.getValue() instanceof CompositeDataSupport) {
						Set<String> subAttributes = ((CompositeDataSupport)attribute.getValue()).getCompositeType().keySet();
						for (String subAttributeName : subAttributes) {
							String subAttributeValue = sanitizeValue(((CompositeDataSupport)attribute.getValue()).get(subAttributeName));
							if (subAttributeValue != null) {
								Map<String, String> currentLabels = new HashMap<>(labels);
								currentLabels.put("subattribute", subAttributeName);
								metrics.add(new PrometheusMetric("jmx", currentLabels, subAttributeValue));
							}
						}
					} else {
						String attributeValue = sanitizeValue(attribute.getValue());
						if (attributeValue != null) {
							metrics.add(new PrometheusMetric("jmx", labels, attributeValue));
						}
					}
				}
			}
		}
		return metrics.stream()
				.map(PrometheusMetric::toString)
				.collect(Collectors.joining("\n"));
	}

	private static String sanitizeValue(Object value) {
		if (value instanceof Boolean) {
			return ((boolean) value) ? "1" : "0";
		} else if (value instanceof Long) {
			return value.toString();
		}
		try {
			double doubleValue = Double.parseDouble(value.toString());
			return Double.toString(doubleValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
