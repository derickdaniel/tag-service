package com.microservice.tags.dto;

public class EntityCountDTO {
	Long guideCount;
	Long interviewQACount;
	Long issueCount;
	Long totalCount;

	public EntityCountDTO() {
	}

	public EntityCountDTO(Long guideCount, Long interviewQACount, Long issueCount, Long totalCount) {
		this.guideCount = guideCount;
		this.interviewQACount = interviewQACount;
		this.issueCount = issueCount;
		this.totalCount = totalCount;
	}

	public Long getGuideCount() {
		return guideCount;
	}

	public void setGuideCount(Long guideCount) {
		this.guideCount = guideCount;
	}

	public Long getInterviewQACount() {
		return interviewQACount;
	}

	public void setInterviewQACount(Long interviewQACount) {
		this.interviewQACount = interviewQACount;
	}

	public Long getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(Long issueCount) {
		this.issueCount = issueCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
