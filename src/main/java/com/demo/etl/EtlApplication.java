/*
 * Copyright (c) 2019.
 */

package com.demo.etl;

import com.demo.etl.services.BasicEtl;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EtlApplication implements CommandLineRunner {

  @Autowired
  private BasicEtl basicEtl;

  public static void main(String[] args) throws IOException {
    SpringApplication.run(EtlApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    basicEtl.readFileData();
    basicEtl.execute();
  }

}
