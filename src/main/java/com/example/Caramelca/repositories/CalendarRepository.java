package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    Iterable<Calendar> findByDate(Date date);


}
