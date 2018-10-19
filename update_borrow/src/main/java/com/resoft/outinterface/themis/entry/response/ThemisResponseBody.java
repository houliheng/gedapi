package com.resoft.outinterface.themis.entry.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
public class ThemisResponseBody {
	@XmlElement(name="SCORE")
	private List<Score> score;
	@XmlElement(name="REVIEW")
	private List<Review> review;
	
	@Override
	public String toString() {
		return "Body [score=" + score.toString() + ", review=" + review.toString() + "]";
	}
	public List<Score> getScore() {
		return score;
	}
	public void setScore(List<Score> score) {
		this.score = score;
	}
	public List<Review> getReview() {
		return review;
	}
	public void setReview(List<Review> review) {
		this.review = review;
	}
	public ThemisResponseBody() {
		super();
	}
}
