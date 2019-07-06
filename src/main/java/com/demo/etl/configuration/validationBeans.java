/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.demo.etl.configuration;

import com.demo.etl.validations.postvalidations.Postvalidation;
import com.demo.etl.validations.prevalidations.Prevalidation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author evivehealth on 2019-07-06.
 */
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
