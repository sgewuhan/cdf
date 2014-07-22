package com.sg.cdf.core.content;

import com.sg.cdf.core.Processor;


public abstract class ContentProvider extends Processor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2237470144638369713L;

	private transient String title;

	private transient String keyWord;

	private transient String summary;

	private transient ContentBody mainBody;

	private transient Timeliness timeliness;

	private transient Attachment attachment;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setMainBody(ContentBody mainBody) {
		this.mainBody = mainBody;
	}

	public void setTimeliness(Timeliness timeliness) {
		this.timeliness = timeliness;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String getTitle() {
		return title;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public String getSummary() {
		return summary;
	}

	public ContentBody getMainBody() {
		return mainBody;
	}

	public Timeliness getTimeliness() {
		return timeliness;
	}

	public Attachment getAttachment() {
		return attachment;
	}

}
