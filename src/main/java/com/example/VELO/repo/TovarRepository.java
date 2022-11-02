package com.example.VELO.repo;

import com.example.VELO.models.Tovar;
import com.example.VELO.models.Chek;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TovarRepository extends CrudRepository<Tovar, Long> {

    List<Tovar> findTovarByCheks(Chek chek);
}