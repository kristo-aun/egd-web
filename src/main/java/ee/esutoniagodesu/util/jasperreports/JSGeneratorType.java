package ee.esutoniagodesu.util.jasperreports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public enum JSGeneratorType {

	XLS,
	CSV,
	ODS;

	private static Logger log = Logger.getLogger(JSGeneratorType.class);

	private JRAbstractExporter _exporter;

	public JRAbstractExporter getExporter() {
		if (_exporter == null) {
			switch (this) {
				case XLS: {
					_exporter = new XLSGenerator().getExporter();
					break;
				}
				case ODS: {
					_exporter = new ODSGenerator().getExporter();
					break;
				}
				case CSV: {
					_exporter = new CSVGenerator().getExporter();
					break;
				}
				default: throw new RuntimeException("not implemented");
			}
		}
		return _exporter;
	}

	public String extension() {
		return name().toLowerCase();
	}

	public byte[] export(InputStream istream, JRDataSource data, Map<String, Object> staticValues) throws IOException, JRException {
		return export(istream, data, staticValues, getExporter());
	}

	public byte[] export(JasperReport jasperReport,
	                      JRDataSource data,
	                      Map<String, Object> staticValues) throws IOException, JRException {
		return export(jasperReport, data, staticValues, getExporter());
	}

	public static JSGeneratorType findByExtension(String extension) throws NullPointerException {
		log.debug("findByExtension: strategyName=" + extension);
		for (JSGeneratorType item : values()) {
			if (item.name().equalsIgnoreCase(extension)) {
				return item;
			}
		}

		log.debug("find: Invalid extension: " + extension);
		throw new NullPointerException("Invalid extension: " + extension);
	}

	/**
	 * @param istream has to be for thread safety
	 * @return compoiled jasper template (basically a .jasper file)
	 * @throws net.sf.jasperreports.engine.JRException make sure you handle this on the upper layer
	 */
	public static JasperReport getCompiledReport(InputStream istream) throws JRException {
		JasperDesign design = JRXmlLoader.load(istream);
		//Compile and print the jasper report
		return JasperCompileManager.compileReport(design);
	}

	public static <E extends JRAbstractExporter> byte[] export(InputStream istream,
	                            JRDataSource data,
	                            Map<String, Object> staticValues,
	                            E exporter) throws JRException, IOException {
		JasperReport report = getCompiledReport(istream);
		return export(report, data, staticValues, exporter);
	}

	public static <E extends JRAbstractExporter> byte[] export(JasperReport jasperReport,
	                                                  JRDataSource data,
	                                                  Map<String, Object> staticValues,
	                                                  E exporter) throws JRException, IOException {
		long ms = System.currentTimeMillis();

		JasperPrint print = JasperFillManager.fillReport(jasperReport, staticValues, data);
		SimpleExporterInput input = new SimpleExporterInput(print);
		exporter.setExporterInput(input);

		ByteArrayOutputStream ostream = new ByteArrayOutputStream();

		ExporterOutput output;

		if (exporter instanceof JRCsvExporter) {
			output = new SimpleWriterExporterOutput(ostream);
		} else {
			output = new SimpleOutputStreamExporterOutput(ostream);
		}

		exporter.setExporterOutput(output);

		exporter.exportReport();

		ostream.close();

		log.debug("compileByteArray: compilation time : " + (System.currentTimeMillis() - ms));
		return ostream.toByteArray();
	}
}
