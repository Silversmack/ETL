/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.demo.etl.validations.postvalidations;

import com.demo.etl.models.EtlDataModel;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author evivehealth on 2019-07-06.
 */
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
