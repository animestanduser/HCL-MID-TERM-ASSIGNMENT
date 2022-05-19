package Assignment.Assignment.controllers;

import Assignment.Assignment.services.UserService;
import Assignment.Assignment.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userLoggedIn") != null &&  httpSession.getAttribute("userLoggedIn").equals("yes")){
            return "redirect:/home";
        }
        model.addAttribute("applicationName", "Assignment");
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("applicationName", "Assignment");
        return "register";
    }

    @PostMapping("/submit")
    public String submit(@Valid User user, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("errorMessage", binding);
            return "register";
        } else {
            userService.saveUser(user);
            return "redirect:/";
        }
    }


    @PostMapping("/loginverify")
    public String loginverify(Model model, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession httpSession) {


        String response = userService.userVerify(email, password, httpSession);

        if (response.equals("success")){
            return "redirect:/home";
        } else {
            model.addAttribute("response", response);

            return "index";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession httpSession) {

        httpSession.invalidate();
        model.addAttribute("applicationName", "Assignment");
        return "redirect:/";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession httpSession) {

        if (httpSession.getAttribute("userLoggedIn") == null ){

            return "redirect:/";
        } else {
            if (!httpSession.getAttribute("userLoggedIn").equals("yes")){
                return "redirect:/";

            }
        }

        model.addAttribute("name", httpSession.getAttribute("userName"));
        model.addAttribute("email",  httpSession.getAttribute("userEmail"));
        model.addAttribute("mobile",  httpSession.getAttribute("userMobile"));
        return "home";
    }

    @PostMapping("/home")
    public String submit(@RequestParam("myFile") MultipartFile file, HttpSession httpSession) throws IOException {

        String mylocation = System.getProperty("user.dir") + "/src/main/resources/static/";

        String filename = file.getOriginalFilename();

        File mySavedFile = new File( mylocation + filename);

        InputStream inputStream = file.getInputStream();

        OutputStream outputStream = new FileOutputStream(mySavedFile);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1){
            outputStream.write(bytes , 0 , read);
        }

        User user1 = new User();
        user1.setId((Long)httpSession.getAttribute("id"));
        user1.setMobile((String)httpSession.getAttribute("userMobile"));
        user1.setName((String)httpSession.getAttribute("userName"));
        user1.setPassword((String)httpSession.getAttribute("userPassword"));
        user1.setEmail((String)httpSession.getAttribute("userEmail"));
        user1.setFileName("http://localhost:9090/" + filename);
        userService.saveUser(user1);



        return "redirect:/";
    }
}
