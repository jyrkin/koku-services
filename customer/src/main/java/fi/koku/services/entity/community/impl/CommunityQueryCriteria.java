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
package fi.koku.services.entity.community.impl;

import java.util.Set;

/**
 * Community query criteria.
 * 
 * @author aspluma
 */
public class CommunityQueryCriteria {
  
  private Set<String> memberPics;
  
  private String communityType;

  /** 
   * Class constructor.
   * 
   * @param member pics
   * @param  community type
   */
  public CommunityQueryCriteria(Set<String> memberPics, String communityType) {
    this.memberPics = memberPics;
    this.communityType = communityType;
  }

  /**
   * Sets community type
   * 
   * @return community type
   */
  public String getCommunityType() {
    return communityType;
  }

  /**
   * Gets members pics
   * 
   * @return member pics
   */
  public Set<String> getMemberPics() {
    return memberPics;
  }
}
