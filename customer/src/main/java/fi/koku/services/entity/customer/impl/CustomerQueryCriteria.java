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
package fi.koku.services.entity.customer.impl;

import java.util.Set;


/**
 * Customer query criteria.
 * 
 * @author aspluma
 */
public class CustomerQueryCriteria {

  private Set<String> pics;
  
  private String selection;

  /**
   * Class constructor.
   *
   * @param pics the pics
   * @param selection the selection
   */
  public CustomerQueryCriteria(Set<String> pics, String selection) {
    this.pics = pics;
    this.selection = selection;
  }

  /**
   * Gets the pics.
   *
   * @return the pics
   */
  public Set<String> getPics() {
    return pics;
  }

  /**
   * Gets the selection.
   *
   * @return the selection
   */
  public String getSelection() {
    return selection;
  }
}
