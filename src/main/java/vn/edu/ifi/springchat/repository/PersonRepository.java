package vn.edu.ifi.springchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.ifi.springchat.entity.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {

}
