package com.example.VELO.controllers;


import com.example.VELO.models.*;
import com.example.VELO.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TovarRepository tovarRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    SkladRepository skladRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ChekRepository chekRepository;

    @Autowired
    PunktRepository punktRepository;

    @Autowired
    CommentRepository commentRepository;

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

        return user;
    }

    @GetMapping("/tovar/details/{id}")
    public String TovarDetails(@PathVariable(value = "id") long id, Model model){
        Optional<Tovar> tovar = tovarRepository.findById(id);
        Category category = categoryRepository.findById(id).get();
        Color color1 = colorRepository.findById(id).get();
        Sklad sklad = skladRepository.findById(id).get();
        Supplier supplier = supplierRepository.findById(id).get();

        Comment comments = commentRepository.findById(id).get();
        ArrayList<Tovar> res = new ArrayList<>();
        tovar.ifPresent(res::add);
        model.addAttribute("tovar", res);
        model.addAttribute("category", category);
        model.addAttribute("color1", color1);
        model.addAttribute("sklad", sklad);
        model.addAttribute("supplier", supplier);

        model.addAttribute("comments", comments);
        return "user-tovar-details";
    }

    @GetMapping("/korzina")
    public String Korzina(@ModelAttribute("tovars") Tovar tovar, Model model){

        Chek chek = chekRepository.findByUser(getAuthUser());
        List<Tovar> tovars1 = tovarRepository.findTovarByCheks(chek);

        model.addAttribute("tovars1", tovars1);
        return "user-korzina";
    }

    @PostMapping("/tovar/korzina/{id}/add")
    public String Tovar(@ModelAttribute("chek")  @Valid Chek chek, BindingResult bindingResult,
                        @PathVariable(value = "id") long id,
                        Model model){

        Tovar tovar = tovarRepository.findById(id).get();
        Chek chekFromDB = chekRepository.findByUser(getAuthUser());
        if (chekFromDB == null) {
            Chek chek1 = new Chek(new Date(), getAuthUser());
            chekRepository.save(chek1);
        }

        Chek chek2 = chekRepository.findByUser(getAuthUser());
        Tovar tovar2 = tovarRepository.findById(id).get();
        //tovar2.getCheks().add(chek2);
        chek2.getTovars().add(tovar2);
        //tovarRepository.save(tovar2);
        chekRepository.save(chek2);

        return "redirect:/main";
    }

    @PostMapping("/tovar/korzina/{id}/remove")
    public String DeleteTovarFromKorzina(@ModelAttribute("chek")  @Valid Chek chek, BindingResult bindingResult,
                        @PathVariable(value = "id") long id,
                        Model model){

        Tovar tovar = tovarRepository.findById(id).get();
        Chek chek2 = chekRepository.findByUser(getAuthUser());
        Tovar tovar2 = tovarRepository.findById(id).orElseThrow();
        //tovar2.getCheks().add(chek2);
        chek2.getTovars().add(tovar2);
        //tovarRepository.save(tovar2);
        chekRepository.delete(chek2);
        Chek chekFromDB = chekRepository.findByUser(getAuthUser());
        model.addAttribute("chek", chek);
        if (chekFromDB == null) {
            Chek chek1 = new Chek(new Date(),getAuthUser());
            chekRepository.save(chek1);
        }

        return "redirect:/main";
    }

    @PostMapping("/tovar/korzina/{id}/pokupka")
    public String Pokupka(@ModelAttribute("chek")  @Valid Chek chek, BindingResult bindingResult,
                                         @PathVariable(value = "id") long id,
                                         Model model){

        Tovar tovar = tovarRepository.findById(id).get();
        Chek chek2 = chekRepository.findByUser(getAuthUser());
        Tovar tovar2 = tovarRepository.findById(id).orElseThrow();
        //tovar2.getCheks().add(chek2);
        chek2.getTovars().add(tovar2);
        //tovarRepository.save(tovar2);
        chekRepository.delete(chek2);
        Chek chekFromDB = chekRepository.findByUser(getAuthUser());
        model.addAttribute("chek", chek);
        if (chekFromDB == null) {
            Chek chek1 = new Chek(new Date(),getAuthUser());
            chekRepository.save(chek1);
        }

        return "redirect:/main";
    }

    //Комментарии

    @GetMapping("/addcomment/{id}")
    public String AddComment(@ModelAttribute("comment") Comment comment, @PathVariable(value = "id") long id,
                             Model model){
        Tovar tovar = tovarRepository.findById(id).get();
        model.addAttribute("tovar", tovar);

        return "user-comment-add";
    }

    @PostMapping("/addcomment")
    public String AddComment(@ModelAttribute("comment") @Valid Comment comment, BindingResult bindingResult,
                             @RequestParam long id, Model model) {

        if (bindingResult.hasErrors()) {
            return "user-comment-add";
        }

        Tovar tovar = tovarRepository.findById(id).get();

        comment.setTovar(tovar);
        comment.setUser(getAuthUser());
        comment.setRating(comment.getRating());
        comment.setFull_text(comment.getFull_text());
        commentRepository.save(comment);
        return "redirect:/main";
    }
}
