package pet.store.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Customer 
{
	@Id 
	@GeneratedValue
	private Integer customerId;
	
	@Column
	private String customerFirstName;
	
	@Column
	private String customerLastName;
	
	@Column
	private String customerEmail;
	
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<PetStore> petStores; 
}
