package vn.edu.ifi.springchat.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import vn.edu.ifi.springchat.repository.QuestionRepository;



@Entity
@Table(name="satisfy")
public class Satisfy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="satisfy_id")
	private Long satisfy_id ; 

	@Column(name="satisfy")
	private int satisfy ; 
	
	@Column(name="satisfy_date")
	private Long satisfy_date ; 
	
	public Satisfy() {
		
	}
	
	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
		 Pattern pat = Pattern.compile(emailRegex);
		 if(email.isEmpty()) {
			 return false ; 
		 }else {
			 return pat.matcher(email).matches();
		 }
	}	

	public int getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(int satisfy) {
		this.satisfy = satisfy;
	}

	public Satisfy(int satisfy, Person person, Question question) {
		
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.setSatisfy_date(timestamp.getTime());
		
		this.setSatisfy(satisfy);
		
		this.setPerson(person);
		
		this.setQuestion(question);
	}
	
	public Long getSatisfy_id() {
		return satisfy_id;
	}


	public void setSatisfy_id(Long satisfy_id) {
		this.satisfy_id = satisfy_id;
	}


	public Long getSatisfy_date() {
		return satisfy_date;
	}


	public void setSatisfy_date(Long satisfy_date) {
		this.satisfy_date = satisfy_date;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}



	@ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
	

	@ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;
	 

}
