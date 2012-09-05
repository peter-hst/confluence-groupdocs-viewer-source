package com.groupdocs.plugins.confluence.component.groupdocs;

import java.util.List;

import com.atlassian.user.User;
import com.groupdocs.plugins.confluence.action.groupdocs.AdminAction.GroupdocsSettings;
import com.groupdocs.plugins.confluence.util.AuthException;

public interface GroupDocsManager {

	String CLIENT_ID = "com.groupdocs.confluence.clientid";
	String PRIVATE_KEY = "com.groupdocs.confluence.privatekey";
	
	String getClientId(User user);
	
	String getPrivateKey(User user);

	void saveAccountInfo(User user, String groupDocsClientId, String groupDocsPrivateKey);

	List<FileItem> getFolderContents(String path, String clientId, String privateKey) throws AuthException;

	GroupdocsSettings getSettings();

	void saveSettings(GroupdocsSettings groupdocsSettins);

	String getDashboardUrl();

}
