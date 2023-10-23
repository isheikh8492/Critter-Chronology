package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.OwnerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPetById(long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet with id: " + petId
        + " not found"));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByOwner(long ownerId) {
        return petRepository.findPetsByOwnerId(ownerId);
    }

    public Pet getPetByPetId(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet of id: "
                        + petId + " not found"));
    }

    public Pet updateOwnerId(Long petId, Long ownerId) {
        Pet p = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Employee with id: "+ petId + " not found"));
        Owner o = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Employee with id: "+ ownerId + " not found"));
        p.setOwner(o);
        return petRepository.save(p);
    }
}
