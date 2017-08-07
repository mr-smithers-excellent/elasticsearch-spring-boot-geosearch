package com.demo.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class ListConverter implements AttributeConverter<List, String> {

	private static final String DELIMITER = ", ";

	/**
	 * Convert List to a comma + space-delimited String
	 */
	@Override
	public String convertToDatabaseColumn(List stringList) {
		return StringUtils.arrayToDelimitedString(stringList.toArray(), DELIMITER);
	}

	/**
	 * Converts a DB String value to a List
	 */
	@Override
	public List convertToEntityAttribute(String string) {
		return new ArrayList(Arrays.asList(StringUtils.delimitedListToStringArray(string, DELIMITER)));
	}

}
