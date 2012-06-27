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

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Community membership request.
 * 
 * @author aspluma
 */
@Entity
@NamedQueries({
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUEST_BY_ID, query="SELECT r FROM MembershipRequest r JOIN FETCH r.approvals WHERE r.id = :id"),
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_REQUESTER_PIC, query="SELECT r FROM MembershipRequest r JOIN FETCH r.approvals WHERE r.requesterPic = :requesterPic"),
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_APPROVER_PIC, query="SELECT r FROM MembershipRequest r " +
  "JOIN FETCH r.approvals WHERE r IN (" +
  "SELECT a.membershipRequest FROM MembershipApproval a WHERE a.approverPic = :approverPic)")
})
@Table(name = "community_membership_request")
public class MembershipRequest implements Serializable {
  
  private static final long serialVersionUID = -409898477181862486L;

  public static final String QUERY_GET_MEM_REQUEST_BY_ID = "getMembershipRequestById";
  
  public static final String QUERY_GET_MEM_REQUESTS_BY_REQUESTER_PIC = "getMembershipRequestByRequesterPic";
  
  public static final String QUERY_GET_MEM_REQUESTS_BY_APPROVER_PIC = "getMembershipRequestByApproverPic";
  
  @Id
  @GeneratedValue
  private Long id;

  // map as id, not relationship
  @Column(name="community_id", nullable=false)
  private Long communityId;

  @Column(name="member_role", nullable=false)
  private String memberRole;

  @Column(name="member_pic", nullable=false)
  private String memberPic;

  @Column(name="requester_pic", nullable=false)
  private String requesterPic;

  @OneToMany(mappedBy="membershipRequest", cascade={PERSIST, REMOVE})
  private Collection<MembershipApproval> approvals = new ArrayList<MembershipApproval>();
  
  @Column(nullable=false)
  @Temporal(TemporalType.DATE)
  private Date created;
  
  /** 
   * Class constructor.
   */ 
  public MembershipRequest() {
  }

  /**
   * Get request id
   * 
   * @return request id
   */
  public Long getId() {
    return id;
  }

  /**
   * Set request id
   * 
   * @param request id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get community id
   * 
   * @return community id
   */
  public Long getCommunityId() {
    return communityId;
  }

  /**
   * Set community id
   * 
   * @param community id
   */
  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
  }

  /**
   * Get member role
   * 
   * @return member's role
   */
  public String getMemberRole() {
    return memberRole;
  }

  /**
   * Set member role
   * 
   * @param member role
   */
  public void setMemberRole(String memberRole) {
    this.memberRole = memberRole;
  }

  /**
   * Get member pic
   * 
   * @return member pic
   */
  public String getMemberPic() {
    return memberPic;
  }

  /**
   * Set member pic
   * 
   * @param member pic
   */
  public void setMemberPic(String memberPic) {
    this.memberPic = memberPic;
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
   * @param requester pic
   */
  public void setRequesterPic(String requesterPic) {
    this.requesterPic = requesterPic;
  }

  /**
   * Get approvals
   * 
   * @return approvals
   */
  public Collection<MembershipApproval> getApprovals() {
    return approvals;
  }

  /**
   * Set approvals
   * 
   * @param approvals to be added
   */
  public void setApprovals(Collection<MembershipApproval> approvals) {
    this.approvals = approvals;
  }

  /**
   * Get creation date
   * 
   * @return date of creation
   */
  public Date getCreated() {
    return created;
  }

  /**
   * Set creation date
   * 
   * @param creation date
   */
  public void setCreated(Date created) {
    this.created = created;
  }

}
