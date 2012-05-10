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

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * Customer entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@NamedQuery(name="getCustomerByPic", query="FROM Customer c WHERE c.pic = :pic")
@Table(name = "customer")
public class Customer {
  
  public static final String QUERY_GET_CUSTOMER_BY_PIC = "getCustomerByPic";
  
  @Id
  @GeneratedValue
  private Long id;
  
  @Column(nullable=false)
  private String status;
  
  @Column(name="status_date", nullable=false)
  @Temporal(TemporalType.DATE)
  private Date statusDate;

  @Column(unique=true, nullable=false)
  private String pic;
  
  @Column(name="birth_date", nullable=false)
  @Temporal(TemporalType.DATE)
  private Date birthDate;
  
  @Column(nullable=false)
  private String lastName;
  
  @Column(name="first_name", nullable=false)
  private String firstName;

  @Column(name="first_names", nullable=false)
  private String firstNames;

  @Column(nullable=false)
  private String nationality;
  
  @Column(nullable=false)
  private String municipality;
  
  @Column(nullable=false)
  private String language;
  
  @Column(nullable=false)
  private boolean turvakielto;
  
  @OneToMany(mappedBy="customer", cascade={PERSIST, REMOVE})
  private Set<Address> addresses = new HashSet<Address>();
  
  @OneToMany(mappedBy="customer", cascade={PERSIST, REMOVE})
  private Set<PhoneNumber> phones = new HashSet<PhoneNumber>();
  
  @OneToMany(mappedBy="customer", cascade={PERSIST, REMOVE})
  private Set<ElectronicContactInfo> electronicContacts = new HashSet<ElectronicContactInfo>();

  
  @Version
  private int version;

  public Customer() {
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getStatusDate() {
    return statusDate;
  }

  public void setStatusDate(Date statusDate) {
    this.statusDate = statusDate;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstNames() {
    return firstNames;
  }

  public void setFirstNames(String firstNames) {
    this.firstNames = firstNames;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getMunicipality() {
    return municipality;
  }

  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public boolean isTurvakielto() {
    return turvakielto;
  }

  public void setTurvakielto(boolean turvakielto) {
    this.turvakielto = turvakielto;
  }

  public Collection<Address> getAddresses() {
    return addresses;
  }

  public Collection<PhoneNumber> getPhones() {
    return phones;
  }

  public Collection<ElectronicContactInfo> getElectronicContacts() {
    return electronicContacts;
  }

  public int getVersion() {
    return version;
  }

  public void setCustomer(Customer c) {
    setStatus(c.getStatus());
    setStatusDate(c.getStatusDate());
    setPic(c.getPic());
    setBirthDate(c.getBirthDate());
    setLastName(c.getLastName());
    setFirstName(c.getFirstName());
    setFirstNames(c.getFirstNames());
    setNationality(c.getNationality());
    setMunicipality(c.getMunicipality());
    setLanguage(c.getLanguage());
    setTurvakielto(c.isTurvakielto());
  }

}
