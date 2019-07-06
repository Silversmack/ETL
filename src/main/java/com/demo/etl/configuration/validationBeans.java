package com.demo.etl.configuration;

import com.demo.etl.validations.postvalidations.Postvalidation;
import com.demo.etl.validations.prevalidations.Prevalidation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class validationBeans {

  /**
   * This method creates bean with name prevalidationMap
   *
   * @param prevalidationList List of all prevalidation implementations
   * @return Map of id to {@link Prevalidation}
   */
  @Bean
  public Map<Integer, Prevalidation> prevalidationMap(List<Prevalidation> prevalidationList) {
    return prevalidationList.stream()
        .collect(Collectors.toMap(Prevalidation::id, Function.identity()));
  }

  /**
   * This method creates bean with name postvalidationMap
   *
   * @param postvalidationList List of all prevalidation implementations
   * @return Map of id to {@link Postvalidation} class
   */
  @Bean
  public Map<Integer, Postvalidation> postvalidationMap(List<Postvalidation> postvalidationList) {
    return postvalidationList.stream()
        .collect(Collectors.toMap(Postvalidation::id, Function.identity()));
  }


}
