package com.groupdocs.plugins.confluence.action.groupdocs;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class GroupDocsAction extends ConfluenceActionSupport {

    private GroupDocsManager groupDocsManager;
    private String groupDocsLogin;
    private String groupDocsPassword;
    private String groupDocsClientId;
    private String groupDocsPrivateKey;

    public String saveAccountInfo() {
        if(groupDocsLogin != null && !groupDocsLogin.equals("") && groupDocsPassword != null && !groupDocsPassword.equals("")){
            groupDocsManager.userLogin(getRemoteUser(), groupDocsLogin, groupDocsPassword);
        }else{
            groupDocsManager.saveAccountInfo(getRemoteUser(), groupDocsClientId, groupDocsPrivateKey);
        }
        return SUCCESS;
    }

    public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
        this.groupDocsManager = groupDocsManager;
    }

    public void setGroupDocsLogin(String groupDocsLogin) {
        this.groupDocsLogin = groupDocsLogin;
    }
    
    public void setGroupDocsPassword(String groupDocsPassword) {
        this.groupDocsPassword = groupDocsPassword;
    }

    public void setGroupDocsClientId(String groupDocsClientId) {
        if(groupDocsClientId != null && !groupDocsClientId.equals("")){
            this.groupDocsClientId = groupDocsClientId;
        }else{
           this.groupDocsClientId = null; 
        }
    }

    public void setGroupDocsPrivateKey(String groupDocsPrivateKey) {
        if(groupDocsPrivateKey != null && !groupDocsPrivateKey.equals("")){
            this.groupDocsPrivateKey = groupDocsPrivateKey;
        }else{
            this.groupDocsPrivateKey = null;
        }
    }
}
