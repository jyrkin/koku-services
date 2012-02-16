package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Folder;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.Message;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;

/**
 * DAO interface for CRUD operations with 'Folder' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface MessageFolderDAO extends AbstractEntityDAO<Folder>{

	/**
	 * @param testUser
	 * @param string
	 * @return
	 */
	Folder getFolderByUserAndType(final User user, final FolderType folderType);

	/**
	 * @param fromUser
	 * @param sent
	 * @param msg
	 */
	MessageRef storeMessage(final User user, final FolderType folderType, final Message message);

	/**
	 * @param testUser
	 * @param outbox
	 * @return
	 */
	List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType);

    List<MessageRef> getMessagesByUserWithRoleAndFolderType(final User user, final List<String> roleUids, final FolderType folderType, 
            final MessageQuery query, final int startNum, final int maxNum);

	/**
	 * @param testUser
	 * @param sent
	 */
	Folder createNewFolderByUserAndType(final User user, final FolderType folderType);

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	Long getTotalMessagesCountByUserAndRoles(final User user, final List<String> roleUids, final FolderType folderType);

	Long getTotalMessagesCountByUserAndRoles(final User user, final List<String> roleUids, final FolderType folderType, final Criteria criteria);

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	Long getUnreadMessagesCountByUserAndRoles(final User user, final List<String> roleUids, final FolderType folderType);
}