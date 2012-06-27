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

  /** 
   * Class constructor.
   */
  public Customer() {
  }
  
  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }
  
  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Gets the status.
   *
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets the status date.
   *
   * @return the status date
   */
  public Date getStatusDate() {
    return statusDate;
  }

  /**
   * Sets the status date.
   *
   * @param statusDate the new status date
   */
  public void setStatusDate(Date statusDate) {
    this.statusDate = statusDate;
  }

  /**
   * Gets the pic.
   *
   * @return the pic
   */
  public String getPic() {
    return pic;
  }

  /**
   * Sets the pic.
   *
   * @param pic the new pic
   */
  public void setPic(String pic) {
    this.pic = pic;
  }

  /**
   * Gets the birth date.
   *
   * @return the birth date
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Sets the birth date.
   *
   * @param birthDate the new birth date
   */
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   *
   * @param lastName the new last name
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   *
   * @param firstName the new first name
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the first names.
   *
   * @return the first names
   */
  public String getFirstNames() {
    return firstNames;
  }

  /**
   * Sets the first names.
   *
   * @param firstNames the new first names
   */
  public void setFirstNames(String firstNames) {
    this.firstNames = firstNames;
  }

  /**
   * Gets the nationality.
   *
   * @return the nationality
   */
  public String getNationality() {
    return nationality;
  }

  /**
   * Sets the nationality.
   *
   * @param nationality the new nationality
   */
  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  /**
   * Gets the municipality.
   *
   * @return the municipality
   */
  public String getMunicipality() {
    return municipality;
  }

  /**
   * Sets the municipality.
   *
   * @param municipality the new municipality
   */
  public void setMunicipality(String municipality) {
    this.municipality = municipality;
  }

  /**
   * Gets the language.
   *
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Sets the language.
   *
   * @param language the new language
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Checks if is turvakielto.
   *
   * @return true, if is turvakielto
   */
  public boolean isTurvakielto() {
    return turvakielto;
  }

  /**
   * Sets turvakielto.
   *
   * @param turvakielto status
   */
  public void setTurvakielto(boolean turvakielto) {
    this.turvakielto = turvakielto;
  }

  /**
   * Gets addresses.
   *
   * @return the addresses
   */
  public Collection<Address> getAddresses() {
    return addresses;
  }

  /**
   * Gets phone numbers.
   *
   * @return the phone numbers
   */
  public Collection<PhoneNumber> getPhones() {
    return phones;
  }

  /**
   * Gets electronic contacts.
   *
   * @return the electronic contacts
   */
  public Collection<ElectronicContactInfo> getElectronicContacts() {
    return electronicContacts;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public int getVersion() {
    return version;
  }

  /**
   * Sets the customer.
   *
   * @param c the new customer
   */
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
