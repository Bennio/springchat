package vn.edu.ifi.springchat.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person implements Serializable {
	
	public Person() {
		
	}
 	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name="person_id")
	private Long person_id;
	
	@Column(name="person_name")
	private String person_name;
	
	@Column(name="person_email")
	private String person_email ;
	
	@Column(name="person_phone")
	private String person_phone ;

	public Long getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Long person_id) {
		this.person_id = person_id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getPerson_email() {
		return person_email;
	}

	public void setPerson_email(String person_email) {
		this.person_email = person_email;
	}

	public String getPerson_phone() {
		return person_phone;
	}

	public void setPerson_phone(String person_phone) {
		this.person_phone = person_phone;
	}

	// one to many
	@OneToMany
	@JoinColumn(name="person_id")
	private List<Satisfy> satisfy = new ArrayList<Satisfy>(); 
	
}
