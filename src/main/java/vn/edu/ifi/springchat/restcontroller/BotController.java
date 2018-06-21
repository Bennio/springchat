package vn.edu.ifi.springchat.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;
import vn.edu.ifi.springchat.repository.QuestionRepository;
import vn.edu.ifi.springchat.repository.ResponseRepository;
import vn.edu.ifi.springchat.stringsimilarity.JaroWinkler;

@RestController
@RequestMapping(value="/api/bot")
public class BotController {
	
	
	@Autowired
	QuestionRepository RepoQuestion ; 
	
	@Autowired 
	ResponseRepository RepoResponse ; 
	
	
	@RequestMapping(value="/question",method=RequestMethod.POST)
	public String answer(@RequestBody  String question){
		List listQuestion = new ArrayList<Question>();
		JaroWinkler testSimilarity  =  new JaroWinkler(); 
		String quest = "";
		String [] splitQuestion = question.split("&");
		String questionEqual = splitQuestion[1]; 
		quest = quest+questionEqual.split("=")[1];
		quest = quest.replace("+", " "); 
		quest = quest.trim(); 
		System.out.println("ici la question: "+quest);
		listQuestion = RepoQuestion.findByQuestionIgnoreCaseContaining(quest); 
		System.out.println("ici nombre de question similaires: "+listQuestion.size());
		if(listQuestion.size() >0) {
			double maxSimilarity = 0; 
			List<Long> index = new ArrayList<Long>(); 
			double similar ; 
			for(int i = 0 ; i< listQuestion.size(); i++) {
				String stringQuestion = listQuestion.get(i).toString(); 
				String[] tabQuestion = stringQuestion.split("#"); 
				System.out.println(tabQuestion[1].trim()+" et "+quest);
				similar = testSimilarity.similarity(quest, tabQuestion[1].trim()); 
				System.out.println("similar = "+ similar); 
				if(similar!= 0 & similar > maxSimilarity ) {
					System.out.println("1"); 
					maxSimilarity = similar;
					System.out.println("2"); 
					index.clear();
					System.out.println("3"); 
					index.add(Long.parseLong(tabQuestion[0])); 
					System.out.println("4"); 
				}
				if(similar!= 0 & similar == maxSimilarity ) {
					index.add(Long.parseLong(tabQuestion[0])); 
				}
			
			}
			if(index.size() > 0) {
				Collections.sort(index);
				String stringResponse = RepoResponse.findById(index.get(index.size()-1)).toString();
				String[] tabResponse = stringResponse.split("#"); 
				quest = tabResponse[1]; 
			}else {
				quest="Plus d'information sur ifi.edu.vn";
			}
			
		}else {
			quest="Plus d'information sur ifi.edu.vn"; 
		}
		return quest;
	 
	}
	
	@RequestMapping(value="/api/bot/allquestions", method=RequestMethod.GET)
	public String questions() {
	
		return "les question"; 
	}
	
}