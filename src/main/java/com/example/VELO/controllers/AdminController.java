package com.example.VELO.controllers;

import com.example.VELO.models.*;
import com.example.VELO.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactsRepository contactsRepository;

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
    PunktRepository punktRepository;

    //Пользователи

    @GetMapping("/users")
    public String UserList(@ModelAttribute("user") User user, Model model){

        Iterable<User> users = new ArrayList<User>();

        users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin-users";
    }

    @GetMapping("/users/details/{id}")
    public String UserDetails(@PathVariable(value = "id") long id, Model model){
        Optional<User> user = userRepository.findById(id);
        Contacts contacts1 = user.get().getContacts();
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("contacts1", contacts1);
        return "admin-users-details";
    }


    @GetMapping("/users/edit/{id}")
    public String EditUserDetails(@PathVariable(value = "id") long id, Model model){
        User user = userRepository.findById(id).get();
        Contacts contacts = user.getContacts();
        model.addAttribute("user", user);
        model.addAttribute("contacts", contacts);
        return "admin-users-edit";
    }

    @PostMapping("/editcontacts/{id}")
    public String EditContacts(@PathVariable("id")long id,
                           @RequestParam String phone,
                           @RequestParam String email,
                           Model model)
    {
        Contacts contacts = contactsRepository.findById(id).orElseThrow();
        contacts.setPhone(phone);
        contacts.setEmail(email);
        contactsRepository.save(contacts);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/edit/{id}")
    public String EditUser(@PathVariable("id")long id,
                               @RequestParam String familia,
                               @RequestParam String name, @RequestParam String otchestvo, @RequestParam String username,
                               @RequestParam String phone,
                               @RequestParam String email,
                               Model model)
    {
        User user = userRepository.findById(id).orElseThrow();
        user.setFamilia(familia);
        user.setName(name);
        user.setOtchestvo(otchestvo);
        user.setUsername(username);
        Contacts contacts = user.getContacts();
        contacts.setPhone(phone);
        contacts.setEmail(email);
        userRepository.save(user);
        contactsRepository.save(contacts);
        return "redirect:/admin/users";
    }


    @PostMapping("/users/details/{id}/remove")
    public  String  UserDelete (@PathVariable("id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/admin/users";
    }

    //Товары

    @GetMapping("/addtovar")
    public String AddTovar(@ModelAttribute("tovar") Tovar tovar, Model model){

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);

        Iterable<Color> colors = colorRepository.findAll();
        model.addAttribute("colors",colors);

        Iterable<Sklad> sklads = skladRepository.findAll();
        model.addAttribute("sklads",sklads);

        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers",suppliers);

        return "admin-tovar-add";
    }

    @PostMapping("/addtovar")
    public String AddTovar(@ModelAttribute("tovar") @Valid Tovar tovar, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            return "admin-tovar-add";
        }

        Iterable<Category> categories = categoryRepository.findAll();
        tovar.setData_p(tovar.getData_p());
        tovar.setCategory(tovar.getCategory());
        tovar.setColor(tovar.getColor());
        tovar.setSklad(tovar.getSklad());
        tovar.setSupplier(tovar.getSupplier());
        tovarRepository.save(tovar);
        return "redirect:/main";
    }

    @PostMapping("/tovar/{id}/remove")
    public  String  TovarDelete (@PathVariable("id") long id, Model model){
        Tovar tovar = tovarRepository.findById(id).orElseThrow();
        tovarRepository.delete(tovar);
        return "redirect:/main";
    }

    //Категории

    @GetMapping("/category")
    public String CategoryList(@ModelAttribute("category") Category category, Model model){

        Iterable<Category> categories = new ArrayList<Category>();

        categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin-categories";
    }

    @GetMapping("/addcategory")
    public String AddCategory(@ModelAttribute("category") Category category, Model model){

        return "admin-category-add";
    }

    @PostMapping("/addcategory")
    public String AddCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            return "admin-category-add";
        }

        category.setCategory_name(category.getCategory_name());
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/edit/{id}")
    public String EditCategory(@PathVariable(value = "id") long id, Model model){
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        return "admin-category-edit";
    }

    @PostMapping("/category/edit/{id}")
    public String EditCategory(@PathVariable("id")long id,
                               @RequestParam String category_name,
                               Model model)
    {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setCategory_name(category_name);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/category/{id}/remove")
    public  String  CategoryDelete (@PathVariable("id") long id, Model model){
        Category category = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(category);
        return "redirect:/admin/category";
    }

    //Цвет

    @GetMapping("/color")
    public String ColorList(@ModelAttribute("color") Color color, Model model){

        Iterable<Color> colors = new ArrayList<Color>();

        colors = colorRepository.findAll();
        model.addAttribute("colors", colors);
        return "admin-colors";
    }

    @GetMapping("/addcolor")
    public String AddColor(@ModelAttribute("color") Color color, Model model){

        return "admin-color-add";
    }

    @PostMapping("/addcolor")
    public String AddColor(@ModelAttribute("color") @Valid Color color, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin-color-add";
        }

        color.setColor_name(color.getColor_name());
        colorRepository.save(color);
        return "redirect:/admin/color";
    }

    @GetMapping("/color/edit/{id}")
    public String EditColor(@PathVariable(value = "id") long id, Model model){
        Color color = colorRepository.findById(id).get();
        model.addAttribute("color", color);
        return "admin-color-edit";
    }

    @PostMapping("/color/edit/{id}")
    public String EditColor(@PathVariable("id")long id,
                               @RequestParam String color_name,
                               Model model)
    {
        Color color = colorRepository.findById(id).orElseThrow();
        color.setColor_name(color_name);
        colorRepository.save(color);
        return "redirect:/admin/color";
    }

    @PostMapping("/color/{id}/remove")
    public  String  ColorDelete (@PathVariable("id") long id, Model model){
        Color color = colorRepository.findById(id).orElseThrow();
        colorRepository.delete(color);
        return "redirect:/admin/color";
    }

    //Склад

    @GetMapping("/sklad")
    public String SkaldList(@ModelAttribute("sklad") Sklad sklad, Model model){

        Iterable<Sklad> sklads = new ArrayList<Sklad>();

        sklads = skladRepository.findAll();
        model.addAttribute("sklads", sklads);
        return "admin-sklads";
    }

    @GetMapping("/addsklad")
    public String AddSkald(@ModelAttribute("sklad") Sklad sklad, Model model){

        return "admin-sklad-add";
    }

    @PostMapping("/addsklad")
    public String AddSkald(@ModelAttribute("sklad") @Valid Sklad sklad, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin-sklad-add";
        }

        sklad.setAdres(sklad.getAdres());
        skladRepository.save(sklad);
        return "redirect:/admin/sklad";
    }

    @GetMapping("/sklad/edit/{id}")
    public String EditSkald(@PathVariable(value = "id") long id, Model model){
        Sklad sklad = skladRepository.findById(id).get();
        model.addAttribute("sklad", sklad);
        return "admin-sklad-edit";
    }

    @PostMapping("/sklad/edit/{id}")
    public String EditSkald(@PathVariable("id")long id,
                            @RequestParam String adres,
                            Model model)
    {
        Sklad sklad = skladRepository.findById(id).orElseThrow();
        sklad.setAdres(adres);
        skladRepository.save(sklad);
        return "redirect:/admin/sklad";
    }

    @PostMapping("/sklad/{id}/remove")
    public  String  SkaldDelete (@PathVariable("id") long id, Model model){
        Sklad sklad = skladRepository.findById(id).orElseThrow();
        skladRepository.delete(sklad);
        return "redirect:/admin/sklad";
    }

    //Поставщики

    @GetMapping("/supplier")
    public String SupplierList(@ModelAttribute("suppliers") Supplier supplier, Model model){

        Iterable<Supplier> suppliers = new ArrayList<Supplier>();

        suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        return "admin-suppliers";
    }

    @GetMapping("/addsupplier")
    public String AddSupplier(@ModelAttribute("supplier") Supplier supplier, Model model){

        return "admin-supplier-add";
    }

    @PostMapping("/addsupplier")
    public String AddSupplier(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin-supplier-add";
        }

        supplier.setName_org(supplier.getName_org());
        supplier.setPhone(supplier.getPhone());
        supplierRepository.save(supplier);
        return "redirect:/admin/supplier";
    }

    @GetMapping("/supplier/edit/{id}")
    public String EditSupplier(@PathVariable(value = "id") long id, Model model){
        Supplier supplier = supplierRepository.findById(id).get();
        model.addAttribute("supplier", supplier);
        return "admin-supplier-edit";
    }

    @PostMapping("/supplier/edit/{id}")
    public String EditSupplier(@PathVariable("id")long id,
                            @RequestParam String name_org,
                               @RequestParam String phone,
                            Model model)
    {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplier.setName_org(name_org);
        supplier.setPhone(phone);
        supplierRepository.save(supplier);
        return "redirect:/admin/supplier";
    }

    @PostMapping("/supplier/{id}/remove")
    public  String  SupplierDelete (@PathVariable("id") long id, Model model){
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        supplierRepository.delete(supplier);
        return "redirect:/admin/supplier";
    }

    //Пункты выдачи

    @GetMapping("/punkt")
    public String PunktList(@ModelAttribute("punkts") Punkt punkt, Model model){

        Iterable<Punkt> punkts = new ArrayList<Punkt>();

        punkts = punktRepository.findAll();
        model.addAttribute("punkts", punkts);
        return "admin-punkt";
    }

    @GetMapping("/addpunkt")
    public String AddPunkt(@ModelAttribute("punkt") Punkt punkt, Model model){

        return "admin-punkt-add";
    }

    @PostMapping("/addpunkt")
    public String AddPunkt(@ModelAttribute("punkt") @Valid Punkt punkt, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin-punkt-add";
        }

        punkt.setAdres(punkt.getAdres());
        punkt.setWork_time(punkt.getWork_time());
        punkt.setName(punkt.getName());
        punktRepository.save(punkt);
        return "redirect:/admin/punkt";
    }

    @GetMapping("/punkt/edit/{id}")
    public String EditPunkt(@PathVariable(value = "id") long id, Model model){
        Punkt punkt = punktRepository.findById(id).get();
        model.addAttribute("punkt", punkt);
        return "admin-punkt-edit";
    }

    @PostMapping("/punkt/edit/{id}")
    public String EditPunkt(@PathVariable("id")long id,
                               @RequestParam String adres,
                               @RequestParam String work_time,
                                @RequestParam String name,
                               Model model)
    {
        Punkt punkt = punktRepository.findById(id).orElseThrow();
        punkt.setAdres(adres);
        punkt.setWork_time(work_time);
        punkt.setName(name);
        punktRepository.save(punkt);
        return "redirect:/admin/punkt";
    }

    @PostMapping("/punkt/{id}/remove")
    public  String  PunktDelete (@PathVariable("id") long id, Model model){
        Punkt punkt = punktRepository.findById(id).orElseThrow();
        punktRepository.delete(punkt);
        return "redirect:/admin/punkt";
    }
}