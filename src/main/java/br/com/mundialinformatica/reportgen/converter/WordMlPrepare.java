package br.com.mundialinformatica.reportgen.converter;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.JaxbXmlPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTSimpleField;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import br.com.mundialinformatica.reportgen.filters.Filter;
import br.com.mundialinformatica.reportgen.filters.FilterCpfCnpj;
import br.com.mundialinformatica.reportgen.filters.FilterCurrency;
import br.com.mundialinformatica.reportgen.filters.FilterDateLong;
import br.com.mundialinformatica.reportgen.filters.FilterDateShort;
import br.com.mundialinformatica.reportgen.filters.FilterExtenso;
import br.com.mundialinformatica.reportgen.filters.FilterMaskFloat;
import br.com.mundialinformatica.reportgen.filters.FilterMaskInteger;
import br.com.mundialinformatica.reportgen.filters.NoFilter;
import br.com.mundialinformatica.reportgen.model.ObjectList;
import br.com.mundialinformatica.reportgen.model.ObjectMap;

public class WordMlPrepare {

	private Logger LOG = Logger.getLogger(WordMlPrepare.class);
	private WordprocessingMLPackage wordMLPackage;
	private final List<ObjectMap> objMapList;

	public WordMlPrepare(String inputfilepath, List<ObjectMap> objMapList)
			throws Docx4JException, JAXBException {
		this.objMapList = objMapList;
		this.wordMLPackage = WordprocessingMLPackage.load(new java.io.File(
				inputfilepath));
	}

	public void setup(boolean mergeFieldInTable) throws Docx4JException,
			JAXBException {
		if (mergeFieldInTable) {
			for (ObjectMap obj : objMapList) {
				replaceTable(obj);
			}
		}

		setMergeFields();
	}

	private String getClearFieldName(CTSimpleField ct) {
		String fieldName = ct.getInstr().replaceAll(
				"(MERGEFIELD)|(MERGEFORMAT)", "");
		fieldName = fieldName.substring(0, fieldName.indexOf('\\') - 1).trim();
		return fieldName;
	}

	private String getClearFieldName(FldChar ct) {
		String fieldName = ct.getFldData().getValue();
		fieldName = fieldName.substring(0, fieldName.indexOf('\\') - 1).trim();
		return fieldName;
	}

	private void setMergeFields() throws Docx4JException {
		org.docx4j.model.fields.merge.MailMerger
				.setMERGEFIELDInOutput(OutputField.DEFAULT);

		org.docx4j.model.fields.merge.MailMerger.performMerge(wordMLPackage,
				getDataMapFields(), true);

	}

	public Map<org.docx4j.model.fields.merge.DataFieldName, String> getDataMapFields() {
		Map<DataFieldName, String> result = new HashMap<DataFieldName, String>();
		JaxbXmlPart docPart = wordMLPackage.getMainDocumentPart();

		List<Object> ctSimpleFieldList = getAllElementFromObject(docPart,
				CTSimpleField.class);
		// List<Object> ctFldCharList = getAllElementFromObject(
		// wordMLPackage.getMainDocumentPart(), FldChar.class);

		RelationshipsPart rp = docPart.getRelationshipsPart();
		for (Relationship r : rp.getJaxbElement().getRelationship()) {

			if (r.getType().equals(Namespaces.HEADER)
					|| r.getType().equals(Namespaces.FOOTER)) {
				JaxbXmlPart part = (JaxbXmlPart) rp.getPart(r);
				List<Object> ctheaderFooterSimple = getAllElementFromObject(
						part, CTSimpleField.class);
				List<Object> ctHeaderFooterFldCharList = getAllElementFromObject(
						part, FldChar.class);
				ctSimpleFieldList.addAll(ctheaderFooterSimple);
				// ctFldCharList.addAll(ctHeaderFooterFldCharList);

			}
		}

		Map<String, String> joinMap = new HashMap<String, String>();
		for (ObjectMap objMap : objMapList) {
			joinMap.putAll(objMap.getMap());
		}

		for (Object object : ctSimpleFieldList) {
			CTSimpleField ct = (CTSimpleField) object;
			String fieldName = getClearFieldName(ct);
			String value = getReplacementValue(joinMap, ct);
			result.put(new DataFieldName(fieldName), value);
		}

		// for (Object object : ctFldCharList) {
		// FldChar ct = (FldChar) object;
		// String fieldName = getClearFieldName(ct);
		// String value = getReplacementValue(joinMap, ct);
		// result.put(new DataFieldName(fieldName), value);
		// }
		return result;
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

	private void replaceTable(ObjectMap objMap) throws Docx4JException,
			JAXBException {
		List<Object> tables = getAllElementFromObject(
				wordMLPackage.getMainDocumentPart(), Tbl.class);

		for (ObjectList list : objMap.getObjectList()) {
			for (String[] placeholder : list.getPlaceholders()) {
				// 1. find the table
				Tbl tempTable = getTemplateTable(tables, placeholder[0]);
				List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
				// first row is header, second row is content
				if (rows.size() == 2) {
					// this is our template row
					Tr templateRow = (Tr) rows.get(1);

					for (Map<String, String> replacements : list
							.getValuesList()) {
						// 2 and 3 are done in this method
						addRowToTable(tempTable, templateRow, replacements);
					}
					// 4. remove the template row
					tempTable.getContent().remove(templateRow);
				}
			}
		}

	}

	private Tbl getTemplateTable(List<Object> tables, String templateKey)
			throws Docx4JException, JAXBException {

		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = getAllElementFromObject(tbl, Text.class);
			for (Object text : textElements) {
				Text textElement = (Text) text;

				// if (textElement.getValue() != null
				// && textElement.getValue().contains(templateKey)) {
				// System.out.println(templateKey);
				// }

				return (Tbl) tbl;
			}
		}

		return null;
	}

