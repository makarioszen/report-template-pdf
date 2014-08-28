package br.com.mundialinformatica.reportgen.converter;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;

import br.com.mundialinformatica.reportgen.model.ObjectMapOld;

public class WordMlPrepareTest {
	private String fileTest = "D:\\tmp\\variable.zip.docx";

	@Test
	public void substituiTexto() throws Docx4JException, JAXBException {
		ObjectMapOld objMap = new ObjectMapOld();
		WordMlPrepare wordPrepare = new WordMlPrepare(fileTest, objMap);
		WordprocessingMLPackage wordMLPackage = wordPrepare.getWordMLPackage();
		wordMLPackage.save(new java.io.File(fileTest + ".merge.docx"));
	}

}
