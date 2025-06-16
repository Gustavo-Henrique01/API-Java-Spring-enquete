package enquetes.sistemaenquetes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import enquetes.sistemaenquetes.enums.PollStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "polls")
public class Poll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id  ; 
	@Column(nullable = false)
	private String  question ; 
	@Column(columnDefinition = "TEXT")
	private String description ; 
	@Column(nullable = false)

	private LocalDateTime startDate ;
	@Column(nullable = false)
	private LocalDateTime endDate ; 
	
	@Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private PollStatus status;	
	
	 @CreationTimestamp
	    @Column(nullable = false, updatable = false)
	    private LocalDateTime createdAt;

	    @UpdateTimestamp
	    @Column(nullable = false)
	    private LocalDateTime updatedAt;
	    
	    @ManyToOne
	    @JoinColumn(name = "created_by_user_id", nullable = false) 
	    private User createdBy;
	    
	    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Option> options = new ArrayList<>();
	    
	    
	    

		public Poll() {
			
		}

		public Poll( String question, String description, LocalDateTime startDate, LocalDateTime endDate,
				PollStatus status, User createdBy, List<Option> options) {
			super();
			
			this.question = question;
			this.description = description;
			this.startDate = startDate;
			this.endDate = endDate;
			this.status = status;
			this.createdBy = createdBy;
			if (options != null) { 
	            this.options.addAll(options);
	            options.forEach(option -> option.setPoll(this));
	        }
		}
		 public Poll(String question, String description, LocalDateTime startDate, LocalDateTime endDate,
		            PollStatus status, User createdBy) {
		        this.question = question;
		        this.description = description;
		        this.startDate = startDate;
		        this.endDate = endDate;
		        this.status = status;
		        this.createdBy = createdBy;
		    }
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDateTime getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDateTime startDate) {
			this.startDate = startDate;
		}

		public LocalDateTime getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDateTime endDate) {
			this.endDate = endDate;
		}

		public PollStatus getStatus() {
			return status;
		}

		public void setStatus(PollStatus status) {
			this.status = status;
		}

		public User getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(User createdBy) {
			this.createdBy = createdBy;
		}

		public List<Option> getOptions() {
			return options;
		}

		public void setOptions(List<Option> options) {
			this.options = options;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

	    
		
	    public void addOption(Option option) {
	        if (!this.options.contains(option)) { // Evita duplicatas
	            this.options.add(option);
	            option.setPoll(this); // Garante a relação bidirecional
	        }
	    }

	    public void removeOption(Option option) {
	        if (this.options.contains(option)) {
	            this.options.remove(option);
	            option.setPoll(null); 
	        }
	    }

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Poll other = (Poll) obj;
			return Objects.equals(id, other.id);
		}

		@Override
		public String toString() {
			return "Poll [id=" + id + ", question=" + question + ", description=" + description + ", startDate="
					+ startDate + ", endDate=" + endDate + ", status=" + status + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", options=" + options + "]";
		}
	    
	
}
