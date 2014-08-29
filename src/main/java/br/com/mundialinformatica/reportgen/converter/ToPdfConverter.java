package br.com.mundialinformatica.reportgen.converter;

import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.samples.AbstractSample;

import br.com.mundialinformatica.reportgen.model.ObjectMap;

public class ToPdfConverter extends AbstractSample {

	/*
	 * NOT WORKING?
	 * 
	 * If you are getting:
	 * 
	 * "fo:layout-master-set" must be declared before "fo:page-sequence"
	 * 
	 * please check:
	 * 
	 * 1. the jaxb-xslfo jar is on your classpath
	 * 
	 * 2. that there is no stack trace earlier in the logs
	 * 
	 * 3. your JVM has adequate memory, eg
	 * 
	 * -Xmx1G -XX:MaxPermSize=128m
	 */

	// Config for non-command line use
	static {

		inputfilepath = null; // to generate a docx (and PDF output) containing
								// font samples

		inputfilepath = System.getProperty("user.dir")
				+ "/sample-docs/word/sample-docx.docx";

		saveFO = true;
	}

	// For demo/debugging purposes, save the intermediate XSL FO
	// Don't do this in production!
	static boolean saveFO;

	public static void main(String[] args) throws Exception {

		try {
			getInputFilePath(args);
		} catch (IllegalArgumentException e) {
		}

		FOSettings foSettings = Docx4J.createFOSettings();
		if (saveFO) {
			foSettings.setFoDumpFile(new java.io.File(inputfilepath + ".fo"));
		}

		ObjectMap objMap = new ObjectMap();
		WordMlPrepare wordPrepare = new WordMlPrepare(inputfilepath, objMap);
		foSettings.setWmlPackage(wordPrepare.getWorlMlPackage());

		String outputfilepath;
		if (inputfilepath == null) {
			outputfilepath = System.getProperty("user.dir")
					+ "/OUT_FontContent.pdf";
		} else {
			outputfilepath = inputfilepath + ".pdf";
		}
		OutputStream os = new java.io.FileOutputStream(outputfilepath);

		// Specify whether PDF export uses XSLT or not to create the FO
		// (XSLT takes longer, but is more complete).

		// Don't care what type of exporter you use
		Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

		// Prefer the exporter, that uses a xsl transformation
		// Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

		// Prefer the exporter, that doesn't use a xsl transformation (= uses a
		// visitor)
		// .. faster, but not yet at feature parity
		// Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_NONXSL);

		System.out.println("Saved: " + outputfilepath);
	}

}
