package fi.koku.services.utility.portaluser.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.entity.customer.v1.AddressType;
import fi.koku.services.entity.customer.v1.AddressesType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfoType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfosType;
import fi.koku.services.entity.customer.v1.PhoneNumberType;
import fi.koku.services.entity.customer.v1.PhoneNumbersType;
import fi.koku.services.utility.portal.v1.ContactInfoType;
import fi.koku.services.utility.portal.v1.ContactInfoUpdateType;
import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;

/**
 * Convert between webservice type (PortalUserType, CustomerType,
 * PortalUserUpdateType) and the internal object representation.
 * 
 * @author hekkata
 */
public class PortalUserConverter {

  private Logger logger = LoggerFactory.getLogger(PortalUserConverter.class);
  
  private PortalUserPasswordEncryption encrypt;
  
  public static final String STATUS_ELOSSA = "elossa";
  public static final String KANSALAISUUSKOODI_FI = "fi";
  public static final String OSOITE_VIRALLINEN = "virallinen";
  public static final String PUH_GSM = "gsm";
  public static final String PUH_KOTIPUHELIN = "kotipuhelin";
  public static final String ELECTRONIC_INFO_TYPE = "email";
  
  /**
   * Instantiates a new portal user converter.
   */
  public PortalUserConverter() {
    encrypt = new PortalUserPasswordEncryption();
  }

  /**
   * Converts PortalUser and CustomerType to single PortalUseAllType
   * to webservice.
   *
   * @param pu PortalUser table
   * @param ct CustomerType single customer from customer service
   * @return the portal user all type
   */
  public PortalUserAllType toWsType(PortalUser pu, CustomerType ct) {
    
    //from portal user
    PortalUserAllType pt = new PortalUserAllType();
    pt.setUserName(pu.getUserName());
    pt.setNotificationMethod(pu.getNotificationMethod());
    pt.setCreationTime(CalendarUtil.getXmlDate(pu.getCreationTime()));
    pt.setModificationTime(CalendarUtil.getXmlDate(pu.getModificationTime()));
    pt.setLastLogin(CalendarUtil.getXmlDate(pu.getLastLoginTime()));
    pt.setDisabled(pu.getDisabled());
    pt.setLockedTime(CalendarUtil.getXmlDate(pu.getLockedTime()));
    pt.setPasswordChanged(CalendarUtil.getXmlDate(pu.getPasswordChanged()));
    pt.setWrongPwdCount(pu.getWrongPasswordCount());

    //from customer service single customer
    pt.setCustomerId(ct.getId());
    pt.setFirstNames(ct.getEtuNimi());
    pt.setPic(ct.getHenkiloTunnus());
    pt.setBirthDate(ct.getSyntymaPvm());
    pt.setSurName(ct.getSukuNimi());
    
    //set contactinfo(s)
    ContactInfoType cit = new ContactInfoType();
    AddressesType aty = new AddressesType();
    PhoneNumbersType pnt = new PhoneNumbersType();
    ElectronicContactInfosType ect = new ElectronicContactInfosType();
    
    if(ct.getAddresses()!=null | ct.getPhoneNumbers()!=null | ct.getElectronicContactInfos()!=null)
    {    
    if ( ct.getAddresses() != null){
        aty = ct.getAddresses();
      for( AddressType a : aty.getAddress() )
      {
      cit.setStreetAddress(a.getKatuNimi());
      cit.setPostalCode(a.getPostinumeroKoodi());
      cit.setCity(a.getPostitoimipaikkaNimi());
      cit.setCountry(a.getMaatunnusKoodi());
      }
    }
    
    if ( ct.getPhoneNumbers() != null){
       pnt = ct.getPhoneNumbers();
       for(PhoneNumberType pn : pnt.getPhone())
       {
         cit.setPhoneNumber(pn.getPuhelinnumeroTeksti());         
       }
    }
    
    if ( ct.getElectronicContactInfos() != null){
       ect = ct.getElectronicContactInfos();
       for( ElectronicContactInfoType ec : ect.getEContactInfo())
       {
         cit.setEmail(ec.getContactInfo());
       }
      
    }
    pt.getContactInfos().add(cit);
    }    
    
    return pt;
  }

  
  /**
   * Converts PortalUser to customer ws type. Converts only single user.
   *
   * @param pu the portal user
   * @return the customer type
   */
  public CustomerType ToCustomerWsType(PortalUserType pu) {
    
    CustomerType c = new CustomerType();
    c.setStatus(STATUS_ELOSSA);
    c.setStatusDate(CalendarUtil.getXmlDate(new Date()));
    c.setHenkiloTunnus(pu.getPic());
    c.setSyntymaPvm(pu.getBirthDate());
    c.setEtuNimi(pu.getFirstNames());
    c.setSukuNimi(pu.getSurName());
    c.setEtunimetNimi("");
    c.setKansalaisuusKoodi(KANSALAISUUSKOODI_FI);
    c.setKuntaKoodi("");
    c.setKieliKoodi("");
    c.setTurvakieltoKytkin(false);
    
    AddressesType at = new AddressesType();
    PhoneNumbersType pt = new PhoneNumbersType();
    ElectronicContactInfosType et = new ElectronicContactInfosType();
    
    if (pu.getContactInfos() != null) {
      for (ContactInfoType ct : pu.getContactInfos()) {
        // osoitteet
        AddressType a = new AddressType();
        a.setAddressType(OSOITE_VIRALLINEN);
        a.setKatuNimi(ct.getStreetAddress());
        a.setPostitoimipaikkaNimi(ct.getCity());
        a.setPostinumeroKoodi(ct.getPostalCode());
        a.setPostilokeroTeksti("");
        a.setMaatunnusKoodi("");
        a.setAlkuPvm(null);
        a.setLoppuPvm(null);
        at.getAddress().add(a);
        // puhelinnumerot
        PhoneNumberType p = new PhoneNumberType();
        p.setNumberClass(PUH_GSM);
        p.setNumberType(PUH_KOTIPUHELIN);
        p.setPuhelinnumeroTeksti(ct.getPhoneNumber());
        pt.getPhone().add(p);
        // sähköpostit
        ElectronicContactInfoType e = new ElectronicContactInfoType();
        e.setContactInfoType(ELECTRONIC_INFO_TYPE);
        e.setContactInfo(ct.getEmail());
        et.getEContactInfo().add(e);
      }
        c.setAddresses(at);
        c.setPhoneNumbers(pt);
        c.setElectronicContactInfos(et);        
      }
    return c;
  }

