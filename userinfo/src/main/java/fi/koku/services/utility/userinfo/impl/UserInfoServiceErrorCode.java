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
package fi.koku.services.utility.userinfo.impl;

/**
 * Error codes for userinfo service.
 * 
 * @author hekkata
 */
public enum UserInfoServiceErrorCode {

  PORTAL_USER_NOT_FOUND(1001, "User not found."),
  NO_QUERY_CRITERIA(1002, "Query criteria missing."),
  UNAUTHORIZED(1003,"Unauthorized to use operation");
  private final int value;

  private final String description;
  
  UserInfoServiceErrorCode(int value, String description) {
    this.value = value;
    this.description = description;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}
