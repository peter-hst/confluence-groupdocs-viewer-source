package com.groupdocs.plugins.confluence.macro.groupdocs;

import java.util.Map;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.content.render.xhtml.DefaultConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.renderer.v2.macro.MacroException;

public class XhtmlViewGroupdocsDocumentMacro extends ViewGroupdocsDocumentMacro implements Macro {

	@Override
	public String execute(Map<String, String> parameters, String body,
			ConversionContext context) throws MacroExecutionException {

		DefaultConversionContext defaultConversionContext = (DefaultConversionContext) context;
		try {
			return execute(parameters, body, defaultConversionContext.getRenderContext());
		} catch (MacroException e) {
			throw new MacroExecutionException(e);
		}
	}

	@Override
	public BodyType getBodyType() {
		return Macro.BodyType.NONE;
	}

	@Override
	public OutputType getOutputType() {
		return Macro.OutputType.BLOCK;
	}

}