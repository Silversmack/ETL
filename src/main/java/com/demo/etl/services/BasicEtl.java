/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.demo.etl.services;

import com.demo.etl.AbstractEtl;
import com.demo.etl.models.EtlDataModel;
import com.demo.etl.validations.postvalidations.Postvalidation;
import com.demo.etl.validations.prevalidations.Prevalidation;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author evivehealth on 2019-07-06.
 */
@Slf4j
@Service
public class BasicEtl extends AbstractEtl {

  @Autowired
  private Map<Integer, Prevalidation> prevalidationMap;
  @Autowired
  private Map<Integer, Postvalidation> postvalidationMap;


  @Override
  protected boolean preValidate(List<EtlDataModel> etlDataModelList) {
    log.info("Prevalidate Etl Data Model List: {}", etlDataModelList);
    // 0 because validating with single class
    return prevalidationMap.get(0).validation(etlDataModelList);
  }

  @Override
  protected boolean postValidate(List<EtlDataModel> etlDataModelList) {
    // 0 because validating with single class
    return postvalidationMap.get(0).check(etlDataModelList);
  }
}
