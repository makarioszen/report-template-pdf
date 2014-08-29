package br.com.mundialinformatica.reportgen.converter;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;

import br.com.mundialinformatica.reportgen.annotation.TemplateMapProducer;
import br.com.mundialinformatica.reportgen.model.ObjectList;
import br.com.mundialinformatica.reportgen.model.ObjectMap;
import br.com.mundialinformatica.reportgen.model.sample.Cliente;

public class WordMlPrepareTest {
	private String fileTest = "D:\\tmp\\variable.zip.docx";

	@Test
	public void substituiTexto() throws Docx4JException, JAXBException {
		TemplateMapProducer tt = new TemplateMapProducer();
		Object cliente = new Cliente();
		ObjectMap om = tt.loadTemplateMapFromClass(cliente);
		WordMlPrepare wordPrepare = new WordMlPrepare(fileTest, om);
		WordprocessingMLPackage wordMLPackage = wordPrepare.getWordMLPackage();
		wordMLPackage.save(new java.io.File(fileTest + ".merge.docx"));
	}

	@Test
	public void loadObjectMapFromClass() {
		TemplateMapProducer tt = new TemplateMapProducer();
		Object cliente = new Cliente();
		ObjectMap om = tt.loadTemplateMapFromClass(cliente);
		// for (Map.Entry<String, String> entry : om.getMap().entrySet()) {
		// System.out.println("Key map: " + entry.getKey() + " Value : "
		// + entry.getValue());
		// }
		// for (Map<String, String> map : om.getValuesList()) {
		// for (Map.Entry<String, String> entry : map.entrySet()) {
		// System.out.println("Key list: " + entry.getKey() + " Value : "
		// + entry.getValue());
		// }
		// }

		for (ObjectList ll : om.getObjectList()) {
			System.out.println(ll.getValuesList().size());
		}

	}

}
