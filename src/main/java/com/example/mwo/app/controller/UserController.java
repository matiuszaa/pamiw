package com.example.mwo.app.controller;

import java.util.List;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/showFormForRegistry")
    public List<String> showFormForRegistry(Model theModel) {
        User theUser = new User();
        List<String> cities = userService.showAvailableCities();
        theModel.addAttribute("user", theUser);
        theModel.addAttribute("cities", cities);
        return cities;
    }

    @RequestMapping(value ="/register",method = { RequestMethod.GET})
    public String displayRegisterPage(Model model) {
        model.addAttribute("person", new RegisterUserDto());
        return "register.html";
    }

    @RequestMapping(value ="/createUser",method = { RequestMethod.POST})
    public String createUser(@Valid @ModelAttribute("person") RegisterUserDto person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register.html";
        }
        boolean isSaved = userService.createNewPerson(person);
        if(isSaved){
            return "redirect:/login?register=true";
        }else {
            return "register.html";
        }
    }

    @GetMapping("/showFormForLogin")
    public String showFormForLogin(Model theModel) {

        User theUser = new User();
        theModel.addAttribute("user-login", theUser);

        return "user-login";
    }

    @RequestMapping(value ="/login",method = { RequestMethod.GET, RequestMethod.POST })
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,
                                   @RequestParam(value = "register", required = false) String register,
                                   Model model) {
        String errorMessge = null;
        if(error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        else if(register != null) {
            errorMessge = "You registration successful. Login with registered credentials !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login.html";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @PostMapping("/mail")
    public String sendMail(){
        userService.sendEmail();
        return "redirect:/menu";
    }

}
