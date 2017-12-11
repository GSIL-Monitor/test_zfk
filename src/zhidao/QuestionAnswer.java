package zhidao;

public class QuestionAnswer {
	String question;
	
	String answer;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question [question=").append(question).append(", answer=").append(answer).append("]");
		return builder.toString();
	}
	
	
}
