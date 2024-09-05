package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Employee 
{
	@Id 
	@GeneratedValue
	private Integer employeeId;
	
	@Column
	private String employeeFirstName;
	
	@Column
	private String employeeLastName;
	
	@Column
	private String employeePhone;
	
	@Column
	private String employeeJobTitle;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_store_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private PetStore petStore;
}
