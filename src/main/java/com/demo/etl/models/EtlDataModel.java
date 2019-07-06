package com.demo.etl.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtlDataModel {
  private Integer position;
  private String name;
  private boolean isValidated;
}
