/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (kohtikumppanuutta@ixonos.com).
 *
 */
package fi.koku.services.entity.kks.impl;

/**
 * Wraps collection info 
 * 
 * @author tuomape
 *
 */
public class KksCollectionInfo {
  
  private String id;
  private String name;
  
  public KksCollectionInfo(String id, String name ) {
    this.id = id;
    this.name = name;
  }
  
  public KksCollectionInfo(Long id, String name ) {
    this.id = ""  + id;
    this.name = name;
  }
  
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Constructs instance from given object
   * 
   * @param o
   * @return
   */
  public static KksCollectionInfo fromObject(Object o[]) {
    if (o == null || o.length < 2 )
      throw new IllegalArgumentException("Object needs to contain data");
    
    return new KksCollectionInfo("" + o[0], "" + o[1]);
  }
  

}
