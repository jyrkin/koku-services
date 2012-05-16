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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity for entry group
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = KksGroup.NAMED_QUERY_GET_ALL_GROUPS, query = "FROM KksGroup k"),
    @NamedQuery(name = KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES_WITH_GROUP_REGISTERS, query = "SELECT DISTINCT k.collectionClassId FROM KksGroup k WHERE k.register IN (:registers)"),
    @NamedQuery(name = KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_REGISTERS, query = "SELECT DISTINCT k.register FROM KksGroup k WHERE k.collectionClassId =:id"),
    @NamedQuery(name = KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUPS, query = "SELECT DISTINCT k FROM KksGroup k WHERE k.collectionClassId =:id"),
    @NamedQuery(name = KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUP_IDS, query = "SELECT DISTINCT k.groupId FROM KksGroup k WHERE k.collectionClassId=:classId") })
@Table(name = "kks_group")
public class KksGroup implements Serializable, Comparable<KksGroup> {

  public static final String NAMED_QUERY_GET_ALL_GROUPS = "getAllGroups";
  public static final String NAMED_QUERY_GET_ALL_COLLECTION_CLASSES_WITH_GROUP_REGISTERS = "getAllCollectionClassesWithGroupRegisters";
  public static final String NAMED_QUERY_GET_ALL_COLLECTION_CLASS_REGISTERS = "getAllCollectionClassRegisters";
  public static final String NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUPS = "getAllCollectionClassGroups";
  public static final String NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUP_IDS = "getAllCollectionClassGroupIdsById";

  private static final long serialVersionUID = -9141086962336862170L;

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "group_id")
  private int groupId;

  @Column(name = "sort_order", nullable = false)
  private int sortOrder;

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  private String register;

  @Column(name = "parent_id", nullable = true)
  private Integer parentId;

  @Column
  private String accountable;

  @Transient
  private List<KksEntryClass> entryClasses;

  @Column(name = "collection_class_id")
  private int collectionClassId;

  @Transient
  private List<KksGroup> subGroups;

  public KksGroup() {
    subGroups = new ArrayList<KksGroup>();
    entryClasses = new ArrayList<KksEntryClass>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
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

  public String getRegister() {
    return register;
  }

  public void setRegister(String register) {
    this.register = register;
  }

  public List<KksEntryClass> getEntryClasses() {
    return entryClasses;
  }

  public void setEntryClasses(List<KksEntryClass> entryClasses) {

    if (entryClasses == null) {
      this.entryClasses = new ArrayList<KksEntryClass>();

    } else {
      this.entryClasses = entryClasses;
    }
  }

  public int getCollectionClassId() {
    return collectionClassId;
  }

  public void setCollectionClassId(int collectionClassId) {
    this.collectionClassId = collectionClassId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getAccountable() {
    return accountable;
  }

  public void setAccountable(String accountable) {
    this.accountable = accountable;
  }

  public List<KksGroup> getSubGroups() {
    return subGroups;
  }

  public void setSubGroups(List<KksGroup> subGroups) {
    this.subGroups = subGroups;
  }

  public void addSubGroup(KksGroup g) {
    this.subGroups.add(g);
  }

  @Override
  public int compareTo(KksGroup o) {

    if (o != null) {
      return Integer.valueOf(getSortOrder()).compareTo(Integer.valueOf(o.getSortOrder()));
    }
    return 1;
  }
}
