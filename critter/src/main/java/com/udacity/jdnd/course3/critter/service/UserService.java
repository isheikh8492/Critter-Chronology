package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.OwnerRepository;
import com.udacity.jdnd.course3.critter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " not found"));
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("Owner with id: " + id + " not found"));
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner saveCustomer(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Employee saveEmployee(Employee givenEmployee) {
        return employeeRepository.save(givenEmployee);
    }

    public void updateDaysAvailable(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee e = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Employee with id: "+ employeeId + " not found"));
        e.setDaysAvailable(daysAvailable);
        employeeRepository.save(e);

    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> availableEmployees = new ArrayList<>();
        List<Employee> employees = this.employeeRepository.findEmployeesByDaysAvailable(date.getDayOfWeek());

        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(skills)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
