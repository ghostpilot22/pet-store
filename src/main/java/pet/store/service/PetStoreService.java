package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
@Slf4j
public class PetStoreService 
{
	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired 
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;

	@Transactional (readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) 
	{
		log.info("Saving pet store...");
		Integer petStoreId =  petStoreData.getPetStoreId();
		log.info("Pet store id get: {}", petStoreId);
		PetStore petStore = findOrCreatePetStore(petStoreId);
		log.info("Found or created pet store...");
		copyPetStoreFields(petStore, petStoreData);
		log.info("Pet store fields copied...");
		return new PetStoreData(petStoreDao.save(petStore));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) 
	{
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	private PetStore findOrCreatePetStore(Integer petStoreId) 
	{
		PetStore petStore;
		if(Objects.isNull(petStoreId))
		{
			log.info("Pet store id null, creating new pet store...");
			petStore = new PetStore();
		}
		else
		{
			log.info("Pet store id not null, finding pet store by id...");
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}

	private PetStore findPetStoreById(Integer petStoreId) 
	{
		return petStoreDao.findById(petStoreId)
				.orElseThrow();
	}
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee (Integer petStoreId, 
			PetStoreEmployee petStoreEmployee)
	{
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, 
				petStoreEmployee.getEmployeeId());
		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		return new PetStoreEmployee(employeeDao.save(employee));
	}
	
	private Employee findEmployeeById(Integer petStoreId,
			Integer employeeId)
	{
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow();
		if(employee.getPetStore().getPetStoreId() != petStoreId)
			throw new IllegalArgumentException();
		return employee;
	}
	
	private Employee findOrCreateEmployee(Integer petStoreId,
			Integer employeeId) 
	{
		Employee employee;
		if(Objects.isNull(employeeId))
		{
			log.info("Employee id null, creating new employee...");
			employee = new Employee();
		}
		else
		{
			log.info("Employee id not null, finding employee by id...");
			employee = findEmployeeById(petStoreId, employeeId);
		}
		return employee;
	}
	
	private void copyEmployeeFields(Employee employee, 
			PetStoreEmployee petStoreEmployee)
	{
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer (Integer petStoreId, 
			PetStoreCustomer petStoreCustomer)
	{
		PetStore petStore = findPetStoreById(petStoreId);
		Customer customer = findOrCreateCustomer(petStoreId, 
				petStoreCustomer.getCustomerId());
		copyCustomerFields(customer, petStoreCustomer);
		//customer.setPetStore(petStore);
		petStore.getCustomers().add(customer);
		return new PetStoreCustomer(customerDao.save(customer));
	}
	
	private Customer findCustomerById(Integer petStoreId,
			Integer customerId)
	{
		Customer customer = customerDao.findById(customerId)
				.orElseThrow();
		Set<PetStore> stores = customer.getPetStores();
		boolean foundId = false;
		for (PetStore store : stores)
		{
			if(store.getPetStoreId() == petStoreId)
				foundId = true;
		}
		if(!foundId)	throw new IllegalArgumentException();
		return customer;
	}
	
	private Customer findOrCreateCustomer(Integer petStoreId,
			Integer customerId) 
	{
		Customer customer;
		if(Objects.isNull(customerId))
		{
			log.info("Customer id null, creating new employee...");
			customer = new Customer();
		}
		else
		{
			log.info("Customer id not null, finding customer by id...");
			customer = findCustomerById(petStoreId, customerId);
		}
		return customer;
	}
	
	private void copyCustomerFields(Customer customer, 
			PetStoreCustomer petStoreCustomer)
	{
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() 
	{
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for (PetStore petStore : petStores)
		{
			PetStoreData psd = new PetStoreData(petStore);
			if(psd.getCustomers() != null) psd.getCustomers().clear();
			if(psd.getEmployees() != null) psd.getEmployees().clear();
			result.add(psd);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public PetStoreData retrievePetStore(Integer petStoreId) 
	{
		return new PetStoreData(findPetStoreById(petStoreId));
	}

	public void deletePetStoreById(Integer petStoreId) 
	{
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}
