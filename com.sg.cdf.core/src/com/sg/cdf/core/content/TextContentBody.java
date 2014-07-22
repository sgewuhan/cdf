package com.sg.cdf.core.content;

public class TextContentBody extends ContentBody {

	private String text;

	public TextContentBody(String text) {
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
