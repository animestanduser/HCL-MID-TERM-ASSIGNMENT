package Assignment.Assignment.controllers;
import Assignment.Assignment.services.UserService;
import Assignment.Assignment.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/view")
    public String view(  Model model ){
        List<User> users =  userService.getfile();
        model.addAttribute("users" , users);
        return "view";
    }
}
