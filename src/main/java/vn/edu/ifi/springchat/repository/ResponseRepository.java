package vn.edu.ifi.springchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.ifi.springchat.entity.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
	
//	@Modifying(clearAutomatically = true)
//	@Query("SELECT answer response FROM answer WHERE response.response_id = :resp_id")
//	Response findByResponse_id(@Param("resp_id") Long resp_id);
}
