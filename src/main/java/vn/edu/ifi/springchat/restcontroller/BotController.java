package vn.edu.ifi.springchat.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.ws.ResponseWrapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import org.tartarus.snowball.ext.FrenchStemmer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;


import vn.edu.ifi.springchat.entity.Person;
import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;
import vn.edu.ifi.springchat.entity.Satisfy;
import vn.edu.ifi.springchat.googleurlsearch.Crawler;
import vn.edu.ifi.springchat.keywordextraction.Rake;
import vn.edu.ifi.springchat.repository.PersonRepository;
import vn.edu.ifi.springchat.repository.QuestionRepository;
import vn.edu.ifi.springchat.repository.ResponseRepository;
import vn.edu.ifi.springchat.repository.SatisfyRepository;
import vn.edu.ifi.springchat.stringsimilarity.JaroWinkler;

@RestController
@RequestMapping(value="/api/bot")
public class BotController {
	
	public static Long rememberQuestion = null ; 
	
	public static String rememberString = null ; 
	
	public Long user_id ; 
	
	@Autowired 
	PersonRepository RepoPerson ;
	
	@Autowired
	QuestionRepository RepoQuestion ; 
	
	@Autowired 
	ResponseRepository RepoResponse ; 
	
	@Autowired
	SatisfyRepository RepoSatisfy; 
	
	
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public @ResponseBody Person user( @RequestBody final  Person person) {    
		Person pers = RepoPerson.save(person);
	      this.user_id  = pers.getPerson_id();
	      return person;
	  }
	
	
	@RequestMapping(value="/user",  method=RequestMethod.POST)
	public @ResponseBody boolean registerUser(@RequestBody String userInfo) {
		System.out.println(userInfo); 
		return true;
	}
	
	
	@RequestMapping(value="/answerokko", method=RequestMethod.POST)
	public boolean anwswerOk(@RequestBody String okvalue) {
		System.out.println(BotController.rememberQuestion+ " question : "+BotController.rememberString); 
		String value[] = okvalue.split("="); 
		System.out.println(value[1].trim().equalsIgnoreCase("1")+": OK : "+okvalue.equalsIgnoreCase("ok")+" : KO :  "+okvalue.equalsIgnoreCase("ko"));
		String msg = " ";
		if(value[1].trim().equalsIgnoreCase("1")) {
			Question question = RepoQuestion.findById(BotController.rememberQuestion).get();
			Person person = RepoPerson.findById(this.user_id).get();
			Satisfy satisfy = new Satisfy(1,person, question ); 
			msg =RepoSatisfy.save(satisfy).getPerson().getPerson_name(); 
			//Satisfy satisfy = new Satisfy("serge","useremail@info.com",  1, question); 
			//String msg =RepoSatisfy.save(satisfy).getSatisfy_name(); 
			System.out.println(msg);
		}else if(value[1].trim().equalsIgnoreCase("0")) {
			Question question = new Question(BotController.rememberString); 
			question = RepoQuestion.save(question); 
			Person person = RepoPerson.findById(this.user_id).get();
			Satisfy satisfy = new Satisfy(0,person, question ); 
			msg =RepoSatisfy.save(satisfy).getPerson().getPerson_name(); 
			System.out.println(msg);
		}
		BotController.rememberQuestion = null; 
		BotController.rememberString= null ; 
		return true ; 
	}
	
	@RequestMapping(value="/answeryesno", method=RequestMethod.POST)
	public String answerYes(@RequestBody String answer) { 
		String response = "";
		String[] resp = answer.split("="); 
		System.out.println(resp[1]+" rember string  "+BotController.rememberString+" rember id "+ BotController.rememberQuestion);
		if(BotController.rememberQuestion == null || BotController.rememberString == null) {
			response = "Veillez reposer votre question"; 
		}else {
			if(resp[1].equalsIgnoreCase("yes")) {
				Question question = RepoQuestion.findById(BotController.rememberQuestion).get(); 
				response = RepoResponse.findById(question.getResponse().getResponse_id()).get().getResponse();
			}else {
				Crawler google = new Crawler(); 
				response  = google.getDataFromGoogle(BotController.rememberString); 
			}
		}
		
		return response ; 
	}
	
	
	
