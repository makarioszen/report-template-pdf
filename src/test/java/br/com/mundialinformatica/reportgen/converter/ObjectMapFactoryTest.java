package br.com.mundialinformatica.reportgen.converter;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;

public class ObjectMapFactoryTest {

	@Test
	public void objectMap() throws Docx4JException {

		String inputfilepath = "D:\\tmp\\variable.zip.docx";
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(inputfilepath));
		
		
		
	}

}
