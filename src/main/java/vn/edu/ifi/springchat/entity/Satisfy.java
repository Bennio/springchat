package vn.edu.ifi.springchat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="satisfy")
public class Satisfy {
	@Id
	@Column(name="satisfy_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long satisfy_id ; 
	
	@Column(name="satisfy_name")
	private String satisfy_name ; 
	
	@Column(name="satisfy_email")
	private String satisfy_email ; 
	
	@Column(name="satisfy_phone")
	private String satisfy_phone ; 
	
	@Column(name="satisfy")
	private boolean satisfy ; 
	
	@Column(name="satisfy_date")
	private Long satisfy_date ; 
		
	
	@ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
	 

}
