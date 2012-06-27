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

/**
 * Community member entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name="community_member")
public class CommunityMember implements Serializable {
  
  private static final long serialVersionUID = -4604215142623220332L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(name="member_id")
  private String memberId;
  
  @Column(name="member_pic")
  private String memberPic;
  
  private String role;
  
  @ManyToOne
  @JoinColumn(name="community_id")
  private Community community;

  /** 
   * Class constructor.
   */
  public CommunityMember() {
  }
  
  /** 
   * Class constructor.
   * 
   * @param community
   * @param member id
   * @param member pic
   * @param member role
   */
  public CommunityMember(Community c, String memberId, String memberPic, String role) {
    this.community = c;
    this.memberId = memberId;
    this.memberPic = memberPic;
    this.role = role;
  }

  /**
   * Gets the community id.
   *
   * @return the community id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the community member id.
   *
   * @return the community member id
   */
  public String getMemberId() {
    return memberId;
  }

  /**
   * Sets the community member id.
   *
   * @param the community member id
   */
  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  /**
   * Gets the community member pic.
   *
   * @return the community member pic
   */
  public String getMemberPic() {
    return memberPic;
  }

  /**
   * Sets the community member pic.
   *
   * @param the community member pic
   */
  public void setMemberPic(String memberPic) {
    this.memberPic = memberPic;
  }

  /**
   * Gets the community member role.
   *
   * @return the community member role
   */
  public String getRole() {
    return role;
  }

  /**
   * Sets the community member role.
   *
   * @param the community member role
   */
  public void setRole(String role) {
    this.role = role;
  }
  
  /**
   * Gets the community .
   *
   * @return community
   */
  public Community getCommunity() {
    return community;
  }
  
  /**
   * Sets the community.
   *
   * @param the community
   */
  public void setCommunity(Community c) {
    this.community = c;
  }
}
