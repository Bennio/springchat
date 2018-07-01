package vn.edu.ifi.springchat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.edu.ifi.springchat.entity.Question;
import vn.edu.ifi.springchat.entity.Satisfy;

@Repository
public interface SatisfyRepository extends JpaRepository<Satisfy, Long>{

	@Query("select satisfy  from Satisfy satisfy where satisfy.satisfy = 0")
	List<Satisfy> findAllSatisfyWhereSatisfyZero(); 
}
