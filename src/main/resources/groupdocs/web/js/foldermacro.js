jQuery(function () {
	loadFileTree();
});

function loadFileTree(){
	var parent = AJS.$("div[name='groupdocsBrowser']");
	var container = AJS.$("div[name='groupdocsBrowserInner']", parent);
	var selectedFolder = AJS.$("input[name='path']", parent).val();
	var selectedWidth = AJS.$("input[name='width']", parent).val();
	var selectedHeight = AJS.$("input[name='height']", parent).val();
	var opts = {
		root: selectedFolder,
		script: contextPath + '/plugins/servlet/groupdocs/list',
		onTreeShow: function(){
//			parent.removeClass("fileTree-loading");
		},
		onServerSuccess: function(){
			jQuery("a.iframe", container).each(function() {
				var self = jQuery(this);

				if(self.hasClass("image-doc")) {
					self.gfancybox({
						titleShow: false
					});
				} else {
					self.gfancybox({
						type: 'iframe',
						width: parseInt(selectedWidth),
						height: parseInt(selectedHeight),
						titleShow: false
					});
				}
			});
		},
		onServerError: function(response) {
			var message = "Unable to retrieve data due to an error.";
//			parent.removeClass("fileTree-loading");
			parent.append(AJS.$("<div class='aui-message warning'>" + message + "</div>"));
		}
	};

	var onFileSelected = function(file) {
		file = file.substr(0, file.length - 1);
		console.log(file + " selected in browser");
	};
//	container.fileTree(opts, onFileSelected);
	container.fileTree(opts);

}