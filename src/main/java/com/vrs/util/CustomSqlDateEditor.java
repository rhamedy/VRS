package com.vrs.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;

import org.springframework.util.StringUtils;

public class CustomSqlDateEditor extends PropertyEditorSupport {

	private final DateFormat dateFormat;
	private final boolean allowEmpty;
	private final int exactDateLength;

	public CustomSqlDateEditor(DateFormat dateFormat, boolean allowEmpty) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	public CustomSqlDateEditor(DateFormat dateFormat, boolean allowEmpty,
			int exactDateLength) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else if (text != null && this.exactDateLength >= 0
				&& text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly "
							+ this.exactDateLength + "characters long.");
		} else { 
			try { 
				java.util.Date d = dateFormat.parse(text); 
				java.sql.Date sqlDate = new java.sql.Date(d.getTime()); 
				setValue(sqlDate); 
			} catch(ParseException pe) { 
				throw new IllegalArgumentException("Cannot parse string to date: " + pe.getMessage()); 
			}
		}
	}
}
