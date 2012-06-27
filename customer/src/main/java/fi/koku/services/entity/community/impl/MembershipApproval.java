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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Membership request approval.
 * 
 * @author aspluma
 */
@Entity
@Table(name = "membership_approval")
public class MembershipApproval implements Serializable {
  
  private static final long serialVersionUID = 5782489865453741129L;

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name="membership_request_id")
  private MembershipRequest membershipRequest;
  
  @Transient
  private Long membershipRequestId;

  @Column(name = "approver_pic", nullable = false)
  private String approverPic;

  @Column(nullable = false)
  private String status;

  /** 
   * Class constructor.
   */
  public MembershipApproval() {
  }

  /**
   * Class constructor
   * 
   * @param membershipRequestId
   * @param approverPic
   * @param status
   */
  public MembershipApproval(Long membershipRequestId, String approverPic, String status) {
    this.membershipRequestId = membershipRequestId;
    this.approverPic = approverPic;
    this.status = status;
  }

  /**
   * Class constructor    
   * 
   * @param membership request
   * @param approvers pic
   * @param status
   */
  public MembershipApproval(MembershipRequest rq, String approverPic, String status) {
    this.membershipRequest = rq;
    this.approverPic = approverPic;
    this.status = status;
  }

  /**
   * Get approval id
   * 
   * @return approval id
   */
  public Long getId() {
    return id;
  }

  /**
   * Set approval id
   * 
   * @param approval id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get membership request
   * 
   * @return membership request
   */
  public MembershipRequest getMembershipRequest() {
    return membershipRequest;
  }

  /**
   * Set membership request
   * 
   * @param membership request
   */
  public void setMembershipRequest(MembershipRequest membershipRequest) {
    this.membershipRequest = membershipRequest;
  }

  /**
   * Get membership request id
   * 
   * @return membership request id
   */  
  public Long getMembershipRequestId() {
    return membershipRequestId;
  }

  /**
   * Set membership request id
   * 
   * @param membership request id
   */  
  public void setMembershipRequestId(Long membershipRequestId) {
    this.membershipRequestId = membershipRequestId;
  }

  /**
   * Get approver pic
   * 
   * @return approver pic
   */
  public String getApproverPic() {
    return approverPic;
  }

  /**
   * Set approver pic
   * 
   * @param approver pic
   */
  public void setApproverPic(String approverPic) {
    this.approverPic = approverPic;
  }

  /**
   * Get status
   * 
   * @return status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Set status
   * 
   * @param status
   */
  public void setStatus(String status) {
    this.status = status;
  }
  
}
