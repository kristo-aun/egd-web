package com.jc.jasperreports;

import java.io.Serializable;

public class DynamicParameter implements Serializable {

	private static final long serialVersionUID = -6398547821687653801L;
	private String headerName;
	private String columnClass;
	private String[] columnFields;

	public DynamicParameter() {
	}

	public DynamicParameter(String headerName, String columnClass, String... fields) {
		this.headerName = headerName;
		this.columnClass = columnClass;
		this.columnFields = fields;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getColumnClass() {
		return columnClass;
	}

	public void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}

	public String[] getColumnFields() {
		return columnFields;
	}

	public void setColumnFields(String[] columnFields) {
		this.columnFields = columnFields;
	}
}