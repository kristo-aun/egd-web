package com.jc.jasperreports;

import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;

public final class CSVGenerator {

	public static final String FIELD_DELIMITER = ";";
	private final SimpleCsvExporterConfiguration _conf;
	private final JRCsvExporter _exporter;

	public CSVGenerator() {
		this(FIELD_DELIMITER);
	}

	public CSVGenerator(String fielddelimiter) {
		_conf = new SimpleCsvExporterConfiguration();
		_conf.setFieldDelimiter(fielddelimiter);
		_exporter = new JRCsvExporter();
		_exporter.setConfiguration(_conf);
	}

	public SimpleCsvExporterConfiguration getConfiguration() {
		return _conf;
	}

	public JRCsvExporter getExporter() {
		return _exporter;
	}
}