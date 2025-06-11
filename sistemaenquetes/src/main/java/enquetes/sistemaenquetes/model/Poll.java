package enquetes.sistemaenquetes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Poll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id  ; 
	@Column(nullable = false)
	String  question ; 
	@Column(columnDefinition = "TEXT")
	String description ; 
	@Column(nullable = false)

	LocalDateTime startDate ;
	@Column(nullable = false)
	LocalDateTime endDate ; 
	
	String status 
	
	
}
