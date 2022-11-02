package com.example.VELO.repo;

import com.example.VELO.models.Contacts;
import org.springframework.data.repository.CrudRepository;

public interface ContactsRepository extends CrudRepository<Contacts, Long> {

    Contacts findByPhone(String phone);
}