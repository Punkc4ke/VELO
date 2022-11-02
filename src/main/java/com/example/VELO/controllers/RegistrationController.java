package com.example.VELO.controllers;

import com.example.VELO.models.Contacts;
import com.example.VELO.models.Role;
import com.example.VELO.models.User;
import com.example.VELO.repo.ContactsRepository;
import com.example.VELO.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactsRepository contactsRepository;


    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user, @ModelAttribute("contacts") Contacts contacts){
        return "registration";
    }

    @PostMapping("/addcontacts")
    public String addContact(@ModelAttribute("contacts") @Valid Contacts contacts, BindingResult bindingResult, Model model){
        Contacts contactFromDb = contactsRepository.findByPhone(contacts.getPhone());
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        if(contactFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким телефоном уже зарегистрирован");
            return "registration";
        }
        contactsRepository.save(contacts);
        return "redirect:/login";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, @ModelAttribute("contacts") @Valid Contacts contacts, BindingResult bindingResult, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(bindingResult.hasErrors()) {
            return "registration";
        }
        contactsRepository.save(contacts);
        if(userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован");
            return "registration";
        }
        user.setContacts(contacts);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
