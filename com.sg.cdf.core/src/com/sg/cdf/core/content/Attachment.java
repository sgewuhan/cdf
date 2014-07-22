package com.sg.cdf.core.content;

import java.util.ArrayList;
import java.util.List;

public class Attachment {

	private List<String> files;
	
	public Attachment() {
	}
	
	public void addFile(String filePath) {
		if (files == null) {
			files = new ArrayList<String>();
		}
		files.add(filePath);
	}


	public List<String> getFiles() {
		return files;
	}
	
	

}
