package com.rongdu.common.file;

/**
 * 文件类型
 * @author sunda
 * @version 2017-06-27
 */
public enum FileType {
	
	IMG("IMG"),
	VIDEO("VIDEO"),
	DOC("DOC");
	
	private String type = null;
	
	FileType(){		
	}
	
	FileType(String type){
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
