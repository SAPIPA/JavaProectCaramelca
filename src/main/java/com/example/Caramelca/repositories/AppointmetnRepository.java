package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmetnRepository extends CrudRepository<Appointment, Long> {
}