	@RequestMapping(value="/question", method=RequestMethod.POST)
	public @ResponseBody  Response answer(@RequestBody Question question){
		Response response = null ;
		List<Question> listQuestionBase = new ArrayList<Question>();
		JaroWinkler testSimilarity  =  new JaroWinkler(); 
		String quest = "";
		quest = question.getQuestion().trim();
		String query = quest ;
		quest = quest.replaceAll("[^a-zA-Z0-9]", " ");
		BotController.rememberString = quest ; 
		System.out.println("Remember question :"+BotController.rememberString ); 
		listQuestionBase = RepoQuestion.findAllQuestionWhereAnswer_idNoEmpty(); 
		Rake R = new Rake("fr"); 
		String[] questionPhrase = {quest}; 
		// have unique words from user
		String[] listWordQuestion =  new HashSet<String>(Arrays.asList(R.getKeywords(questionPhrase))).toArray(new String[0]);
		System.out.println("-------------------------------");
		for(int i = 0 ; i < listWordQuestion.length; i++) {
			System.out.println(listWordQuestion[i]);
		}
		System.out.println("-------------------------------");
		Long responseIdUniqueQuestion = null  ; 
		List<Long> listMax = new ArrayList<Long>(); 
		int maxCount = 0; 
		for(int i= 0; i < listQuestionBase.size() ; i++) {
			String stringQuestion = listQuestionBase.get(i).getQuestion(); 
			String[] baseQuestion = {stringQuestion.replaceAll("[^a-zA-Z0-9]", " ").trim()}; 
			// have unique words from database question
			String[] listWord = new HashSet<String>(Arrays.asList(R.getKeywords(baseQuestion))).toArray(new String[0]);
			int countKey = 0; 
			for(int j =0 ; j<listWordQuestion.length ; j++ ) {
				int k = 0; 
				while(k<listWord.length && !listWordQuestion[j].equals(listWord[k])) {
					k = k+1; 
				}
				if(k<listWord.length) {
					System.out.println("le mot "+listWordQuestion[j]+" existe");
					countKey = countKey + 1; 
				}
			}
			System.out.println(maxCount+"nombre de mot identique : "+countKey);
			if(countKey > 0 && countKey > maxCount) {
				listMax.clear();
				listMax.add(listQuestionBase.get(i).getQuestion_id()); // garde id question
				responseIdUniqueQuestion = listQuestionBase.get(i).getResponse().getResponse_id(); 
				maxCount = countKey ; 
				System.out.println("le max trouve : "+maxCount);
			}else {
				if(countKey > 0 && countKey == maxCount) {
					listMax.add(listQuestionBase.get(i).getQuestion_id()); // garde id question
				}
			}	
		}
		System.out.println("mot en commun "+maxCount);
		if(listMax.size() == 0) {
			Crawler google = new Crawler(); 
			quest  = google.getDataFromGoogle(query); 
			response = new Response("Consultez le site : "+quest+" pour plus d'infomations");
		}else if(listMax.size() == 1) {
			BotController.rememberQuestion = listMax.get(0); 
			String[] baseQuestion = {RepoQuestion.findById(listMax.get(0)).get().getQuestion().replaceAll("[^a-zA-Z0-9]", " ").trim()}; 
			// have unique words from database question
			String[] listWord = new HashSet<String>(Arrays.asList(R.getKeywords(baseQuestion))).toArray(new String[0]);
			
			if(listWord.length == listWordQuestion.length ) {
				response = RepoResponse.findById(responseIdUniqueQuestion).get();
			}else {
				BotController.rememberQuestion = listMax.get(0); 
				System.out.println("remember id  of the question : "+BotController.rememberQuestion);
				response = new Response("Désolé mais voulez vous savoir : "+RepoQuestion.findById(listMax.get(0)).get().getQuestion()); 
			}
		}else{
				System.out.println(" nombre de mot clé similaires : "+listMax.size());
				double maxSimilarity = 0; 
				List<Long> index = new ArrayList<Long>(); 
				double similar ; 
				boolean similar_1 = false ;
				int i = 0; 
				while(i < listMax.size() && similar_1 == false) {
					System.out.println("id de la question numero : "+i+" est : "+listMax.get(i));
					Question dbq = RepoQuestion.findById(listMax.get(i)).get(); 
					String stringQuestion = dbq.getQuestion(); 
					System.out.println(stringQuestion+" et "+quest);
					similar = testSimilarity.similarity(quest, stringQuestion); 
					System.out.println("similar = "+ similar+"id a conserver : "+dbq.getResponse().getResponse_id()); 
					if(similar == 1) {
						index.clear(); 
						index.add(dbq.getResponse().getResponse_id());
						similar_1 =  true; 
						System.out.println("4"); 
					}else if(similar!= 0 & similar > maxSimilarity ) {
						System.out.println("1"); 
						maxSimilarity = similar;
						System.out.println("2"); 
						index.clear();
						System.out.println("3"); 
						//index.add(Long.parseLong(tabQuestion[0])); 
						index.add(dbq.getQuestion_id());
						System.out.println("4"); 
					}
					i = i + 1; 
				}
				System.out.println(" Fin test similarite ");
				if(similar_1 == true) {
					BotController.rememberQuestion =  index.get(0);
					quest = RepoResponse.findById(index.get(0)).get().getResponse(); 
					response = new Response(quest);
				}else if(index.size() > 0 && similar_1 == false ) {
					BotController.rememberQuestion =  index.get(0); 
					System.out.println(" nombre de similaire identique : "+index.size());
					quest = "Désolé mais voulez vous savoir : "+RepoQuestion.findById(index.get(0)).get().getQuestion(); 
					response = new Response(quest);
				}else {
						quest="Plus d'information sur www.ifi.edu.vn";
						response = new Response(quest);
					}
				}
		return response;
	}

	
	@RequestMapping(value="/api/bot/allquestions", method=RequestMethod.GET)
	public String questions() {
	
		return "les question"; 
	}
	
}