package com.groupdocs.plugins.confluence.action.groupdocs;

import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class MyAccountAction extends AbstractUserProfileAction {

	private GroupDocsManager groupDocsManager;
        private String groupDocsLogin;
        private String groupDocsPassword;
	private String groupDocsClientId;
	private String groupDocsPrivateKey;
	private String dashboardUrl;
	
	public String account() {
		return SUCCESS;
	}
        
        public String getGroupDocsLogin(){
		if(groupDocsLogin == null){
			groupDocsLogin = groupDocsManager.getLogin(getRemoteUser());
		}
		return groupDocsLogin;
	}
        
        public String getGroupDocsPassword(){
		if(groupDocsPassword == null){
			groupDocsPassword = groupDocsManager.getPassword(getRemoteUser());
		}
		return groupDocsPassword;
	}

	public String getGroupDocsClientId(){
		if(groupDocsClientId == null){
			groupDocsClientId = groupDocsManager.getClientId(getRemoteUser());
		}
		return groupDocsClientId;
	}

	public String getGroupDocsPrivateKey(){
		if(groupDocsPrivateKey == null){
			groupDocsPrivateKey = groupDocsManager.getPrivateKey(getRemoteUser());
		}
		return groupDocsPrivateKey;
	}

	public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
		this.groupDocsManager = groupDocsManager;
	}

	public String getDashboardUrl() {
		if(dashboardUrl == null){
			dashboardUrl = groupDocsManager.getDashboardUrl();
		}
		return dashboardUrl;
	}

//	public Map<String, String> getSharedFolders(){
//		// there is no api method to get list of shared folders, so store it in confluence
//		return groupDocsManager.getSharedFolders();
//	}

}
