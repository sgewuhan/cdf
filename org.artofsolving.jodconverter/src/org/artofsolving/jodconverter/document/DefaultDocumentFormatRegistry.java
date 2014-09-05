//
// JODConverter - Java OpenDocument Converter
// Copyright 2004-2011 Mirko Nasato and contributors
//
// JODConverter is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// JODConverter is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General
// Public License along with JODConverter.  If not, see
// <http://www.gnu.org/licenses/>.
//
package org.artofsolving.jodconverter.document;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultDocumentFormatRegistry extends SimpleDocumentFormatRegistry {


	public DefaultDocumentFormatRegistry() {
		DocumentFormat pdf = new DocumentFormat(PDF_TYPENAME, PDF, PDF_MEDIA);
		pdf.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "writer_pdf_Export"));
		pdf.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "calc_pdf_Export"));
		pdf.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "impress_pdf_Export"));
		pdf.setStoreProperties(DocumentFamily.DRAWING, Collections.singletonMap("FilterName", "draw_pdf_Export"));
		addFormat(pdf);
		
		DocumentFormat swf = new DocumentFormat(SWF_TYPENAME, SWF, SWF_MEDIA);
		swf.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "impress_flash_Export"));
		swf.setStoreProperties(DocumentFamily.DRAWING, Collections.singletonMap("FilterName", "draw_flash_Export"));
		addFormat(swf);
		
		// disabled because it's not always available
		//DocumentFormat xhtml = new DocumentFormat("XHTML", "xhtml", "application/xhtml+xml");
		//xhtml.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "XHTML Writer File"));
		//xhtml.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "XHTML Calc File"));
		//xhtml.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "XHTML Impress File"));
		//addFormat(xhtml);

		DocumentFormat html = new DocumentFormat(HTML_TYPENAME, HTML, HTML_MEDIA);
        // HTML is treated as Text when supplied as input, but as an output it is also
        // available for exporting Spreadsheet and Presentation formats
		html.setInputFamily(DocumentFamily.TEXT);
		html.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "HTML (StarWriter)"));
		html.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "HTML (StarCalc)"));
		html.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "impress_html_Export"));
		addFormat(html);
		
		DocumentFormat odt = new DocumentFormat(ODT_TYPENAME, ODT, ODT_MEDIA);
		odt.setInputFamily(DocumentFamily.TEXT);
		odt.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "writer8"));
		addFormat(odt);

		DocumentFormat sxw = new DocumentFormat(SXW_TYPENAME, SXW, SXW_MEDIA);
		sxw.setInputFamily(DocumentFamily.TEXT);
		sxw.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "StarOffice XML (Writer)"));
		addFormat(sxw);

		DocumentFormat doc = new DocumentFormat(DOC_TYPENAME, DOC, DOC_MEDIA);
		doc.setInputFamily(DocumentFamily.TEXT);
		doc.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "MS Word 97"));
		addFormat(doc);

		DocumentFormat docx = new DocumentFormat(DOCX_TYPENAME, DOCX, DOCX_MEDIA);
		docx.setInputFamily(DocumentFamily.TEXT);
        addFormat(docx);

		DocumentFormat rtf = new DocumentFormat(RTF_TYPENAME, RTF, RFT_MEDIA);
		rtf.setInputFamily(DocumentFamily.TEXT);
		rtf.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", RTF_TYPENAME));
		addFormat(rtf);

		DocumentFormat wpd = new DocumentFormat(WPD_TYPENAME, WPD, WPD_MEDIA);
		wpd.setInputFamily(DocumentFamily.TEXT);
		addFormat(wpd);

		DocumentFormat txt = new DocumentFormat(TXT_TYPENAME, TXT, TXT_MEDIA);
		txt.setInputFamily(DocumentFamily.TEXT);
		Map<String,Object> txtLoadAndStoreProperties = new LinkedHashMap<String,Object>();
		txtLoadAndStoreProperties.put("FilterName", "Text (encoded)");
		txtLoadAndStoreProperties.put("FilterOptions", "utf8");
		txt.setLoadProperties(txtLoadAndStoreProperties);
		txt.setStoreProperties(DocumentFamily.TEXT, txtLoadAndStoreProperties);
		addFormat(txt);

        DocumentFormat wikitext = new DocumentFormat(WIKI_TYPENAME, WIKI, WIKI_MEDIA);
        wikitext.setStoreProperties(DocumentFamily.TEXT, Collections.singletonMap("FilterName", "MediaWiki"));
        //addFormat(wikitext);
		
		DocumentFormat ods = new DocumentFormat(ODS_TYPENAME, ODS, ODS_MEDIA);
		ods.setInputFamily(DocumentFamily.SPREADSHEET);
		ods.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "calc8"));
		addFormat(ods);

		DocumentFormat sxc = new DocumentFormat(SXC_TYPENAME, SXC, SXC_MEDIA);
		sxc.setInputFamily(DocumentFamily.SPREADSHEET);
		sxc.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "StarOffice XML (Calc)"));
		addFormat(sxc);

		DocumentFormat xls = new DocumentFormat(XLS_TYPENAME, XLS, XLS_MEDIA);
		xls.setInputFamily(DocumentFamily.SPREADSHEET);
		xls.setStoreProperties(DocumentFamily.SPREADSHEET, Collections.singletonMap("FilterName", "MS Excel 97"));
		addFormat(xls);

		DocumentFormat xlsx = new DocumentFormat(XLSX_TYPENAME, XLSX, XLSX_MEDIA);
		xlsx.setInputFamily(DocumentFamily.SPREADSHEET);
        addFormat(xlsx);

        DocumentFormat csv = new DocumentFormat(CSV_TYPENAME, CSV, CSV_MEDIA);
        csv.setInputFamily(DocumentFamily.SPREADSHEET);
        Map<String,Object> csvLoadAndStoreProperties = new LinkedHashMap<String,Object>();
        csvLoadAndStoreProperties.put("FilterName", "Text - txt - csv (StarCalc)");
        csvLoadAndStoreProperties.put("FilterOptions", "44,34,0");  // Field Separator: ','; Text Delimiter: '"' 
        csv.setLoadProperties(csvLoadAndStoreProperties);
        csv.setStoreProperties(DocumentFamily.SPREADSHEET, csvLoadAndStoreProperties);
        addFormat(csv);

        DocumentFormat tsv = new DocumentFormat(TSV_TYPENAME, TSV, TSV_MEDIA);
        tsv.setInputFamily(DocumentFamily.SPREADSHEET);
        Map<String,Object> tsvLoadAndStoreProperties = new LinkedHashMap<String,Object>();
        tsvLoadAndStoreProperties.put("FilterName", "Text - txt - csv (StarCalc)");
        tsvLoadAndStoreProperties.put("FilterOptions", "9,34,0");  // Field Separator: '\t'; Text Delimiter: '"' 
        tsv.setLoadProperties(tsvLoadAndStoreProperties);
        tsv.setStoreProperties(DocumentFamily.SPREADSHEET, tsvLoadAndStoreProperties);
        addFormat(tsv);

		DocumentFormat odp = new DocumentFormat(ODP_TYPENAME, ODP, ODP_MEDIA);
		odp.setInputFamily(DocumentFamily.PRESENTATION);
		odp.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "impress8"));
		addFormat(odp);

		DocumentFormat sxi = new DocumentFormat(SXI_TYPENAME, SXI, SXI_MEDIA);
		sxi.setInputFamily(DocumentFamily.PRESENTATION);
		sxi.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "StarOffice XML (Impress)"));
		addFormat(sxi);

		DocumentFormat ppt = new DocumentFormat(PPT_TYPENAME, PPT, PPT_MEDIA);
		ppt.setInputFamily(DocumentFamily.PRESENTATION);
		ppt.setStoreProperties(DocumentFamily.PRESENTATION, Collections.singletonMap("FilterName", "MS PowerPoint 97"));
		addFormat(ppt);

		DocumentFormat pptx = new DocumentFormat(PPTX_TYPENAME, PPTX, PPTX_MEDIA);
		pptx.setInputFamily(DocumentFamily.PRESENTATION);
        addFormat(pptx);
        
        DocumentFormat odg = new DocumentFormat(ODG_TYPENAME, ODG, ODG_MEDIA);
        odg.setInputFamily(DocumentFamily.DRAWING);
        odg.setStoreProperties(DocumentFamily.DRAWING, Collections.singletonMap("FilterName", "draw8"));
        addFormat(odg);
        
        DocumentFormat svg = new DocumentFormat(SVG_TYPENAME, SVG, SVG_MEDIA);
        svg.setStoreProperties(DocumentFamily.DRAWING, Collections.singletonMap("FilterName", "draw_svg_Export"));
        addFormat(svg);
  	}

}
