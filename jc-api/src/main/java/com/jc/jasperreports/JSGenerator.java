package com.jc.jasperreports;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.ExporterConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface JSGenerator {
	byte[] compile(InputStream istream, JRDataSource data, Map<String, Object> staticValues) throws IOException, JRException;

	byte[] compile(JasperReport jasperReport, JRDataSource data, Map<String, Object> staticValues) throws IOException, JRException;

	byte[] compile(InputStream istream,
	               JRDataSource data,
	               Map<String, Object> staticValues,
	               ExporterConfiguration conf) throws IOException, JRException;

	byte[] compile(JasperReport jasperReport,
	               JRDataSource data,
	               Map<String, Object> staticValues,
	               ExporterConfiguration conf) throws IOException, JRException;

	JSGeneratorType getJSGeneratorType();
}