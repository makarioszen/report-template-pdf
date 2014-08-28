package br.com.mundialinformatica.reportgen.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectMapOld {
	private String[] placeholders;
	private List<Map<String, String>> valuesList;
	private HashMap<String, String> map;

	public String[] getPlaceholders() {
		return placeholders != null ? placeholders : new String[] {
				"${produto}", "${valor}", "${quantidade}" };
	}

	public List<Map<String, String>> getValuesList() {
		Map<String, String> repl1 = new HashMap<String, String>();
		repl1.put("${produto}", "Bola quadrada do Kiko");
		repl1.put("${valor}", "R$150,00");
		repl1.put("${quantidade}", "1");
		Map<String, String> repl2 = new HashMap<String, String>();
		repl2.put("${produto}", "Sanduíche de Mortadela do Chaves");
		repl2.put("${valor}", "R$15,00");
		repl2.put("${quantidade}", "91");
		return valuesList != null ? valuesList : Arrays.asList(repl1, repl2);
	}

	public HashMap<String, String> getMap() {
		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("nome", "João Marimbondo");
		mappings.put("endereco", "Rua da Jacutinga");
		return map != null ? map : mappings;
	}

	public void setPlaceholders(String[] placeholders) {
		this.placeholders = placeholders;
	}

	public void setValuesList(List<Map<String, String>> valuesList) {
		this.valuesList = valuesList;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

}
