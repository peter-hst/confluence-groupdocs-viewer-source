package com.groupdocs.plugins.confluence.util;

import java.util.Arrays;
import java.util.List;

public class Utils {

	public static List<String> IMAGE_FILE_TYPES = Arrays.asList(new String[]{"bmp", "gif", "jpeg", "jpg", "bmp", "tif", "png"});

	public static String getFileType(String fileName) {
		String fileType = "file";

		String extension = getFileExtension(fileName);
		if (extension.length() > 1) {
			if (extension.contains("pdf"))
				fileType = "pdf";
			else if (extension.contains("doc"))
				fileType = "document";
			else if (extension.contains("xls"))
				fileType = "spreadsheet";
			else if (extension.contains("ppt"))
				fileType = "presentation";
			else if (extension.contains("zip"))
				fileType = "zip";
			else if (IMAGE_FILE_TYPES.contains(extension)) {
				fileType = "image";
			}
		}

		return fileType;
	}
	
	public static String getFileExtension(String fileName) {
		String[] parts = fileName.split("\\.");
		if (parts.length > 1) {
			String extension = parts[(parts.length - 1)];
			return extension;
		}
		return "";
	}
	
}
