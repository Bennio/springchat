package vn.edu.ifi.springchat.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Response {
	@Id
	@Column(name="answer_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long response_id ;
	
	@Column(name="answer")
	private String response ; 
	
	@Column(name="answer_status")
	private int response_status ; 
	
	public Response() {
		
	}
	
	public Response(String resp) {
		this.setResponse(resp);
		this.setResponse_status(1);
		
	}
	
	public Response(Long ans_id) {
		this.setResponse_id(ans_id);
		this.setResponse_status(1);
		
	}
	
	public Response(Long id, String resp, int status) {
		this.setResponse_id(id);
		this.setResponse(resp);
		this.setResponse_status(status);
		
	}
	
	public Long getResponse_id() {
		return response_id;
	}

	public void setResponse_id(Long response_id) {
		this.response_id = response_id;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getResponse_status() {
		return response_status;
	}

	public void setResponse_status(int response_status) {
		this.response_status = response_status;
	}

	 @OneToMany
	 @JoinColumn(name= "response_id")
	 //@OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
	private List<Question> question = new ArrayList<Question>(); 
	
	/*@Override
	public String toString() {
		return response_id+"#"+response+"#"+response_status ; 
	}*/

}
