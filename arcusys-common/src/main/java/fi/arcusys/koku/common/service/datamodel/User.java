package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Entity(name = "User_")
@NamedQueries({
	@NamedQuery(name = "findUserByUid", query = "SELECT DISTINCT u FROM User_ u WHERE u.uid = :uid")
}) 
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uid"})
})
public class User extends AbstractEntity {
	/**
	 * Natural ID, e.g. in LDAP (uid=user,cn=people,dn=example,dn=com)
	 */
	private String uid;

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
