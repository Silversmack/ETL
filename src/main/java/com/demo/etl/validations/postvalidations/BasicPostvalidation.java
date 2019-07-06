package com.demo.etl.validations.postvalidations;

import com.demo.etl.models.EtlDataModel;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BasicPostvalidation implements Postvalidation {

  @Override
  public Boolean check(List<EtlDataModel> etlDataModelList) {
    if (etlDataModelList.isEmpty()) {
      return false;
    }
    return etlDataModelList.stream()
        .allMatch(etlDataModel -> etlDataModel.getName().endsWith("A"));
  }

  @Override
  public Integer id() {
    return 0;
  }
}
