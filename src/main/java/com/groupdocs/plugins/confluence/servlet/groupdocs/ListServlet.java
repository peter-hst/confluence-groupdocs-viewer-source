package com.groupdocs.plugins.confluence.servlet.groupdocs;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.groupdocs.plugins.confluence.component.groupdocs.FileItem;
import com.groupdocs.plugins.confluence.component.groupdocs.GroupDocsManager;
import com.groupdocs.plugins.confluence.util.AuthException;
import com.wordnik.swagger.runtime.common.APIInvoker;
import com.wordnik.swagger.runtime.common.GroupDocsUrlSigningSecurityHandler;

public class ListServlet extends HttpServlet {

	private GroupDocsManager groupDocsManager;

	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {

		String path = request.getParameter("dir");
		if (path == null || path.equals("/")){
			path = "";
		} else {
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		String clientId = groupDocsManager.getClientId(AuthenticatedUserThreadLocal.getUser());
		String privateKey = groupDocsManager.getPrivateKey(AuthenticatedUserThreadLocal.getUser());

		if (path != null) {
			if (clientId != null && !clientId.isEmpty() && privateKey != null && !privateKey.isEmpty() ) {
				try {
					List<FileItem> items = groupDocsManager.getFolderContents(path, clientId, privateKey);
					if(items == null){
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						return;
					}
					response.setContentType("text/html; charset=UTF-8");
//					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();

					out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
					for (FileItem item : items) {
						if(item.getType() != null) {
							out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" +
										path + item.getPath() + "/\">" + item.getTitle() + "</a></li>");
						}
					}
					for (FileItem item : items) {
						if(item.getType() == null) {
							StringBuilder url = new StringBuilder(groupDocsManager.getSettings().getViewerUrl() + item.getId());
							new GroupDocsUrlSigningSecurityHandler(privateKey).populateSecurityInfo(url, null);
							out.print("<li class=\"file ext_" + item.getExtension() + "\"><a class='iframe' href='" + url.toString() + "' rel=\"" +
										item.getId() + "\">" + item.getTitle() + "</a></li>");
						}
					}
					out.print("</ul>");

				} catch (AuthException e) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		} else
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

	public void setGroupDocsManager(GroupDocsManager groupDocsManager) {
		this.groupDocsManager = groupDocsManager;
	}

}