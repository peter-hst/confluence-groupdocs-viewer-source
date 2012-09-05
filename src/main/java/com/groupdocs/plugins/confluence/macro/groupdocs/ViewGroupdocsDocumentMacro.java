package com.groupdocs.plugins.confluence.macro.groupdocs;

import java.util.Map;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;

public class ViewGroupdocsDocumentMacro extends BaseMacro {
	private static final String ENC = "UTF-8";
	private static final String TEMPLATE_PATH = "groupdocs/templates/macros/groupdocs/viewDoc.vm";
	private static final String AUTH_TEMPLATE_PATH = "groupdocs/templates/macros/groupdocs/authError.vm";
	private static final String DEFAULT_WIDTH = "100%";
	private static final String DEFAULT_HEIGHT = "400px";

	private GroupDocsManager groupDocsManager;


	public String execute(Map parameters, String body, RenderContext renderContext) throws MacroException {
		Map<String, Object> contextMap = MacroUtils.defaultVelocityContext();

		Object width = parameters.get("width");
		Object height = parameters.get("height");
		String guid = (String) parameters.get("path");
		contextMap.put("url", guid.startsWith("http") ? guid : (groupDocsManager.getSettings().getViewerUrl() + guid));
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