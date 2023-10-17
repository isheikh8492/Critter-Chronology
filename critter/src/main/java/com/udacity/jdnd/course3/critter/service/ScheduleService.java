package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.OwnerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPetId(Long petId) {
        return scheduleRepository.getScheduleByPets_Id(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(Long employeeId) {
        return scheduleRepository.getScheduleByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleByOwnerId(Long ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if (!optionalOwner.isPresent()) {
            throw new ResourceNotFoundException("No schedule found for the given owner id: "+ ownerId);
        }
        else{
            Owner customer = optionalOwner.get();
            List<Pet> pets = customer.getPets();
            List<Schedule> schedules = new ArrayList<>();

            for (Pet pet : pets) {
                schedules.addAll(scheduleRepository.getScheduleByPets_Id(pet.getId()));
            }
            return schedules;
        }
    }
}
