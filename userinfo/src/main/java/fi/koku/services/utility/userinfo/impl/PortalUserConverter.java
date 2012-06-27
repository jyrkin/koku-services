package fi.koku.services.utility.userinfo.impl;

import java.util.Date;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.user.v1.ContactInfoType;
import fi.koku.services.utility.user.v1.PortalUserAllType;
import fi.koku.services.utility.user.v1.PortalUserType;

/**
 * Convert between webservice type (PortalUserType) and the internal object
 * representation.
 * 
 * @author hekkata
 */
public class PortalUserConverter {

  private UserInfoPasswordEncryption encrypt;

  public PortalUserConverter() {
    encrypt = new UserInfoPasswordEncryption();
  }

  public PortalUserAllType toWsType(PortalUser pu) {
    PortalUserAllType pt = new PortalUserAllType();
    pt.setFirstNames(pu.getFirstNames());
    pt.setModificationTime(CalendarUtil.getXmlDate(pu.getModificationTime()));
    pt.setPic(pu.getPic());
    pt.setSurName(pu.getSurName());
    pt.setCreationTime(CalendarUtil.getXmlDate(pu.getCreationTime()));
    pt.setDisabled(pu.getDisabled());
    pt.setLastLogin(CalendarUtil.getXmlDate(pu.getLastLoginTime()));
    pt.setLockedTime(CalendarUtil.getXmlDate(pu.getLockedTime()));
    pt.setNotificationMethod(pu.getNotificationMethod());
    pt.setPasswordChanged(CalendarUtil.getXmlDate(pu.getPasswordChanged()));
    pt.setUserName(pu.getUserName());
    pt.setWrongPwdCount(pu.getWrongPasswordCount());

    for (PortalUserContactInfo pi : pu.getPortalUserContactInfo()) {
      ContactInfoType ci = new ContactInfoType();
      ci.setEmail(pi.getEmail());
      ci.setPhoneNumber(pi.getPhoneNumber());
      ci.setCity(pi.getCity());
      ci.setCountry(pi.getCountry());
      ci.setPostalCode(pi.getPostalCode());
      ci.setStreetAddress(pi.getStreetAddress());
      pt.getContactInfos().add(ci);
    }

    return pt;
  }

  public PortalUser fromWsType(PortalUserType pu) {

    String salt = encrypt.getSalt();

    PortalUser p = new PortalUser();
    p.setFirstNames(pu.getFirstNames());
    p.setModificationTime(new Date());
    p.setPic(pu.getPic());
    p.setSurName(pu.getSurName());
    p.setCreationTime(new Date());
    p.setDisabled(pu.getDisabled());
    p.setLastLoginTime(new Date());
    p.setLockedTime(new Date());
    p.setNotificationMethod(pu.getNotificationMethod());
    p.setSalt(salt); // salt setting before password
    p.setPassword(encrypt.getEncryptedPassword(pu.getPassword(), salt));
    p.setPasswordChanged(new Date());
    p.setUserName(pu.getUserName());
    p.setWrongPasswordCount(0);

    if (pu.getContactInfos() != null) {
      for (ContactInfoType ct : pu.getContactInfos()) {
        PortalUserContactInfo pc = new PortalUserContactInfo();
        pc.setEmail(ct.getEmail());
        pc.setPhoneNumber(ct.getPhoneNumber());
        pc.setCity(ct.getCity());
        pc.setCountry(ct.getCountry());
        pc.setPostalCode(ct.getPostalCode());
        pc.setStreetAddress(ct.getStreetAddress());
        p.getPortalUserContactInfo().add(pc);
        pc.setPortalUser(p);
      }
    }

    return p;
  }
}