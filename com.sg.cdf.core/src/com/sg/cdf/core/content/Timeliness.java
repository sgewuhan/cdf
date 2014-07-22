package com.sg.cdf.core.content;

import java.util.Date;

public class Timeliness {
	
	private Date start;
	
	private Date end;

	public Timeliness(Date start,Date end){
		setEnd(end);
		setStart(start);
	}
	
	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}
	
	

}
