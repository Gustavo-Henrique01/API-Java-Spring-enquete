package enquetes.sistemaenquetes.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@Column(nullable =false )
	private String text ; 
	
	@ManyToOne
	@JoinColumn(name="poll_id" , nullable =false)
	
	private Poll poll ;
	
	
	 public Option() {
	    }

	public Option( String text, Poll poll) {
		
		this.text = text;
		this.poll = poll;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	
	
	

	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Option other = (Option) o;
	        if (id != null && other.id != null) {
	            return Objects.equals(id, other.id);
	        }

	        return Objects.equals(text, other.text) && Objects.equals(poll, other.poll);
	    }

	    @Override
	    public int hashCode() {
	        if (id == null) {
	            return Objects.hash(text, poll);
	        }
	        return Objects.hash(id);
	    }

	@Override
    public String toString() {
        return "Option [id=" + id + ", text='" + text + '\'' +
               ", pollId=" + (poll != null ? poll.getId() : "null") + "]"; // Evitar ciclos infinitos
    }

}
