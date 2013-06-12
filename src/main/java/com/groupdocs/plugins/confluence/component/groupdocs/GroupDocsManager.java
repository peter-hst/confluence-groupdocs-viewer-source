package com.groupdocs.plugins.confluence.component.groupdocs;

import com.atlassian.user.User;
import com.groupdocs.plugins.confluence.action.groupdocs.AdminAction.GroupdocsSettings;
import com.groupdocs.plugins.confluence.util.AuthException;
import java.util.List;

public interface GroupDocsManager {
    public String LOGIN = "com.groupdocs.confluence.login";
    public String PASSWORD = "com.groupdocs.confluence.password";
    public String CLIENT_ID = "com.groupdocs.confluence.clientid";
    public String PRIVATE_KEY = "com.groupdocs.confluence.privatekey";

    public String getLogin(User user);
    public String getPassword(User user);
    public String getClientId(User user);
    public String getPrivateKey(User user);
    public String getDashboardUrl();
    public List<FileItem> getFolderContents(String path, String clientId, String privateKey) throws AuthException;
    public GroupdocsSettings getSettings();
    public void saveAccountInfo(User user, String groupDocsClientId, String groupDocsPrivateKey);
    public void saveSettings(GroupdocsSettings groupdocsSettins);
    public void userLogin(User user, String login, String password);
}
