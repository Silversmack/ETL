/*
 * Copyright (c) 2019.
 */

package com.demo.etl;

import com.demo.etl.models.EtlDataModel;
import com.google.common.base.Splitter;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
 * @author evivehealth on 2019-07-05.
 */
@Slf4j
public abstract class AbstractEtl {

  private final MutableGraph<Integer> graph;
  private final Map<Integer, Integer> transformationMap;
  private Map<Integer, EtlDataModel> positionToEtlDataMap;

  public AbstractEtl() {
    graph = GraphBuilder.directed().allowsSelfLoops(true).build();
    positionToEtlDataMap = new HashMap<>();
    transformationMap = new HashMap<>();
  }

  /**
   * This method reads and stores the data from files.
   *
   * @throws IOException
   */
  public void readFileData() throws IOException {
    setEtlData(getPath("etl_files/data_file"));
    setDependentData(getPath("etl_files/dependent_file"));
    setTransformationData(getPath("etl_files/transform_file"));
  }

  /**
   * Method for setting ETL data.
   *
   * @param dataPath Path of ETL file
   */
  private void setEtlData(Path dataPath) {
    try (BufferedReader bufferedReader = Files.newBufferedReader(dataPath)) {
      positionToEtlDataMap = bufferedReader.lines()
          .map(record -> {
            List<String> recordData = Splitter.on(",").trimResults().splitToList(record);
            return EtlDataModel.builder()
                .position(Integer.valueOf(recordData.get(0)))
                .name(recordData.get(1))
                .build();
          })
          .collect(Collectors.toMap(EtlDataModel::getPosition, Function.identity()));
    } catch (IOException e) {
      log.error("Error parsing file with path: {}", dataPath);
    }
    log.info("ETL Data: {}", positionToEtlDataMap);
  }

  /**
   * Method for getting class path to stream data.
   *
   * @param path path of the file
   * @return {@link Path}
   */
  private Path getPath(String path) {
    try {
      ClassPathResource classPathResource = new ClassPathResource(path);
      return Paths.get(classPathResource.getURL().getPath());
    } catch (IOException e) {
      log.error("Error getting path: {}", path);
      return Paths.get("");
    }
  }

  /**
   * Method for setting dependent data.
   *
   * @param dependentPath Path of Depdendent file
   * @throws IOException
   */
  private void setDependentData(Path dependentPath) throws IOException {
    try (BufferedReader bufferedReader = Files.newBufferedReader(dependentPath)) {
      bufferedReader.lines().forEach(record -> {
        List<String> recordData = Splitter.on(",").trimResults().splitToList(record);
        Integer position = Integer.valueOf(recordData.get(0));
        graph.addNode(position);
        if ("Y".equals(recordData.get(1))) {
          for (int i = 2; i < recordData.size(); i++) {
            graph.putEdge(position, Integer.valueOf(recordData.get(i)));
          }
        }
        graph.putEdge(position, position);
      });
    }
  }

  /**
   * Method for setting transformation data.
   *
   * @param transformPath Path of Transformation file
   * @throws IOException
   */
  private void setTransformationData(Path transformPath) throws IOException {
    try (BufferedReader bufferedReader = Files.newBufferedReader(transformPath)) {
      bufferedReader.lines().forEach(record -> {
        List<String> recordData = Splitter.on(",").trimResults().splitToList(record);
        Integer position = Integer.valueOf(recordData.get(0));
        if ("Y".equals(recordData.get(1))) {
          transformationMap.put(position, Integer.valueOf(recordData.get(2)));
        }
      });
    }
    log.info("Transformation Map: {}", transformationMap);
  }

  /**
   * This method takes all the data from graph. Pre-validates, Transforms, Post-validates and logs
   * the data.
   */
  public void execute() {
    graph.nodes().parallelStream()
        .map(node -> graph.successors(node).parallelStream()
            .map(position -> positionToEtlDataMap.get(position))
            .collect(Collectors.toList()))
        .filter(this::preValidate)
        .map(this::transform)
        .filter(this::postValidate)
        //Logging for now (Can store in database here)
        .forEach(etlDataModelList -> log.info("Final Etl Data Model List: {}", etlDataModelList));
    log.info("ETL Data: {}", positionToEtlDataMap);
  }

  /**
   * Method for prevalidating data models.
   *
   * @param etlDataModelList List of models to be validated
   * @return true if it successfully validates, else false
   */
  protected abstract boolean preValidate(List<EtlDataModel> etlDataModelList);

  /**
   * Method for transforming data models.
   *
   * @param etlDataModelList List of models to be transformed
   * @return data models
   */
  protected List<EtlDataModel> transform(List<EtlDataModel> etlDataModelList) {
    return etlDataModelList.stream()
        .peek(etlDataModel -> {
          if (transformationMap.containsKey(etlDataModel.getPosition())) {
            etlDataModel.setName(positionToEtlDataMap.get(transformationMap.get(
                etlDataModel.getPosition())).getName() + "A");
          }
        })
        .collect(Collectors.toList());
  }

  /**
   * Method for postvalidating data models.
   *
   * @param etlDataModelList List of models to be validated
   * @return true if it successfully validates, else false
   */
  protected abstract boolean postValidate(List<EtlDataModel> etlDataModelList);

}
