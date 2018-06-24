package vn.edu.ifi.springchat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.beans.factory.annotation.Autowired;

import vn.edu.ifi.springchat.repository.ResponseRepository;

@Entity
@Table(name="question")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="question_id")
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long question_id; 
	
	
	@Column(name="question")
	private String question ;
	
	@Column(name="question_status")
	private int question_status;
	
//	@Column(name="answer_id")
//	private Long answer_id ; 
	
//	public Long getAnswer_id() {
//		return answer_id;
//	}
//	public void setAnswer_id(Long answer_id) {
//		this.answer_id = answer_id;
//	}
	public Long getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getQuestion_status() {
		return question_status;
	}
	public void setQuestion_status(int question_status) {
		this.question_status = question_status;
	}
	
	public List<Satisfy> getSatisfy() {
		return satisfy;
	}
	public void setSatisfy(List<Satisfy> satisfy) {
		this.satisfy = satisfy;
	}
	
	
	public Question() {
		this.question = question ; 
		this.question_status = 1; 
	}
	
	public Question(String question) {
		this.question = question ; 
		this.question_status = 1; 
	}
	
	
	public Question(String question, Long ans_id) {
		this.question = question ; 
		this.response = new Response(ans_id); 
		this.question_status = 1; 
	}
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return question_id.toString()+"#"+question+"#"+question_status+"#"+response.getResponse_id() ; 
	}
	// one to many
	@OneToMany
	@JoinColumn(name="question_id")
	private List<Satisfy> satisfy = new ArrayList<Satisfy>(); 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="answer_id")
	private Response response;
}
