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
	
	@Column(name="satisfy_name")
	private String satisfy_name ; 
	
	@Column(name="satisfy_email")
	private String satisfy_email ; 
	
	@Column(name="satisfy_phone")
	private String satisfy_phone ; 
	
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
	
	public Satisfy(String nom, String mailPhone, int satisfy, Question question) {
		this.setSatisfy_name(nom);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.setSatisfy_date(timestamp.getTime());
		
		if(this.isValidEmail(mailPhone)) {
			this.setSatisfy_email(mailPhone);
		}else if(!mailPhone.isEmpty()) {
			this.setSatisfy_phone(mailPhone);
		}
		
		this.setSatisfy(satisfy);
	
		this.setQuestion(question);
	}
	
	

	public int getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(int satisfy) {
		this.satisfy = satisfy;
	}

	public Satisfy(String nom, String mail, String phone, int satisfy, Question question) {
		this.setSatisfy_name(nom);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		this.setSatisfy_date(timestamp.getTime());
		
		if(this.isValidEmail(mail)) {
			this.setSatisfy_email(mail);
		}
		if(!phone.isEmpty()) {
			this.setSatisfy_phone(phone);
		}
		
		this.setSatisfy(satisfy);
	
		this.setQuestion(question);
	}
	
	public Long getSatisfy_id() {
		return satisfy_id;
	}


	public void setSatisfy_id(Long satisfy_id) {
		this.satisfy_id = satisfy_id;
	}


	public String getSatisfy_name() {
		return satisfy_name;
	}


	public void setSatisfy_name(String satisfy_name) {
		this.satisfy_name = satisfy_name;
	}


	public String getSatisfy_email() {
		return satisfy_email;
	}


	public void setSatisfy_email(String satisfy_email) {
		this.satisfy_email = satisfy_email;
	}


	public String getSatisfy_phone() {
		return satisfy_phone;
	}


	public void setSatisfy_phone(String satisfy_phone) {
		this.satisfy_phone = satisfy_phone;
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


	
	@ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
	 

}
