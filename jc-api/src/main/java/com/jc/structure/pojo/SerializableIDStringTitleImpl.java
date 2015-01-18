package com.jc.structure.pojo;

import java.io.Serializable;

public final class SerializableIDStringTitleImpl implements SerializableIDStringTitle, Serializable {

	private static final long serialVersionUID = 2362589565828831639L;

	private Serializable id;
	private String title;

	public SerializableIDStringTitleImpl() {
	}

	public SerializableIDStringTitleImpl(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public Serializable getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SerializableIDStringTitleImpl that = (SerializableIDStringTitleImpl) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "SerializableIDStringTitleImpl{" +
				"id=" + id +
				", title='" + title + '\'' +
				'}';
	}
}