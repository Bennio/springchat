package vn.edu.ifi.springchat.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
import org.tartarus.snowball.ext.FrenchStemmer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;
import vn.edu.ifi.springchat.googleurlsearch.Crawler;
import vn.edu.ifi.springchat.keywordextraction.Rake;
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
		List listQuestionBase = new ArrayList<Question>();
		JaroWinkler testSimilarity  =  new JaroWinkler(); 
		// Debut extraction de la question posee 
		String quest = "";
		String [] splitQuestion = question.split("&");
		String questionEqual = splitQuestion[1]; 
		quest = quest+questionEqual.split("=")[1];
		quest = quest.replace("+", " "); 
		String query = quest ; 
		// retrait des caractere speciaux 
		quest = quest.replaceAll("[^a-zA-Z0-9]", " ");
		quest = quest.trim(); 
		// Fin extaction
		System.out.println("ici la question posee est : "+quest);
		listQuestionBase = RepoQuestion.findAll(); 
		Rake R = new Rake("fr"); 
		String[] questionPhrase = {quest}; 
		// have unique words from user
		String[] listWordQuestion =  new HashSet<String>(Arrays.asList(R.getKeywords(questionPhrase))).toArray(new String[0]);
		System.out.println("-------------------------------");
		for(int i = 0 ; i < listWordQuestion.length; i++) {
			System.out.println(listWordQuestion[i]);
		}
		System.out.println("-------------------------------");
		
		List<Long> listMax = new ArrayList<Long>(); 
		int maxCount = 0; 
		for(int i= 0; i < listQuestionBase.size() ; i++) {
			String stringQuestion = listQuestionBase.get(i).toString(); 
			String[] tabQuestion = stringQuestion.split("#"); 
			String[] baseQuestion = {tabQuestion[1].replaceAll("[^a-zA-Z0-9]", " ").trim()}; 
			// have unique words 
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
				listMax.add(Long.parseLong(tabQuestion[3])); // garde id response 
				maxCount = countKey ; 
				System.out.println("le max trouve : "+maxCount);
			}else {
				if(countKey > 0 && countKey == maxCount) {
					listMax.add(Long.parseLong(tabQuestion[3])); // garde id response 
				}
			}	
		}
		System.out.println("mot en commun "+maxCount);
		if(listMax.size() == 0) {
//			FrenchStemmer fStemmer = new  FrenchStemmer(); 
//			for(int i=0 ; i< listWordQuestion.length; i++) {
//				String query = listWordQuestion.toString(); 
				Crawler google = new Crawler(); 
				//google.getDataFromGoogle(query); 
//				fStemmer.setCurrent(listWordQuestion[i]);
//				if(fStemmer.stem()) {
//					System.out.println(" stemming : "+fStemmer.getCurrent());
//				}
//			}
//			String[] backQuestion = {"Que voulez vous dire par : ", };
			quest  = google.getDataFromGoogle(query); 
//			= backQuestion[0]+quest ; 
		}else if(listMax.size() == 1) {
			String stringResponse = RepoResponse.findById(listMax.get(0)).toString();
			String[] tabResponse = stringResponse.split("#"); 
			quest = tabResponse[1]; 
		}else {
			double maxSimilarity = 0; 
			List<Long> index = new ArrayList<Long>(); 
			double similar ; 
			for(int i = 0 ; i < listMax.size(); i++) {
				String stringQuestion = RepoResponse.findById(listMax.get(i)).toString(); 
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
					//index.add(Long.parseLong(tabQuestion[0])); 
					index.add(listMax.get(i));
					System.out.println("4"); 
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
		}
		return quest;
	}
		
//		if(listQuestionBase.size() >0) {
//			double maxSimilarity = 0; 
//			List<Long> index = new ArrayList<Long>(); 
//			double similar ; 
//			for(int i = 0 ; i< listQuestionBase.size(); i++) {
//				String stringQuestion = listQuestionBase.get(i).toString(); 
//				String[] tabQuestion = stringQuestion.split("#"); 
//				System.out.println(tabQuestion[1].trim()+" et "+quest);
//				similar = testSimilarity.similarity(quest, tabQuestion[1].trim()); 
//				System.out.println("similar = "+ similar); 
//				if(similar!= 0 & similar > maxSimilarity ) {
//					System.out.println("1"); 
//					maxSimilarity = similar;
//					System.out.println("2"); 
//					index.clear();
//					System.out.println("3"); 
//					index.add(Long.parseLong(tabQuestion[0])); 
//					System.out.println("4"); 
//				}
//				if(similar!= 0 & similar == maxSimilarity ) {
//					index.add(Long.parseLong(tabQuestion[0])); 
//				}
//			
//			}
//			if(index.size() > 0) {
//				Collections.sort(index);
//				String stringResponse = RepoResponse.findById(index.get(index.size()-1)).toString();
//				String[] tabResponse = stringResponse.split("#"); 
//				quest = tabResponse[1]; 
//			}else {
//				quest="Plus d'information sur ifi.edu.vn";
//			}
//			
//		}else {
//			quest="Plus d'information sur ifi.edu.vn"; 
//		}
//		return quest;
//	 
//	}
	
	@RequestMapping(value="/api/bot/allquestions", method=RequestMethod.GET)
	public String questions() {
	
		return "les question"; 
	}
	
}