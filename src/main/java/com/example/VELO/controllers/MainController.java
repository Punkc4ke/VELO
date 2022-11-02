package com.example.VELO.controllers;

import com.example.VELO.models.Role;
import com.example.VELO.models.Tovar;
import com.example.VELO.models.User;
import com.example.VELO.repo.TovarRepository;
import com.example.VELO.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    TovarRepository tovarRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/main")
    public String getMainPage(@ModelAttribute("tovar") Tovar tovar,
                              Model model) {

        Iterable<Tovar> tovars = new ArrayList<Tovar>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (user.getRoles().contains(Role.ADMIN))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        tovars = tovarRepository.findAll();

        model.addAttribute("tovars", tovars);

        return "user-main";
    }
}