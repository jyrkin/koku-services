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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Community entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@NamedQueries({
  @NamedQuery(name=Community.QUERY_GET_COMMUNITY_BY_ID, query="SELECT c FROM Community c LEFT JOIN FETCH c.members WHERE c.id = :id")
})
@Table(name = "community")
public class Community implements Serializable {
  
  public static final String QUERY_GET_COMMUNITY_BY_ID = "getCommunityById";
  
  private static final long serialVersionUID = -6318987012290421231L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)  
  private String type;
  
  private String name;

  @OneToMany(mappedBy="community", cascade={PERSIST, REMOVE})
  private Collection<CommunityMember> members = new ArrayList<CommunityMember>();
  
  @Version
  private int version;

  /** 
   * Class constructor.
   */
  public Community() {
  }

  
  /**
   *  Gets the community type
   * 
   * @return  community type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the community type.
   *
   * @param type the community type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   *  Gets the community name
   * 
   * @return  community name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the community name.
   *
   * @param name the community name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *  Gets the community version
   * 
   * @return  community version
   */
  public int getVersion() {
    return version;
  }

  /**
   *  Gets the community id
   * 
   * @return  community id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the community id.
   *
   * @param id the community id
   */
  protected void setId(Long id) {
    this.id = id;
  }

  /**
   *  Gets the community members
   * 
   * @return  community members
   */
  public Collection<CommunityMember> getMembers() {
    return members;
  }

  /*
   * (non-Javadoc)
   * helper mehtod
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * helper method
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Community other = (Community) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
