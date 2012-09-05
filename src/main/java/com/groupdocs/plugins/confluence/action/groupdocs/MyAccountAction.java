package com.groupdocs.plugins.confluence.action.groupdocs;

import java.util.Map;

import com.atlassian.confluence.user.actions.AbstractUserProfileAction;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class MyAccountAction extends AbstractUserProfileAction {

	private GroupDocsManager groupDocsManager;
	private String groupDocsClientId;
	private String groupDocsPrivateKey;
	private String dashboardUrl;
	
	public String account() {
		return SUCCESS;
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
