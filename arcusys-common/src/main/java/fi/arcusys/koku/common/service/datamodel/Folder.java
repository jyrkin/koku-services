package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Entity for representing message folder in KV-Messages functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "findFolderByUserAndType", query = "SELECT f FROM Folder f WHERE f.user = :user AND f.folderType = :folderType"),
	
	@NamedQuery(name = "findMessagesByUserAndFolderType", query = "SELECT DISTINCT mr FROM MessageRef mr " +
			" WHERE mr.folder.folderType = :folderType AND " +
			" (mr.folder.user = :user) " +
			" ORDER BY mr.createdDate DESC, mr.id DESC"),
    @NamedQuery(name = "findMessagesByUserWithRoleAndFolderType", query = "SELECT DISTINCT mr FROM MessageRef mr " +
            " WHERE mr.folder.folderType = :folderType AND " +
            " (mr.folder.user = :user OR mr.message.fromRoleUid in (:userRoles)) " +
            " ORDER BY mr.createdDate DESC, mr.id DESC"),

    @NamedQuery(name = "getTotalMessagesCount", query = "SELECT COUNT(mr) FROM MessageRef mr " +
            " WHERE mr.folder.folderType = :folderType AND " +
            " (mr.folder.user = :user) "),
	@NamedQuery(name = "getTotalMessagesCountWithRole", query = "SELECT COUNT(mr) FROM MessageRef mr " +
			" WHERE mr.folder.folderType = :folderType AND " +
			" (mr.folder.user = :user OR mr.message.fromRoleUid in (:userRoles)) "),
			
	@NamedQuery(name = "getMessagesCountByReadStatus", query = "SELECT COUNT(mr) FROM MessageRef mr " +
			" WHERE mr.folder.folderType = :folderType AND " +
			" (mr.folder.user = :user) AND mr.isRead = :isRead"),
    @NamedQuery(name = "getMessagesCountByReadStatusWithRole", query = "SELECT COUNT(mr) FROM MessageRef mr " +
            " WHERE mr.folder.folderType = :folderType AND " +
            " (mr.folder.user = :user OR mr.message.fromRoleUid in (:userRoles)) AND mr.isRead = :isRead")
}) 

public class Folder extends AbstractEntity {
	@Enumerated(EnumType.STRING)
	private FolderType folderType;
	
	@ManyToOne
	private User user;

	/**
	 * @return the type
	 */
	public FolderType getFolderType() {
		return this.folderType;
	}

	/**
	 * @param type the type to set
	 */
	public void setFolderType(FolderType type) {
		this.folderType = type;
	}

	/**
	 * @param testUser
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
}
