package com.demo.convert;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Converter
public class GeoPointConverter implements AttributeConverter<GeoPoint, String> {

  /**
   * Converts a String "latitude,longitude" field to a GeoPoint for Elasticsearch
   */
  @Override
  public GeoPoint convertToEntityAttribute(String input) {
    GeoPoint geoPoint = null;
    List<String> coordinates = Lists.newArrayList(Splitter.on(", ").trimResults().split(input));
    if (coordinates != null && coordinates.size() == 2) {
      geoPoint = new GeoPoint(Double.valueOf(coordinates.get(0)), Double.valueOf(coordinates.get(1)));
    }

    return geoPoint;
  }

  /**
   * Converts a GeoPoint field to a "latitude,longitude" String for DB
   */
  @Override
  public String convertToDatabaseColumn(GeoPoint geoPoint) {
    String dbValue = null;
    if (geoPoint != null) {
      dbValue = Double.toString(geoPoint.getLat()) + ", " + Double.toString(geoPoint.getLon());
    }

    return dbValue;
  }

}
