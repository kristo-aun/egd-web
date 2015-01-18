package com.jc.structure.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public final class ResponseWrapper implements Serializable {

	private static final long serialVersionUID = -6040751292362264640L;

	private Object data;
	private Collection<String> errors;

	public ResponseWrapper() {}

	public ResponseWrapper(String error) {
		if (errors == null) errors = new ArrayList<>();
		errors.add(error);
	}

	public ResponseWrapper(Object data) {
		this.data = data;
	}

	public Collection<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		if (errors == null) errors = new ArrayList<>();
		errors.add(error);
	}

	public void clearErrors() {
		if (errors != null) errors.clear();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setErrors(Collection<String> errors) {
		this.errors = errors;
	}

	public String toString() {
		return "ResponseWrapper{" +
				"data=" + data +
				", errors=" + errors +
				'}';
	}
}