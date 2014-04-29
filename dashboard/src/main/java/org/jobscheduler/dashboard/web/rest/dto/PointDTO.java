package org.jobscheduler.dashboard.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic=true)
@JsonFormat(shape=JsonFormat.Shape.ARRAY)
public class PointDTO {

	Long x;
	Long y;
	
	public PointDTO() {
	}
	
	public PointDTO(Long x, Long y) {
		this.x = x;
		this.y = y;
	}
	
	public Long getX() {
		return x;
	}
	public void setX(Long x) {
		this.x = x;
	}
	public Long getY() {
		return y;
	}
	public void setY(Long y) {
		this.y = y;
	}
	
	
}
