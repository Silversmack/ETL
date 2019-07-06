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
