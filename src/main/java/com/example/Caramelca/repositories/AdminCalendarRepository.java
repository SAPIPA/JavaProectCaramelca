package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCalendarRepository extends CrudRepository<Calendar, Long> {

}
