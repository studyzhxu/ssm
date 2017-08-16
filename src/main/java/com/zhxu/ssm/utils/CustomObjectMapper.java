package com.zhxu.ssm.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomObjectMapper extends ObjectMapper {

	/**
	 * 字段描述
	 */
	private static final long serialVersionUID = 5189997968481093161L;
	private boolean camelCaseToLowerCaseWithUnderscores = false;
	private String dateFormatPattern;

	public void setCamelCaseToLowerCaseWithUnderscores(boolean camelCaseToLowerCaseWithUnderscores) {
		this.camelCaseToLowerCaseWithUnderscores = camelCaseToLowerCaseWithUnderscores;
	}

	public void setDateFormatPattern(String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
	}

	public void init() {
		/*setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
		configure(SerializationFeature.INDENT_OUTPUT, false);
		if (camelCaseToLowerCaseWithUnderscores) {
			setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		}
		if (StringUtils.isNotEmpty(dateFormatPattern)) {
			DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
			setDateFormat(dateFormat);
		}*/
	}
}