	private void addRowToTable(Tbl reviewtable, Tr templateRow,
			Map<String, String> replacements) throws JAXBException {
		Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
		List<?> fieldP = getAllElementFromObject(workingRow, P.class);

		for (Object o : fieldP) {
			P p = (P) o;
			List<Object> listP = new ArrayList<Object>();
			listP.addAll(p.getContent());

			for (Object oTc : listP) {
				try {
					if (oTc instanceof JAXBElement) {
						oTc = ((JAXBElement<?>) oTc).getValue();
					}
					if (oTc.getClass().equals(CTSimpleField.class)) {
						CTSimpleField cts = (CTSimpleField) oTc;
						List<?> runList = getAllElementFromObject(cts, R.class);
						// for (Entry<String, String> rpl : replacements
						// .entrySet()) {
						// System.out.println(rpl.getKey() + ":"
						// + rpl.getValue());
						// }

						String replacementValue =

						getReplacementValue(replacements, cts);
						for (Object run : runList) {
							List<?> textList = getAllElementFromObject(run,
									Text.class);
							for (Object textOb : textList) {
								Text text = (Text) textOb;
								// System.out.println(text.getValue() + ":"
								// + replacementValue);
								text.setValue(replacementValue);
							}
							p.getContent().add(run);
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}

			}
		}

		String workString = XmlUtils.marshaltoString(workingRow, true, false)
				.replaceAll("<(w:fldSimple)*[^>]+?[^>]*>.*?</\\1>", "");
		workingRow = (Tr) XmlUtils.unmarshalString(workString);
		reviewtable.getContent().add(workingRow);
	}

	private String getReplacementValue(Map<String, String> replacements,
			CTSimpleField cts) {
		String replacementValue = "";
		try {
			String fieldName = getClearFieldName(cts);
			Filter filter = extractFilter(fieldName);
			fieldName = fieldName.contains("!") ? fieldName.substring(0,
					fieldName.indexOf('!')) : fieldName;

			replacementValue = filter.getValue((String) replacements
					.get(fieldName));
			return replacementValue;
		} catch (Exception e) {
			LOG.error("getReplacementValue", e);
			return "-";
		}

	}

	private String getReplacementValue(Map<String, String> replacements,
			FldChar ct) {
		String replacementValue = "";
		try {
			String fieldName = getClearFieldName(ct);
			Filter filter = extractFilter(fieldName);
			fieldName = fieldName.contains("!") ? fieldName.substring(0,
					fieldName.indexOf('!')) : fieldName;

			replacementValue = filter.getValue((String) replacements
					.get(fieldName));
			return replacementValue;
		} catch (Exception e) {
			LOG.error("getReplacementValue", e);
			return "-";
		}
	}

	private Filter extractFilter(String fieldName) {

		Filter filter = new NoFilter();
		try {
			String strFilter = fieldName.substring(fieldName.indexOf('!') + 1)
					.toLowerCase();
			if (strFilter.equals("currency")) {
				filter = new FilterCurrency();
			}
			if (strFilter.equals("dateshort")) {
				filter = new FilterDateShort();
			}
			if (strFilter.equals("datelong")) {
				filter = new FilterDateLong();
			}
			if (strFilter.equals("extenso")) {
				filter = new FilterExtenso();
			}
			if (strFilter.contains("maskinteger")) {
				filter = new FilterMaskInteger(strFilter);
			}
			if (strFilter.contains("maskfloat")) {
				filter = new FilterMaskFloat(strFilter);
			}
			if (strFilter.contains("cpfcnpj")) {
				filter = new FilterCpfCnpj();
			}
		} catch (Exception e) {
			LOG.error("No Filter select", e);
		}
		return filter;
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
