package com.groupdocs.plugins.confluence.component.groupdocs;

import java.util.ArrayList;
import java.util.List;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.user.User;
import com.groupdocs.plugins.confluence.action.groupdocs.AdminAction.GroupdocsSettings;
import com.groupdocs.plugins.confluence.util.AuthException;
import com.groupdocs.plugins.confluence.util.Utils;
import com.groupdocs.sdk.java.api.StorageAPI;
import com.groupdocs.sdk.java.model.FileSystemDocument;
import com.groupdocs.sdk.java.model.FileSystemFolder;
import com.groupdocs.sdk.java.model.ListEntitiesResponse;
import com.groupdocs.sdk.java.model.StorageStorageInputFoldersInput;
import com.wordnik.swagger.runtime.common.APIInvoker;
import com.wordnik.swagger.runtime.common.GroupDocsUrlSigningSecurityHandler;
import com.wordnik.swagger.runtime.exception.APIException;

public class GroupDocsManagerImpl implements GroupDocsManager {

	private static final String GROUPDOCS_API_URL = "groupdocs.apiUrl";
	private static final String GROUPDOCS_VIEWER_URL = "groupdocs.viewerUrl";
	private static final String GROUPDOCS_DASHBOARD_URL = "groupdocs.dashboardUrl";

	private PluginSettingsFactory pluginSettingsFactory;
	private BandanaManager bandanaManager;

	@Override
	public String getClientId(User user) {
		return (String) getPluginSettings(CLIENT_ID).get(user.getName());
	}

	@Override
	public String getPrivateKey(User user) {
		return (String) getPluginSettings(PRIVATE_KEY).get(user.getName());
	}

	@Override
	public void saveAccountInfo(User user, String groupDocsClientId, String groupDocsPrivateKey) {
		getPluginSettings(CLIENT_ID).put(user.getName(), groupDocsClientId);
		getPluginSettings(PRIVATE_KEY).put(user.getName(), groupDocsPrivateKey);
	}

	@Override
	public List<FileItem> getFolderContents(String path, String clientId, String privateKey) throws AuthException {
		String apiServer = getSettings().getApiUrl();
		APIInvoker apiInvoker = APIInvoker.initialize(new GroupDocsUrlSigningSecurityHandler(privateKey), apiServer , true);
		StorageAPI.setApiInvoker(apiInvoker);
		StorageStorageInputFoldersInput input = new StorageStorageInputFoldersInput();
		input.setUserId(clientId);
		input.setPageIndex("0");
		input.setPageSize("0");
		input.setOrderAsc("false");
		input.setExtended("false");
		if(path != null && !path.isEmpty()){
			input.setPath(path);
		}
		try {
			ListEntitiesResponse response = StorageAPI.ListEntities(input);
			List<FileSystemDocument> files = response.getResult().getFiles();
			List<FileSystemFolder> folders = response.getResult().getFolders();

			List<FileItem> items = new ArrayList<FileItem>();
			for (FileSystemFolder doc : folders) {
				FileItem item = new FileItem();
				item.setTitle(doc.getName());
				item.setType("folder");
				item.setPath(doc.getName());

				items.add(item);
			}
			for (FileSystemDocument doc : files) {
				FileItem item = new FileItem();
				item.setTitle(doc.getName());
				item.setId(doc.getGuid());
				item.setExtension(Utils.getFileExtension(doc.getName()));

				items.add(item);
			}
			return items;

		} catch (APIException e) {
			if(e.getCode() == 401 || e.getCode() == 403){
				throw new AuthException();
			} else {
				e.printStackTrace();
				return null;
			}
		}
	}

	private PluginSettings getPluginSettings(String key) {
		return this.pluginSettingsFactory.createSettingsForKey(key);
	}

	public void setPluginSettingsFactory(PluginSettingsFactory pluginSettingsFactory) {
		this.pluginSettingsFactory = pluginSettingsFactory;
	}

	public void setBandanaManager(BandanaManager manager) {
		this.bandanaManager = manager;
	}

	@Override
	public GroupdocsSettings getSettings() {
		ConfluenceBandanaContext ctx = new ConfluenceBandanaContext();
		GroupdocsSettings groupdocsSettings = new GroupdocsSettings((String)bandanaManager.getValue(ctx, GROUPDOCS_API_URL),
				(String)bandanaManager.getValue(ctx, GROUPDOCS_VIEWER_URL),
				(String)bandanaManager.getValue(ctx, GROUPDOCS_DASHBOARD_URL));
		return groupdocsSettings;
	}

	@Override
	public void saveSettings(GroupdocsSettings groupdocsSettins) {
		ConfluenceBandanaContext ctx = new ConfluenceBandanaContext();
		bandanaManager.setValue(ctx, GROUPDOCS_API_URL, groupdocsSettins.getApiUrl());
		bandanaManager.setValue(ctx, GROUPDOCS_VIEWER_URL, groupdocsSettins.getViewerUrl());
		bandanaManager.setValue(ctx, GROUPDOCS_DASHBOARD_URL, groupdocsSettins.getDashboardUrl());
	}

	@Override
	public String getDashboardUrl() {
		ConfluenceBandanaContext ctx = new ConfluenceBandanaContext();
		return (String)bandanaManager.getValue(ctx, GROUPDOCS_DASHBOARD_URL);
	}

}
