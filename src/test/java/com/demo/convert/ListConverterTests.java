package com.demo.convert;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ListConverterTests {

	ListConverter converter = new ListConverter();

	/**
	 * Convert ArrayList of Strings to comma-delimited String for DB
	 */
	@Test
	public void convertToDatabaseColumnValid() {
		List<String> arrayList = new ArrayList<>(Arrays.asList("Clover Crafted Batch Coffee", "Lunch", "Oven-warmed Food", "Reserve Amenity", "Sirena Espresso Machine"));
		String result = converter.convertToDatabaseColumn(arrayList);

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		assertThat(result).isEqualTo("Clover Crafted Batch Coffee, Lunch, Oven-warmed Food, Reserve Amenity, Sirena Espresso Machine");
	}

	/**
	 * Convert DB String value to List<String>
	 */
	@Test
	public void convertToEntityAttributeValid() {
		String dbValue = "Starbucks Card Mobile, Wireless Hotspot";
		List<String> result = converter.convertToEntityAttribute(dbValue);

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0)).isEqualTo("Starbucks Card Mobile");
		assertThat(result.get(1)).isEqualTo("Wireless Hotspot");
	}

}
