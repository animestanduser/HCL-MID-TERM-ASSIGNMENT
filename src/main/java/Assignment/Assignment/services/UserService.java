package Assignment.Assignment.services;

import Assignment.Assignment.entities.User;
import Assignment.Assignment.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public String userVerify(String email, String password, HttpSession httpSession) {

        List<User> userList = userRepo.findByEmail(email);
        if (userList.size() ==0){
            return "user not registered";
        }
        if (userList.get(0).getPassword().equals(password)){
            httpSession.setAttribute("userLoggedIn", "yes");
            httpSession.setAttribute("userName", userList.get(0).getName());
            httpSession.setAttribute("userMobile", userList.get(0).getMobile());
            httpSession.setAttribute("userEmail", userList.get(0).getEmail());
            httpSession.setAttribute("id", userList.get(0).getId());
            httpSession.setAttribute("userPassword", userList.get(0).getPassword());
            return "success";
        } else {
            return "invalid password";
        }
    }

    public List<User> getfile() {
        return userRepo.findAll();
    }
}