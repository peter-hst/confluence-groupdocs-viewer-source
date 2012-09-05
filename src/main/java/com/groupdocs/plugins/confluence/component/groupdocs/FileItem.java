package com.groupdocs.plugins.confluence.component.groupdocs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.jcip.annotations.Immutable;

@Immutable
public class FileItem {
	private String id;	
	private String title;	
	private String description;
	private String type;
	private String created;
	private String updated;
	private String updatedRaw;
	private String size;
	private long bytes;
	private String fileType;
	private boolean isEditable;
	private String path;
	private String mimeType;
	private String key;
	private String extension;

	public FileItem() {
	}

	public FileItem(String id, String title, String description, String type) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.type = type;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated() {
		return this.created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return this.updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public boolean getIsEditable() {
		return this.isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public long getBytes() {
		return this.bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	public String getUpdatedRaw() {
		return this.updatedRaw;
	}

	public void setUpdatedRaw(String updatedRaw) {
		this.updatedRaw = updatedRaw;
	}

	public long getUpdatedEpoch() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss Z");
		Date date = dateFormat.parse(this.updatedRaw);
		return date.getTime();
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}