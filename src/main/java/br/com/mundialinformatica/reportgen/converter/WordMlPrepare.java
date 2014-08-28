package br.com.mundialinformatica.reportgen.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.docx4j.XmlUtils;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import br.com.mundialinformatica.reportgen.model.ObjectMapOld;

public class WordMlPrepare {

	private Logger LOG = Logger.getLogger(WordMlPrepare.class);
	private WordprocessingMLPackage wordMLPackage;
	private final ObjectMapOld objMap;

	public WordMlPrepare(String inputfilepath, ObjectMapOld objMap)
			throws Docx4JException, JAXBException {
		this.objMap = objMap;
		this.wordMLPackage = WordprocessingMLPackage.load(new java.io.File(
				inputfilepath));
		setup();
	}

	private void setup() throws Docx4JException, JAXBException {
		replaceTable();
		setVariables();
	}

	public OpcPackage getWorlMlPackage() throws Exception {
		String regex = null;
		// Windows:
		// String
		// regex=".*(calibri|camb|cour|arial|symb|times|Times|zapf).*";
		regex = ".*(calibri|camb|cour|arial|times|comic|georgia|impact|LSANS|pala|tahoma|trebuc|verdana|symbol|webdings|wingding).*";
		// Mac
		// String
		// regex=".*(Courier New|Arial|Times New Roman|Comic Sans|Georgia|Impact|Lucida Console|Lucida Sans Unicode|Palatino Linotype|Tahoma|Trebuchet|Verdana|Symbol|Webdings|Wingdings|MS Sans Serif|MS Serif).*";
		PhysicalFonts.setRegex(regex);
		Mapper fontMapper = new IdentityPlusMapper();
		wordMLPackage.setFontMapper(fontMapper);
		PhysicalFont font = PhysicalFonts.getPhysicalFonts().get(
				"Arial Unicode MS");
		// make sure this is in your regex (if any)!!!
		if (font != null) {
			fontMapper.getFontMappings().put("Times New Roman", font);
		}
		fontMapper.getFontMappings().put("Libian SC Regular",
				PhysicalFonts.getPhysicalFonts().get("SimSun"));
		return wordMLPackage;
	}

	public WordprocessingMLPackage getWordMLPackage() {
		return wordMLPackage;
	}

	@SuppressWarnings("restriction")
	private void setVariables() throws JAXBException, Docx4JException {
		MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
		documentPart.variableReplace(objMap.getMap());
	}

	private void replaceTable() throws Docx4JException, JAXBException {
		List<Object> tables = getAllElementFromObject(
				wordMLPackage.getMainDocumentPart(), Tbl.class);

		// 1. find the table
		Tbl tempTable = getTemplateTable(tables, objMap.getPlaceholders()[0]);
		List<Object> rows = getAllElementFromObject(tempTable, Tr.class);

		// first row is header, second row is content
		if (rows.size() == 2) {
			// this is our template row
			Tr templateRow = (Tr) rows.get(1);

			for (Map<String, String> replacements : objMap.getValuesList()) {
				// 2 and 3 are done in this method
				addRowToTable(tempTable, templateRow, replacements);
			}
			// 4. remove the template row
			tempTable.getContent().remove(templateRow);
		}
	}

	private Tbl getTemplateTable(List<Object> tables, String templateKey)
			throws Docx4JException, JAXBException {
		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = getAllElementFromObject(tbl, Text.class);
			for (Object text : textElements) {
				Text textElement = (Text) text;
				if (textElement.getValue() != null
						&& textElement.getValue().equals(templateKey))
					return (Tbl) tbl;
			}
		}
		return null;
	}

	private void addRowToTable(Tbl reviewtable, Tr templateRow,
			Map<String, String> replacements) {
		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
		List<?> textElements = getAllElementFromObject(workingRow, Text.class);
		for (Object object : textElements) {
			Text text = (Text) object;
			String replacementValue = (String) replacements
					.get(text.getValue());
			if (replacementValue != null)
				text.setValue(replacementValue);
		}
		reviewtable.getContent().add(workingRow);
	}

	private List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement)
			obj = ((JAXBElement<?>) obj).getValue();

		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
		}
		return result;
	}

}
