package br.com.mundialinformatica.reportgen.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.mundialinformatica.reportgen.model.ObjectList;
import br.com.mundialinformatica.reportgen.model.ObjectMap;

public class TemplateMapProducer {
	private Logger LOG = Logger.getLogger(getClass());

	public TemplateMapProducer() {
	}

	public ObjectMap loadTemplateMapFromClass(Object obj) {
		ObjectMap result = new ObjectMap();

		HashMap<String, String> map = new HashMap<String, String>();
		addFields(obj, result, map, obj.getClass().getSimpleName(), false);
		result.setMap(map);
		return result;

	}

	private void addFields(Object obj, ObjectMap objMap,
			HashMap<String, String> map, String alias, boolean isList) {
		Field[] fieldList = obj.getClass().getDeclaredFields();
		for (Field f : fieldList) {
			f.setAccessible(true);
			StringBuilder className = new StringBuilder();
			if (alias != null && !alias.isEmpty()) {
				className.append(alias);
			}

			addFieldToMap(obj, map, f, className.toString(), objMap, isList);

			if (f.isAnnotationPresent(TemplateMapArray.class)) {
				Type listType = f.getGenericType();
				if (listType instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) listType;
					for (Type t : pt.getActualTypeArguments()) {
						try {
							List<?> ii = (List<?>) f.get(obj);
							ObjectList objectList = new ObjectList();
							// List<Map<String, String>> valuesList = new
							// ArrayList<Map<String, String>>();
							for (Object oo : ii) {
								Field[] fieldList2 = oo.getClass()
										.getDeclaredFields();
								HashMap<String, String> ooMap = new HashMap<String, String>();
								for (Field f2 : fieldList2) {
									f2.setAccessible(true);
									String className2 = String.format("%s.%s",
											className, f.getName());
									addFieldToMap(oo, ooMap, f2, className2,
											objMap, true);
								}
								objectList.getValuesList().add(ooMap);
							}
							objMap.getObjectList().add(objectList);
						} catch (Exception e) {
							LOG.error("Erro List Iterator", e);
						}
					}
				}
			}
		}
	}

	private void addFieldToMap(Object obj, HashMap<String, String> map,
			Field f, String clazzName, ObjectMap objMap, boolean isList) {
		if (f.isAnnotationPresent(TemplateMapColumn.class)) {
			TemplateMapColumn ann = f.getAnnotation(TemplateMapColumn.class);
			try {
				String mask = isList ? "${%s.%s}" : "%s.%s";
				String var = String.format(mask, clazzName, f.getName());
				map.put(var, new StringBuilder().append(f.get(obj)).toString());
			} catch (Exception e) {
				LOG.error("Erro load ObjectMapOld", e);
			}
		}

		if (f.isAnnotationPresent(TemplateMapObject.class)) {
			try {
				if (f.get(obj) != null)
					addFields(f.get(obj), objMap, map,
							clazzName + '.' + f.getName(), isList);
			} catch (Exception e) {
				LOG.error("Erro load Nested Object", e);
			}
		}

	}

}
