package com.king.nowedge.query;import com.king.nowedge.query.base.LoreBaseQuery;


public class UserSecqaQuery extends LoreBaseQuery {
	
	
	private String user ; 
	
	private String question ;
	
	private String answer ; 

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
}
