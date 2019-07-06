package com.demo.etl.validations.prevalidations;

import com.demo.etl.models.EtlDataModel;
import com.google.common.base.Strings;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BasicPrevalidation implements Prevalidation {

  @Override
  public Boolean validation(List<EtlDataModel> etlDataModelList) {
    if (etlDataModelList.isEmpty()) {
      return false;
    }
    return etlDataModelList.stream()
        .filter(etlDataModel -> !etlDataModel.isValidated())
        .peek(etlDataModel -> etlDataModel.setValidated(true))
        .noneMatch(etlDataModel -> Strings.isNullOrEmpty(etlDataModel.getName()));
  }

  @Override
  public Integer id() {
    return 0;
  }
}
