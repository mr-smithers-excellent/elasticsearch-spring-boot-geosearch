package com.demo.domain;

import com.demo.convert.GeoPointConverter;
import com.demo.convert.ListConverter;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;


@Entity
@Table(name = "starbucks_locations")
@Document(indexName = "starbucks", type = "starbucks-location")
public class Starbucks implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @GeoPointField
  @Convert(converter = GeoPointConverter.class)
  private GeoPoint location;

  private String ownershipType;

	@Convert(converter = ListConverter.class)
  private List<String> products;

	@Convert(converter = ListConverter.class)
  private List<String> services;

	@Convert(converter = ListConverter.class)
  private List<String> stations;

  private String phone;
  private String street1;
  private String street2;
  private String city;
  private String state;
  private String zip;
  private String country;

  public Starbucks() {
  }

  @Override
  public String toString() {
    return "Starbucks{" +
        "name='" + name + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", location=" + location +
        '}';
  }

  /*
   * Compare IDs to determine if instances are equal
   */
  @Override
  public boolean equals(Object obj) {
    boolean equals = false;
    if (obj instanceof Starbucks) {
      Starbucks starbucks = (Starbucks) obj;
      return this.id.equals(starbucks.id);
    }

    return equals;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GeoPoint getLocation() {
    return location;
  }

  public void setLocation(GeoPoint location) {
    this.location = location;
  }

  public String getOwnershipType() {
    return ownershipType;
  }

  public void setOwnershipType(String ownershipType) {
    this.ownershipType = ownershipType;
  }

  public List<String> getProducts() {
    return products;
  }

  public void setProducts(List<String> products) {
    this.products = products;
  }

  public List<String> getServices() {
    return services;
  }

  public void setServices(List<String> services) {
    this.services = services;
  }

  public List<String> getStations() {
    return stations;
  }

  public void setStations(List<String> stations) {
    this.stations = stations;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getStreet2() {
    return street2;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