  /**
   * Converts PortalUserType to PortalUser entity type 
   * from webservice.
   *
   * @param pu the PortalUserType
   * @return the portal user
   */
  public PortalUser fromWsType(PortalUserType pu) {

    String salt = encrypt.getSalt();

    PortalUser p = new PortalUser();
    // portal user table
    p.setUserName(pu.getUserName());
    p.setNotificationMethod(pu.getNotificationMethod());
    p.setCreationTime(new Date());
    p.setModificationTime(new Date());
    p.setLastLoginTime(new Date());
    p.setDisabled(pu.getDisabled());
    p.setLockedTime(new Date());
    p.setSalt(salt); // salt setting before password
    p.setPassword(encrypt.getEncryptedPassword(pu.getPassword(), salt));
    p.setPasswordChanged(new Date());
    p.setWrongPasswordCount(0);

    return p;
  }
  
  /**
   * Used on updating portal user. Returns merger PortaUser entity with changes
   * given via webservice.
   *
   * @param pu Portal user update params
   * @param p Portal user to be updated
   * @return the portal user
   */
  public PortalUser updateFromWsType(PortalUserUpdateType pu, PortalUser p) {

    PortalUserPasswordEncryption encrypt = new PortalUserPasswordEncryption();

    String salt = encrypt.getSalt();
    
    p.setModificationTime(new Date());
    p.setLastLoginTime(new Date());
        
    if (pu.getNotificationMethod() != null) {
      p.setNotificationMethod(pu.getNotificationMethod());
      logger.debug("updatePortalUser: notification mehtod update to: " + p.getNotificationMethod());
    }
    if (pu.getNewPassword() != null) {
      p.setSalt(salt);
      p.setPassword(encrypt.getEncryptedPassword(pu.getNewPassword(), salt));
      p.setPasswordChanged(new Date());
      logger.debug("updatePortalUser: password update");
    }

    return p;
  }
  
  /**
   * Portal user Update type to user type.
   *
   * @param up PortalUserUpdateType
   * @return the portal user type
   */
  public PortalUserType UpdateTypeToUserType(PortalUserUpdateType up)
  {
    PortalUserType pt = new PortalUserType();
    pt.setFirstNames(up.getFirstNames());
    pt.setSurName(up.getSurName());
    pt.setUserName(up.getUserName());
    pt.setPassword(up.getPassword());
        
    for(ContactInfoUpdateType ciu : up.getContactInfoUpdates())
    {
    ContactInfoType ct = new ContactInfoType();  
    ct.setCity(ciu.getCity());
    ct.setCountry(ciu.getCountry());
    ct.setEmail(ciu.getEmail());
    ct.setPhoneNumber(ciu.getPhoneNumber());
    ct.setPostalCode(ciu.getPhoneNumber());
    ct.setStreetAddress(ciu.getStreetAddress());
    pt.getContactInfos().add(ct);
    }
    return pt;    
  }
  
