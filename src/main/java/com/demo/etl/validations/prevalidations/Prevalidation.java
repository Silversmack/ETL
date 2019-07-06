package com.demo.etl.validations.prevalidations;

import com.demo.etl.models.EtlDataModel;
import java.util.List;

public interface Prevalidation {
  Boolean validation(List<EtlDataModel> etlDataModelList);

  Integer id();
}
