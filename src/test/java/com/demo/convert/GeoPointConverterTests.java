package com.demo.convert;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class GeoPointConverterTests {

  GeoPointConverter geoPointConverter = new GeoPointConverter();

  /**
   * Convert database value of comma-delimited String to GeoPoint object
   */
  @Test
  public void convertToEntityAttributeValid() {
    String dbValue = "37.356559, -121.935953";
    GeoPoint geoPoint = geoPointConverter.convertToEntityAttribute(dbValue);

    assertThat(geoPoint).isNotNull();
    assertThat(geoPoint.getLat()).isEqualTo(37.356559d);
    assertThat(geoPoint.getLon()).isEqualTo(-121.935953d);
  }

  /**
   * Convert GeoPoint object to comma-delimited String
   */
  @Test
  public void convertToDatabaseColumnValid() {
    GeoPoint geoPoint = new GeoPoint(34.690585d, -82.834429d);
    String dbValue = geoPointConverter.convertToDatabaseColumn(geoPoint);

    assertThat(dbValue).isNotNull();
    assertThat(dbValue).isNotBlank();
    assertThat(dbValue).isEqualTo("34.690585, -82.834429");
  }

}
