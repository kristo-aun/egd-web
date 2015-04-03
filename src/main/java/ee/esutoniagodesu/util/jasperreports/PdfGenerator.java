package ee.esutoniagodesu.util.jasperreports;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

/**
 * see klass oskab mingite mõistlike etteantud asjade abil genereerida vajaliku PDFi
 */
public final class PdfGenerator {

	private static final Logger log = Logger.getLogger(PdfGenerator.class);

	/**
	 * käesolev meetod on võimeline genereerima PDF-i ja andma selle tagasi byte[] kujul
	 *
	 * @param params         on parameetrid mis antakse repordi genereerimiseks ette
	 * @param jrxmlAsStream  assetist streamina saadud jrxml fail
	 * @param jdbcConnection connection mille me muidbasepage käest võime saada
	 * @return baidi massiiv genereeritud PDF-ga
	 * @throws net.sf.jasperreports.engine.JRException
	 */
	public static byte[] generatePDF(Map<String, Object> params,
	                                 InputStream jrxmlAsStream,
	                                 Connection jdbcConnection) throws JRException {
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlAsStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jdbcConnection);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public static byte[] generatePDF(Map<String, Object> params, InputStream jrxmlAsStream, JRDataSource source) throws JRException {
		log.debug("generatePDF: Generate pdf with JRDataSource");
		JasperDesign jasperDesign = JRXmlLoader.load(jrxmlAsStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public static byte[] concatanatePDF(byte[] pdf1, byte[] pdf2) throws IOException, DocumentException {
		// näite järgi pdfide kokku panemine -- pole proovinud kas töötab
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfReader reader = new PdfReader(pdf1);
		Document document = new Document(reader.getPageSizeWithRotation(1));
		PdfCopy copy = new PdfCopy(document, output);
		document.open();

		// esimene pdf lisatakse
		PdfImportedPage pdfImportedPage;
		for (int i = 0; i < reader.getNumberOfPages(); ) {
			++i;
			pdfImportedPage = copy.getImportedPage(reader, i);
			copy.addPage(pdfImportedPage);
		}

		// teine pdf lisatakse
		reader = new PdfReader(pdf2);
		for (int i = 0; i < reader.getNumberOfPages(); ) {
			++i;
			pdfImportedPage = copy.getImportedPage(reader, i);
			copy.addPage(pdfImportedPage);
		}

		document.close();

		return output.toByteArray();
	}

	/**
	 * kui tekst läheb markupi sisse, siis ei tohi see sisaldada <, >
	 */
	public static String jasperMarkupFix(String string) {
		if (string == null) return null;
		if (!string.contains("<") && !string.contains(">")) return string;

		char[] chars = string.toCharArray();
		StringBuilder result = new StringBuilder();

		for (char p : chars) {
			if (p == '<') {
				result.append("«");
			} else if (p == '>') {
				result.append("»");
			} else {
				result.append(p);
			}
		}
		return result.toString();
	}
}
