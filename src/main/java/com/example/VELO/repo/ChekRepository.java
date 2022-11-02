package com.example.VELO.repo;

import com.example.VELO.models.Chek;
import com.example.VELO.models.User;
import com.example.VELO.models.Tovar;
import org.springframework.data.repository.CrudRepository;

public interface ChekRepository extends CrudRepository<Chek, Long> {

    Chek findByUser(User user);

    Chek findByTovars(Tovar tovar);
}