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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;
import vn.edu.ifi.springchat.entity.Satisfy;
import vn.edu.ifi.springchat.repository.QuestionRepository;
import vn.edu.ifi.springchat.repository.ResponseRepository;
import vn.edu.ifi.springchat.repository.SatisfyRepository;

@Controller
public class Chat {

	@Autowired
	QuestionRepository RepoQuestion ; 
	
	@Autowired 
	ResponseRepository RepoResponse ; 
	
	@Autowired
	SatisfyRepository RepoSatisfy;
	
	
	@RequestMapping(value={ "/", "/chat"})
	public String welcome() {
		
		return "index"; 
	}
	
	
	@RequestMapping("/admin")
	public String admin(Model model) {
		Long nbsatisfy = RepoSatisfy.count();
		List<Satisfy> listNotSatisfy = RepoSatisfy.findAllSatisfyWhereSatisfyZero(); 
		List<Question> listQuestion = RepoQuestion.findAll(); 	
		System.out.println("nombre satisfaites: "+listQuestion.size());
		model.addAttribute("questions", listQuestion); 
		model.addAttribute("notsatisfies", listNotSatisfy); 
		model.addAttribute("nbsatisfies", nbsatisfy); 
		model.addAttribute("nbnotsatisfies", listNotSatisfy.size()); 
		return "admin"; 
	}
	
	@RequestMapping("/super")
	public String chatAdmin() {
		return "super"; 
	}
	
	@RequestMapping(value="/satisfy", method=RequestMethod.GET )
	public String answerSatisfy(@RequestParam Long id,Long idsatisfy, Model model) {
		Question question = RepoQuestion.findById(id).get();
		model.addAttribute("idquestion",question.getQuestion_id());
		model.addAttribute("idsatisfy",idsatisfy);
		model.addAttribute("question",question.getQuestion());
		return "satisfy";
	}
	
	@RequestMapping(value="/savesatisfy", method=RequestMethod.POST )
	public ModelAndView saveSatisfy(@RequestBody MultiValueMap<String, String> formData, ModelMap model ) {
		System.out.println("reponse "+formData);
		String resp = formData.get("response").get(0); 
		Response answer = new Response(resp); 
		answer = RepoResponse.save(answer);
		Question question = RepoQuestion.findById(Long.parseLong(formData.get("idquestion").get(0))).get();
		question.setResponse(answer);
		RepoQuestion.save(question);
		Satisfy satisfy = RepoSatisfy.findById(Long.parseLong(formData.get("idsatisfy").get(0))).get();
		satisfy.setSatisfy(1);
		RepoSatisfy.save(satisfy);
		return new ModelAndView("forward:/admin", model);
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
	public String allQuestions(Model model) {
		List<Question> allquestions = RepoQuestion.findAllQuestionWhereAnswer_idNoEmpty();
		List<Response> allresponses = RepoResponse.findAll(); 
		System.out.println(" taille response "+allresponses.size());
		model.addAttribute("questions", allquestions); 
		model.addAttribute("responses", allresponses); 
		return "questions" ; 
	}

	
}
