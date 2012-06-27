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

/**
 * Membership request query criteria.
 * 
 * @author aspluma
 */
public class MembershipRequestQueryCriteria {
  
  private String requesterPic;
  
  private String approverPic;

  public MembershipRequestQueryCriteria() {
  }

  /** 
   * Class constructor.
   * 
   * @param requester's pic
   * @param approver's pic
   */
  public MembershipRequestQueryCriteria(String requesterPic, String approverPic) {
    this.requesterPic = requesterPic;
    this.approverPic = approverPic;
  }

  /**
   * Get requester's pic
   * 
   * @return requester's pic
   */
  public String getRequesterPic() {
    return requesterPic;
  }

  /**
   * Set requester's pic
   * 
   * @param requester's pic
   */
  public void setRequesterPic(String requesterPic) {
    this.requesterPic = requesterPic;
  }

  /**
   * Get approver's pic
   * 
   * @return approver's pic
   */
  public String getApproverPic() {
    return approverPic;
  }

  /**
   * Set approver's pic
   * 
   * @param approver's pic
   */
  public void setApproverPic(String approverPic) {
    this.approverPic = approverPic;
  }
}
