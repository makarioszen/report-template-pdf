package br.com.mundialinformatica.reportgen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectMap {
	private HashMap<String, String> map;
	private List<ObjectList> objectList;
	private String objectName;

	public ObjectMap() {
		objectList = new ArrayList<ObjectList>();
		map = new HashMap<String, String>();
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

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

}
