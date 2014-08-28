package br.com.mundialinformatica.reportgen.converter;

import org.junit.Test;

public class ToConvertersTest {

	@Test
	public void geraHtml1() throws Exception {
		String[] input = new String[] { "D:\\tmp\\Images.docx" };
		ToHtmlConverter.main(input);
	}

	@Test
	public void geraPdf1() throws Exception {
		String[] input = new String[] { "D:\\tmp\\Images.docx" };
		ToPdfConverter.main(input);
	}

}
