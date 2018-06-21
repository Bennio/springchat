package vn.edu.ifi.springchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.ifi.springchat.entity.Satisfy;

@Repository
public interface SatisfyRepository extends JpaRepository<Satisfy, Long>{

}
