var FILE_BROWSER_PAGE = 2;

(function($) {
	if(AJS.version >= "3.5.0"){
		FILE_BROWSER_PAGE = 3;
	}

	//TODO refactor so that it can be used by both macrobrowser and folder macro
	GroupdocsBrowser = function(options) {
		console.log("GroupdocsBrowser... ");
		this.options = options;
		var selInstr = '';
		if(this.options.isFolder){
			selInstr = "Double click to select a folder.";
		} else {
			selInstr = "Click to select a file.";
		}
		var perms_hint = '<div class="aui-message shadowed"><p class="title"><span class="aui-icon icon-generic"></span>' + selInstr + '</p></div>';
		var BROWSER_TEMPLATE = perms_hint+'<div name="groupdocsBrowser" class=""><div name="groupdocsBrowserInner"></div></div>';

		this.element = AJS.$(BROWSER_TEMPLATE);
		this.dialog = null; // will be available only in beforeParamsSet
	}

	GroupdocsBrowser.prototype.setDialog = function(dialog){
		console.log(dialog);
		if(!dialog.page[FILE_BROWSER_PAGE]){
			dialog.addPage().addHeader("File Picker").addPanel("SinglePanel", "singlePanel");
			dialog.addButton("Back", function(dialog) {
				dialog.gotoPage(1);
			}, "back left box");
			dialog.addButton("Cancel", function(dialog) {
				dialog.hide();
			}, "cancel");
			dialog.gotoPage(1);
		}
		dialog.page[FILE_BROWSER_PAGE].getCurrentPanel().html(this.element);
		this.dialog = dialog;
	}

	GroupdocsBrowser.prototype.loadFileTree = function(){
		console.log("GroupdocsBrowser.prototype.loadFileTree");
		var contextPath = this.options.contextPath;
		var opts = {
			root: '/',
			script: contextPath + '/plugins/servlet/groupdocs/list',
			onTreeShow: function(){
//				AJS.$("div[name='groupdocsBrowser']", this.element).removeClass("fileTree-loading");
			},
			onServerError: function(response) {
				var message = "Unable to retrieve data due to an error.";
				if(response.status == 403) {
					message = '<span class="aui-icon icon-warning"></span>' +
						'<span>Confluence is currently not authorized to retrieve data on your behalf or the token is invalid. ' +
						'To authorize, please click <a href="' + contextPath + '/users/plugins/groupdocs/account.action">here</a>.</span>';
				}

				var container = AJS.$("div[name='groupdocsBrowser']", this.element);
//				container.removeClass("fileTree-loading");
				container.append(AJS.$("<div class='aui-message warning'>" + message + "</div>"));
			}
		};
		var thiz = this;
		if(this.options.isFolder){
			AJS.$("div[name='groupdocsBrowserInner']", this.element).fileTree(opts, undefined, function(file) {
				console.log(file + " selected in browser");
				var targetInput = jQuery("#macro-param-path");
				targetInput.val(file);
				targetInput.change();
				$("#macro-browser-preview-link").click();
				thiz.dialog.gotoPage(1);
			});
		} else {
			AJS.$("div[name='groupdocsBrowserInner']", this.element).fileTree(opts, function(file, href) {
				console.log(href + " selected in browser");
				var targetInput = jQuery("#macro-param-path");
				targetInput.val(href);
				targetInput.change();
				$("#macro-browser-preview-link").click();
				thiz.dialog.gotoPage(1);
			});
		}
	}

})(AJS.$);
