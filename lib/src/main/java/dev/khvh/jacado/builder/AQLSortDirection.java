package dev.khvh.jacado.builder;

import java.util.Arrays;

public enum AQLSortDirection {

  ASC("ASC"),
  DESC("DESC");

  private final String sortDirection;

  AQLSortDirection(String sortDirection) {
    this.sortDirection = sortDirection;
  }

  public String getDirection() {
    return sortDirection;
  }

  public static AQLSortDirection fromString(String s) {
    return Arrays
      .stream(AQLSortDirection.values())
      .filter(v -> v.sortDirection.equalsIgnoreCase(s))
      .findFirst()
      .orElse(AQLSortDirection.ASC);
  }

}
