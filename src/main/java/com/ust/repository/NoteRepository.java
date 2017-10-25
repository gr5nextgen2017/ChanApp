package com.ust.repository;
import com.ust.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NoteRepository extends JpaRepository<Note, Long> {

}