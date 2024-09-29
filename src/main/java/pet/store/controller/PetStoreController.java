package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController 
{
	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData savePetStore (
			@RequestBody PetStoreData petStoreData)
	{
		log.info("Creating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/pet_store/{id}")
	public PetStoreData updatePetStore(
			@PathVariable Integer id, 
			@RequestBody PetStoreData petStoreData)
	{
		petStoreData.setPetStoreId(id);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addPetStoreEmployee(
			@PathVariable Integer petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee)
	{
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}

	@PostMapping("/pet_store/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addPetStoreCustomer(
			@PathVariable Integer petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer)
	{
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@GetMapping("/pet_store")
	public List<PetStoreData> retrieveAllPetStores()
	{
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/pet_store/{petStoreId}")
	public PetStoreData retrievePetStore(
			@PathVariable Integer petStoreId)
	{
		return petStoreService.retrievePetStore(petStoreId);
	}
	
	@DeleteMapping("/pet_store/{petStoreId}")
	public Map<String, String> deletePetStoreById(
			@PathVariable Integer petStoreId)
	{
		log.info("Deleting pet store {}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId);
		return Map.of("message", "Deletion successful");
	}
}
