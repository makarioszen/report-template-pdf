package br.com.mundialinformatica.reportgen.converter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.OpcPackage;

public class ToHtmlConverter {

	private OpcPackage woWorlMlPackage;

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

	// For demo/debugging purposes, save the intermediate XSL FO
	// Don't do this in production!

	public ToHtmlConverter(OpcPackage woWorlMlPackage) {
		this.woWorlMlPackage = woWorlMlPackage;
	}

	public void save(String outputfilepath) throws Exception {

		HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

    	htmlSettings.setImageDirPath(outputfilepath + "_files");
    	htmlSettings.setImageTargetUri(outputfilepath.substring(outputfilepath.lastIndexOf("/")+1)
    			+ "_files");
    	htmlSettings.setWmlPackage(woWorlMlPackage);
    	
    	
    	/* CSS reset, see http://itumbcom.blogspot.com.au/2013/06/css-reset-how-complex-it-should-be.html 
    	 * 
    	 * motivated by vertical space in tables in Firefox and Google Chrome.
        
	        If you have unwanted vertical space, in Chrome this may be coming from -webkit-margin-before and -webkit-margin-after
	        (in Firefox, margin-top is set to 1em in html.css)
	        
	        Setting margin: 0 on p is enough to fix it.
	        
	        See further http://www.css-101.org/articles/base-styles-sheet-for-webkit-based-browsers/    	
    	*/
    	String userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
    			"{ margin: 0; padding: 0; border: 0;}" +
    			"body {line-height: 1;} ";
    	htmlSettings.setUserCSS(userCSS);
    	
    	
    	//Other settings (optional)
//    	htmlSettings.setUserBodyTop("<H1>TOP!</H1>");
//    	htmlSettings.setUserBodyTail("<H1>TAIL!</H1>");
		
		// Sample sdt tag handler (tag handlers insert specific
		// html depending on the contents of an sdt's tag).
		// This will only have an effect if the sdt tag contains
		// the string @class=XXX
//			SdtWriter.registerTagHandler("@class", new TagClass() );
		
//		SdtWriter.registerTagHandler(Containerization.TAG_BORDERS, new TagSingleBox() );
//		SdtWriter.registerTagHandler(Containerization.TAG_SHADING, new TagSingleBox() );
		
		// output to an OutputStream.		
		OutputStream os; 
		boolean save = true;
		if (save ) {
			os = new FileOutputStream(outputfilepath + ".html");
		} else {
			os = new ByteArrayOutputStream();
		}

		// If you want XHTML output
    	Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);

		//Don't care what type of exporter you use
//		Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);
		//Prefer the exporter, that uses a xsl transformation
		Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
		//Prefer the exporter, that doesn't use a xsl transformation (= uses a visitor)
//		Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_NONXSL);
	}

}
