package com.groupdocs.plugins.confluence.macro.groupdocs;

import java.util.Map;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class ViewGroupdocsFolderMacro extends BaseMacro {
	private static final String ENC = "UTF-8";
	private static final String TEMPLATE_PATH = "groupdocs/templates/macros/groupdocs/viewFolder.vm";
	private static final String AUTH_TEMPLATE_PATH = "groupdocs/templates/macros/groupdocs/authError.vm";
	private static final String DEFAULT_WIDTH = "960";
	private static final String DEFAULT_HEIGHT = "640";
	
	private GroupDocsManager groupDocsManager;

	
	public String execute(Map parameters, String body, RenderContext renderContext) throws MacroException {
		Map<String, Object> contextMap = MacroUtils.defaultVelocityContext();

		String clientId = groupDocsManager.getClientId(AuthenticatedUserThreadLocal.getUser());
		String privateKey = groupDocsManager.getPrivateKey(AuthenticatedUserThreadLocal.getUser());

		if (clientId != null && !clientId.isEmpty() && privateKey != null && !privateKey.isEmpty() ) {
			Object width = parameters.get("width");
			Object height = parameters.get("height");
			String guid = (String) parameters.get("path");
			String title = (String) parameters.get("title");
			contextMap.put("title", title);
			contextMap.put("path", guid);
			contextMap.put("width", width == null ? DEFAULT_WIDTH : width);
			contextMap.put("height", height == null ? DEFAULT_HEIGHT : height);

			return VelocityUtils.getRenderedTemplate(TEMPLATE_PATH, contextMap);
		}

		return VelocityUtils.getRenderedTemplate(AUTH_TEMPLATE_PATH, contextMap);
	}

	public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
		this.groupDocsManager = groupDocsManager;
	}

    public boolean isInline()
    {
        return false;
    }

    public boolean hasBody()
    {
        return false;
    }

    public RenderMode getBodyRenderMode()
    {
        return RenderMode.NO_RENDER;
    }

}