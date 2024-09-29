package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data @NoArgsConstructor
public class PetStoreData 
{
	private Integer petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<PetStoreCustomer> customers;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<PetStoreEmployee> employees;
	
	public PetStoreData (PetStore petStore)
	{
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
		customers = new HashSet<PetStoreCustomer>();
		employees = new HashSet<PetStoreEmployee>();

		if(petStore.getCustomers() != null) 
		{
			for(Customer customer : petStore.getCustomers())
				customers.add(new PetStoreCustomer(customer));
		}
		
		if(petStore.getEmployees() != null)
		{
			for(Employee employee : petStore.getEmployees())
				employees.add(new PetStoreEmployee(employee));
		}
	}
	
	@Data @NoArgsConstructor
	public static class PetStoreCustomer
	{
		public PetStoreCustomer(Customer customer) 
		{
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
		}
		
		private Integer customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
	}
	
	@Data @NoArgsConstructor
	public static class PetStoreEmployee
	{
		public PetStoreEmployee(Employee employee) 
		{
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
		
		private Integer employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
	}
}
