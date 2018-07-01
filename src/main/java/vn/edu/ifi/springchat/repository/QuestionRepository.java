package vn.edu.ifi.springchat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Repository;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Response;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	List<Question> findByQuestionIgnoreCaseContaining(String questionPattern); 
	
	@Query("select question  from Question question where question.response is  not null")
	List<Question> findAllQuestionWhereAnswer_idNoEmpty();  
	

}