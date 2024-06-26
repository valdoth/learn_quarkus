package org.valdo;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import java.util.Objects;
import java.util.UUID;


public class Artist {

  @JsonbTransient
  private UUID id;
  @JsonbProperty("first_name")
  private String firstName;
  @JsonbProperty("last_name")
  private String lastName;

  Artist() {}

  Artist(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Artist id(UUID id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Artist firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Artist lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Artist artist = (Artist) o;
    return id.equals(artist.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Artist{" +
      "id='" + id + '\'' +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      '}';
  }
}