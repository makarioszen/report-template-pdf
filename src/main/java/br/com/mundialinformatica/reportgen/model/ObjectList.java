package br.com.mundialinformatica.reportgen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectList {
	private List<Map<String, String>> valuesList;

	public ObjectList() {
		this.valuesList = new ArrayList<Map<String, String>>();
	}

	public List<String[]> getPlaceholders() {
		List<String[]> result = new ArrayList<String[]>();
		for (Map<String, String> values : valuesList) {
			String[] yArr = new String[values.size()];
			result.add(yArr);
			int y = 0;
			for (Map.Entry<String, String> entry : values.entrySet()) {
				yArr[y++] = entry.getKey();
			}
		}
		return result;
	}

	public List<Map<String, String>> getValuesList() {
		return valuesList;
	}

	public void setValuesList(List<Map<String, String>> valuesList) {
		this.valuesList = valuesList;
	}

}
