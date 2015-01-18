package com.jc.jasperreports;

import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.XlsExporterConfiguration;

public final class XLSGenerator {

	private final XlsExporterConfiguration _conf;
	private final JRXlsExporter _exporter;

	public XLSGenerator() {
		_conf = new SimpleXlsExporterConfiguration();
		_exporter = new JRXlsExporter();
		_exporter.setConfiguration(_conf);
	}

	public XlsExporterConfiguration getConfiguration() {
		return _conf;
	}

	public JRXlsExporter getExporter() {
		return _exporter;
	}
}