  /**
   * Update type to customer type.
   *
   * @param up the up
   * @return the customer type
   */
  public CustomerType UpdateTypeToCustomerType(PortalUserUpdateType up)
  {
    CustomerType c = new CustomerType();
    c.setHenkiloTunnus(up.getPic());
    if (up.getFirstNames() != null) {
      c.setEtuNimi(up.getFirstNames());
      logger.debug("updatePortalUser: first name update to: " + up.getFirstNames());
    }
    if (up.getSurName() != null) {
      c.setSukuNimi(up.getSurName());
      logger.debug("updatePortalUser: surname update to: " + up.getSurName());
    }
        
    AddressesType at = new AddressesType();
    PhoneNumbersType pt = new PhoneNumbersType();
    ElectronicContactInfosType et = new ElectronicContactInfosType();
    
    if (up.getContactInfoUpdates() != null) {
      for (ContactInfoUpdateType ct : up.getContactInfoUpdates()) {
        
        // osoitteet
        AddressType a = new AddressType();
        if (ct.getStreetAddress() !=null)
        {
        a.setKatuNimi(ct.getStreetAddress());
        logger.debug("updatePortalUser: address update to: " + ct.getStreetAddress());
        }
        if (ct.getCity() !=null)
        {
        a.setPostitoimipaikkaNimi(ct.getCity());
        logger.debug("updatePortalUser: city update to: " + ct.getCity());
        }
        if (ct.getPostalCode() !=null)
        {
          a.setPostinumeroKoodi(ct.getPostalCode());
        logger.debug("updatePortalUser: postal code update to: " + ct.getPostalCode());
        }
        at.getAddress().add(a);
        
        // puhelinnumerot
        PhoneNumberType p = new PhoneNumberType();
        if (ct.getPhoneNumber() !=null)
        {
          p.setPuhelinnumeroTeksti(ct.getPhoneNumber());
        logger.debug("updatePortalUser: phone number update to: " + ct.getPhoneNumber());
        }        
        pt.getPhone().add(p);
        
        // sähköpostit
        ElectronicContactInfoType e = new ElectronicContactInfoType();
        if (ct.getEmail() !=null)
        {
        e.setContactInfo(ct.getEmail());
        logger.debug("updatePortalUser: email update to: " + ct.getEmail());
        }        
        et.getEContactInfo().add(e);
        
      }
        c.setAddresses(at);
        c.setPhoneNumbers(pt);
        c.setElectronicContactInfos(et);        
      }

    return c;
  }

  /**
   * Update customer from update request.
   *
   * @param c the customer being updated
   * @param cu the customer from update is done 
   * @return the customer type updated customer
   */
  public CustomerType updateCustomer(CustomerType c, CustomerType cu)
  {
    if(cu.getEtuNimi()!= null)
    {
      c.setEtuNimi(cu.getEtuNimi());
    }
    if(cu.getEtunimetNimi()!= null)
    {
      c.setEtunimetNimi(cu.getEtunimetNimi());
    }
    if(cu.getSukuNimi()!= null)
    {
      c.setSukuNimi(cu.getSukuNimi());
    }
    
    if(cu.getAddresses()!=null | cu.getPhoneNumbers()!=null | cu.getElectronicContactInfos()!=null)
    {    
    if ( cu.getAddresses() != null){
      List<AddressType> upList = cu.getAddresses().getAddress();
      for(int i=0; i<upList.size(); i++)
      {
       if(upList.get(i).getKatuNimi()!=null)
       {
       c.getAddresses().getAddress().get(i).setKatuNimi(upList.get(i).getKatuNimi());
       }
       if(upList.get(i).getPostinumeroKoodi()!=null)
       {
         c.getAddresses().getAddress().get(i).setPostinumeroKoodi(upList.get(i).getPostinumeroKoodi());
       }
       if(upList.get(i).getPostitoimipaikkaNimi()!=null)
       {
         c.getAddresses().getAddress().get(i).setPostitoimipaikkaNimi(upList.get(i).getPostitoimipaikkaNimi());
       }
      }      
    }                  
    if ( cu.getPhoneNumbers() != null){
      List<PhoneNumberType> upList = cu.getPhoneNumbers().getPhone();      
      for(int i=0; i<upList.size(); i++)
      {
       if(upList.get(i).getPuhelinnumeroTeksti()!=null)
       {
       c.getPhoneNumbers().getPhone().get(i).setPuhelinnumeroTeksti(upList.get(i).getPuhelinnumeroTeksti());
       }       
      }
    }      
    if ( cu.getElectronicContactInfos() != null){
        List<ElectronicContactInfoType> upList = cu.getElectronicContactInfos().getEContactInfo();
        for(int i=0; i<upList.size(); i++)
        {
         if(upList.get(i).getContactInfo()!=null)
         {
         c.getElectronicContactInfos().getEContactInfo().get(i).setContactInfo(upList.get(i).getContactInfo());
         }       
        }
     }
    }
    
    return c;
  }
 
  
}