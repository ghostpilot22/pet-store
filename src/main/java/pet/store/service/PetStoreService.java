package pet.store.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
@Slf4j
public class PetStoreService 
{
	@Autowired
	private PetStoreDao petStoreDao;

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
}
