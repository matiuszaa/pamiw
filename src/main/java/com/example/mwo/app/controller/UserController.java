package com.example.mwo.app.controller;

import java.util.List;

import com.example.mwo.app.dto.LoginUserDto;
import com.example.mwo.app.dto.RegisterUserDto;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
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

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") RegisterUserDto theUser) {
        userService.registerUser(theUser);

        return "redirect:/user/list";
    }

    @GetMapping("/showFormForLogin")
    public String showFormForLogin(Model theModel) {

        User theUser = new User();
        theModel.addAttribute("user-login", theUser);

        return "user-login";
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") LoginUserDto loggingUser) {

        if (userService.validateUser(loggingUser) == null) {
            System.out.println("Not found");
        } else {
            System.out.println("Found");
        }

        return "redirect:/parking/menu";
    }
}
