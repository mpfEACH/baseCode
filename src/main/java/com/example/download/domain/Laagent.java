package com.example.download.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Laagent implements Serializable {

  private String agentcode;
  private String devno1;
  private String devno2;
  private String name;
  private String managecom;
  private String password;
  private String trace;
  private String gender;
  private String marry;
  private String nation;
  private String station;
  private String detail;

}
