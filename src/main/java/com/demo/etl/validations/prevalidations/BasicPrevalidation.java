/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.demo.etl.validations.prevalidations;

import com.demo.etl.models.EtlDataModel;
import com.google.common.base.Strings;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author evivehealth on 2019-07-06.
 */
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
