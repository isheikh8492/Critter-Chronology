package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Owner owner = null;

        if (petDTO.getOwnerId() != 0) {
            owner = userService.findOwnerById(petDTO.getOwnerId());
        }

        Pet pet = convertPetDTOToPet(petDTO);
        pet.setOwner(owner);
        Pet savedPet = petService.savePet(pet);

        if (owner != null) {
            owner.addPet(savedPet);
        }
        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPetById(petId));
    }

    @PutMapping("/{petId}")
    public PetDTO setPetToOwner(@RequestBody PetDTO petDTO, @PathVariable long petId) {
        return convertPetToPetDTO(petService
                .updateOwnerId(petId, convertPetDTOToPet(petDTO).getOwner().getId()));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petsDTO = new ArrayList<>();
        for (Pet pet : petService.getAllPets()) {
            petsDTO.add(convertPetToPetDTO(pet));
        }
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petsDTO = new ArrayList<>();
        for (Pet pet : petService.getAllPetsByOwner(ownerId)) {
            petsDTO.add(convertPetToPetDTO(pet));
        }
        return petsDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(petDTO, Pet.class);
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }
}
