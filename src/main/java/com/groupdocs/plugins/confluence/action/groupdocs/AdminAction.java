package com.groupdocs.plugins.confluence.action.groupdocs;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;
import com.opensymphony.webwork.ServletActionContext;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang.StringUtils;

public class AdminAction extends ConfluenceActionSupport {

	private GroupDocsManager groupDocsManager;
	private String apiUrlRoot;
	private String viewerUrlRoot;
	private String dashboardUrl;

	public String settings(){
		GroupdocsSettings stg = groupDocsManager.getSettings();
		try {
                    URL url = new URL(getGlobalSettings().getBaseUrl());
                    if(url.getProtocol() != null && url.getProtocol().equalsIgnoreCase("http")){
                            stg.setProtocol(url.getProtocol());
                    }
		} catch (MalformedURLException e) {
		}

		setApiUrlRoot(stg.getApiUrl());
		setViewerUrlRoot(stg.getViewerUrl());
		setDashboardUrl(stg.getDashboardUrl());
		return SUCCESS;
	}

	public String saveSettings(){
		if(getApiUrlRoot() == null){
			apiUrlRoot = ServletActionContext.getRequest().getParameter("apiUrlRoot");
		}
		if(getViewerUrlRoot() == null){
			viewerUrlRoot = ServletActionContext.getRequest().getParameter("viewerUrlRoot");
		}
		if(getDashboardUrl() == null){
			dashboardUrl = ServletActionContext.getRequest().getParameter("dashboardUrl");
		}

		if(StringUtils.isEmpty(getApiUrlRoot()) || StringUtils.isEmpty(getViewerUrlRoot()) || StringUtils.isEmpty(getDashboardUrl())){
			addActionError("Please specify valid URLs");
			return ERROR;
		}
		System.out.println("saving settings: " + getApiUrlRoot() + " " + getViewerUrlRoot());
		groupDocsManager.saveSettings(new GroupdocsSettings(getApiUrlRoot(), getViewerUrlRoot(), getDashboardUrl()));
		addActionMessage("Settings Saved");
		return SUCCESS;
	}

	public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
		this.groupDocsManager = groupDocsManager;
	}


	public String getApiUrlRoot() {
		return apiUrlRoot;
	}

	public void setApiUrlRoot(String apiUrlRoot) {
		this.apiUrlRoot = apiUrlRoot;
	}


	public String getViewerUrlRoot() {
		return viewerUrlRoot;
	}

	public void setViewerUrlRoot(String viewerUrlRoot) {
		this.viewerUrlRoot = viewerUrlRoot;
	}


	public String getDashboardUrl() {
		return dashboardUrl;
	}

	public void setDashboardUrl(String dashboardUrl) {
		this.dashboardUrl = dashboardUrl;
	}


	public static class GroupdocsSettings {
		private String apiUrl;
		private String viewerUrl;
		private String dashboardUrl;
		private String protocol = "http";

		public GroupdocsSettings(){
			this(null, null, null);
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public GroupdocsSettings(String apiUrl, String viewerUrl, String dashboardUrl){
			this.apiUrl = apiUrl;
			this.viewerUrl = viewerUrl;
			this.dashboardUrl = dashboardUrl;
		}

		public String getApiUrl() {
			if(StringUtils.isEmpty(apiUrl)){
				return "https://api.groupdocs.com/v2.0/";
			}
			return apiUrl;
		}

		public void setApiUrl(String apiUrl) {
			this.apiUrl = apiUrl.trim();
		}

		public String getViewerUrl() {
			if(StringUtils.isEmpty(viewerUrl)){
				return protocol + "://apps.groupdocs.com/document-viewer/Embed/";
			}
			return viewerUrl;
		}

		public void setViewerUrl(String viewerUrl) {
			this.viewerUrl = viewerUrl.trim();
		}

		@Override
		public String toString() {
			return apiUrl + " " + viewerUrl;
		}

		public String getDashboardUrl() {
			if(StringUtils.isEmpty(dashboardUrl)){
				return protocol + "://apps.groupdocs.com";
			}
			return dashboardUrl;
		}

		public void setDashboardUrl(String dashboardUrl) {
			this.dashboardUrl = dashboardUrl;
		}

	}

}
