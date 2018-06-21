package vn.edu.ifi.springchat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;
import vn.edu.ifi.springchat.repository.QuestionRepository;
import vn.edu.ifi.springchat.repository.ResponseRepository;

@Controller
public class Chat {

	@Autowired
	QuestionRepository RepoQuestion ; 
	
	@Autowired 
	ResponseRepository RepoResponse ; 
	
	
	@RequestMapping(value={ "/", "/chat"})
	public String welcome() {
		
		return "index"; 
	}
	
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin"; 
	}
	
	@RequestMapping("/super")
	public String chatAdmin() {
		return "super"; 
	}
	
	@RequestMapping("/responses")
	public  ModelAndView allResponses() {
		System.out.println("test response"); 
		List<Response> allresponses = RepoResponse.findAll(); 
		ModelAndView model = new ModelAndView("responses");
		model.addObject("responses", allresponses); 
		System.out.println("test response "); 
		return model ; 
	}
	
	@RequestMapping(value="/saveresponse", method=RequestMethod.POST )
	public ModelAndView saveResponse(@RequestBody MultiValueMap<String, String> formData, ModelMap model ) {
		//Long id = Long.parseLong(formData.get("response_id").get(0)); 
		String resp = formData.get("response").get(0); 
		Response answer = new Response(resp); 
		System.out.println("reponse "+answer.getResponse());
		RepoResponse.save(answer); 
		System.out.println("reponse +");
		return new ModelAndView("forward:/responses", model);
	}
	
	@RequestMapping(value="/savequestion", method=RequestMethod.POST)
	public ModelAndView saveQuestion(@RequestBody MultiValueMap<String, String> formData, ModelMap model) { 
		System.out.println("question ici"+formData.get("question").get(0));
		String quest = formData.get("question").get(0); 
		Long resp_id = Long.parseLong(formData.get("question").get(1)); 
		
//		Question question = new Question(quest);
		Question question = new Question(); 
		Response  response = RepoResponse.getOne(resp_id);
		System.out.println("reponse ici"+response.getResponse_id());
		question.setResponse(response);
		question.setQuestion(quest);
		Question savequest = RepoQuestion.save(question); 
		
		RepoQuestion.save(question); 
		//RepoQuestion.updateQuestionSetAnswer_idForQuestion_id(resp_id, savequest.getQuestion_id());
		
		return new ModelAndView("forward:/questions", model);
	}
	
	@RequestMapping("/questions")
	public ModelAndView allQuestions() {
		List<Question> allquestions = RepoQuestion.findAll();
		List<Response> allresponses = RepoResponse.findAll(); 
		ModelAndView model = new ModelAndView("questions"); 
		model.addObject("questions", allquestions); 
		model.addObject("responses", allresponses); 
		return model ; 
	}

	
}
