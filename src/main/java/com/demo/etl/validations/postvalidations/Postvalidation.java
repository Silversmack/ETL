package com.demo.etl.validations.postvalidations;

import com.demo.etl.models.EtlDataModel;
import java.util.List;

public interface Postvalidation {
  Boolean check(List<EtlDataModel> etlDataModelList);

  Integer id();
}
