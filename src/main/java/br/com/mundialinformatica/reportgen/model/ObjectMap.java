package br.com.mundialinformatica.reportgen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.docx4j.model.fields.merge.DataFieldName;

public class ObjectMap {
	// private List<Map<String, String>> valuesList;
	private HashMap<String, String> map;

	private List<ObjectList> objectList;

	public ObjectMap() {
		objectList = new ArrayList<ObjectList>();
		map = new HashMap<String, String>();
	}

	public Map<DataFieldName, String> getDateMapFields() {
		Map<DataFieldName, String> result = new HashMap<DataFieldName, String>();
		for (Entry<String, String> entry : map.entrySet()) {
			result.put(new DataFieldName(entry.getKey()), entry.getValue());
		}
		return result;

	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public List<ObjectList> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<ObjectList> objectList) {
		this.objectList = objectList;
	}

}
