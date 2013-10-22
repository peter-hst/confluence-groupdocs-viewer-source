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
import com.groupdocs.sdk.common.GroupDocsRequestSigner;

public class ViewGroupdocsDocumentMacro extends BaseMacro {
	private static final String TEMPLATE_PATH = "groupdocs/templates/macros/groupdocs/viewDoc.vm";
	private static final String DEFAULT_WIDTH = "100%";
	private static final String DEFAULT_HEIGHT = "400px";

	private GroupDocsManager groupDocsManager;


        @Override
	public String execute(Map parameters, String body, RenderContext renderContext) throws MacroException {
            Map<String, Object> contextMap = MacroUtils.defaultVelocityContext();
            GroupDocsRequestSigner signer = new GroupDocsRequestSigner(groupDocsManager.getPrivateKey(AuthenticatedUserThreadLocal.getUser()));
            String referer = "?&referer=Confluence-Viewer/1.3";
            String guid = ((String) parameters.get("path")) + referer;
            String url = guid.startsWith("http") ? guid : (groupDocsManager.getSettings().getViewerUrl()+ guid);
            String newUrl = signer.signUrl(url);
            Object width = parameters.get("width");
            Object height = parameters.get("height");
            contextMap.put("url", newUrl);
            contextMap.put("width", width == null ? DEFAULT_WIDTH : width);
            contextMap.put("height", height == null ? DEFAULT_HEIGHT : height);
            return VelocityUtils.getRenderedTemplate(TEMPLATE_PATH, contextMap);
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