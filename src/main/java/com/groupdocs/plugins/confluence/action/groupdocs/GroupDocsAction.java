package com.groupdocs.plugins.confluence.action.groupdocs;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class GroupDocsAction extends ConfluenceActionSupport {
	
	private GroupDocsManager groupDocsManager;
	private String groupDocsClientId;
	private String groupDocsPrivateKey;

	public String saveAccountInfo(){
		System.out.println(groupDocsClientId + " " + groupDocsPrivateKey);
		groupDocsManager.saveAccountInfo(getRemoteUser(), groupDocsClientId, groupDocsPrivateKey);
		return SUCCESS;
	}

	public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
		this.groupDocsManager = groupDocsManager;
	}

	public void setGroupDocsClientId(String groupDocsClientId) {
		this.groupDocsClientId = groupDocsClientId;
	}

	public void setGroupDocsPrivateKey(String groupDocsPrivateKey) {
		this.groupDocsPrivateKey = groupDocsPrivateKey;
	}
	
}
