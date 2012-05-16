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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity for single kks collection class (collection metadata)
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = KksCollectionClass.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES, query = "FROM KksCollectionClass k") })
@Table(name = "kks_collection_class")
public class KksCollectionClass implements Serializable {

  private static final long serialVersionUID = -465916799624311938L;

  public static final String NAMED_QUERY_GET_ALL_COLLECTION_CLASSES = "getAllCollectionClasses";

  @Id
  private Integer id;

  @Column(name = "type_code")
  private String typeCode;

  @Column
  private String name;

  @Column
  private String description;

  @Column(name = "consent_type")
  private String consentType;

  @Transient
  private List<KksGroup> groups;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<KksGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<KksGroup> groups) {
    this.groups = groups;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getConsentType() {
    return consentType;
  }

  public void setConsentType(String concentType) {
    this.consentType = concentType;
  }

}
