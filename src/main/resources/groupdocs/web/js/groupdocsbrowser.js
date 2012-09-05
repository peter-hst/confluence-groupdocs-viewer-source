(function($) {

	var contextPath = AJS.Data.get('context-path');

	AJS.MacroBrowser.setMacroJsOverride("groupdocs-doc", {
		beforeParamsSet: function(params, inserting) {
//			if(!this.picker.dialog){
//				this.picker.setDialog(AJS.MacroBrowser.dialog);
//				this.picker.loadFileTree();
//			} reusing the same dialog instance might conflict with other macros that add new page

			var picker = new GroupdocsBrowser({
				contextPath: contextPath
			});
			picker.setDialog(AJS.MacroBrowser.dialog);
			picker.loadFileTree();

			var selectButton = AJS.$('<button>Select document</button>');

			AJS.$(".button-panel-button", this.element).show();
			AJS.$("#macro-param-div-path").append(selectButton);
			AJS.$('#macro-param-path').hide();

//			var thiz = this;
			selectButton.click(function(e) {
//				thiz.picker.selectedDocuments = [];
//				thiz.picker.input = AJS.$('#macro-param-div-path');
//				thiz.picker.dialog.gotoPage(FILE_BROWSER_PAGE);
				picker.selectedDocuments = [];
				picker.input = AJS.$('#macro-param-div-path');
				picker.dialog.gotoPage(FILE_BROWSER_PAGE);

				e.preventDefault();
			});

			return params;
		}
	});

	AJS.MacroBrowser.setMacroJsOverride("groupdocs-folder", {
		beforeParamsSet: function(params, inserting) {
			var picker = new GroupdocsBrowser({
				isFolder: true,
				contextPath: contextPath,
				onFolderSelected: function(folder){
					alert(folder);
				}
			});
			picker.setDialog(AJS.MacroBrowser.dialog);
			picker.loadFileTree();

			var selectButton = AJS.$('<button>Select folder</button>');

			AJS.$(".button-panel-button", this.element).show();
			AJS.$("#macro-param-div-path").append(selectButton);
			AJS.$('#macro-param-path').hide();

			selectButton.click(function(e) {
				picker.selectedDocuments = [];
				picker.input = AJS.$('#macro-param-div-path');
				picker.dialog.gotoPage(FILE_BROWSER_PAGE);

				e.preventDefault();
			});

			return params;
		}
	});

})(AJS.$);

