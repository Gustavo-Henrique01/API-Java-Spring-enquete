package enquetes.sistemaenquetes.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity


@Table(name = "votes", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"user_id", "poll_id"}) 
	})
public class Vote {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@ManyToOne
	@JoinColumn(name="user_id" , nullable =false )
	private User user ; 
	@ManyToOne
	@JoinColumn(name="option_id"  ,nullable =false )
	private Option option ;
	@Column(nullable = false )
	@CreationTimestamp
	private LocalDateTime votedAT ;
	 @ManyToOne
	    @JoinColumn(name = "poll_id", nullable = false)
	    private Poll poll; 
	
	
	
	public Vote(User user, Option option , Poll poll) {
		super();
		this.user = user;
		this.option = option;
		   this.poll = poll;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Option getOption() {
		return option;
	}
	public void setOption(Option option) {
		this.option = option;
	}
	public LocalDateTime getVotedAT() {
		return votedAT;
	}
	public void setVotedAT(LocalDateTime votedAT) {
		this.votedAT = votedAT;
	}
	
	public Poll getPoll() { 
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
	@Override
	  
	    public int hashCode() {
	        return Objects.hash(id);
	    }
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null || getClass() != obj.getClass())
	            return false;
	        Vote other = (Vote) obj;
	        return Objects.equals(id, other.id);
	    }

	    @Override
	    public String toString() {
	        return "Vote [id=" + id + ", user=" + (user != null ? user.getId() : "null") + 
	               ", option=" + (option != null ? option.getId() : "null") + 
	               ", poll=" + (poll != null ? poll.getId() : "null") + 
	               ", votedAT=" + votedAT + "]";
	    }
	
}
