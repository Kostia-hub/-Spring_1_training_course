package lesson.service;

import lesson.domain.Product;
import lesson.domain.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lesson.dao.UserDao;
import lesson.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
        initData();
    }

    private void initData(){
        userDao.saveAll(Arrays.asList(
                new User(null,"User", "123", Role.USER),
                new User(null,"Admin", "123", Role.ADMIN),
                new User(null,"Manager", "123", Role.MANAGER)
        ));

    }

    @Override
    public User getById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User auth(String username, String password) {
        if(username == null || username.isEmpty()){
            System.out.println("You are not authenticated");
            return null;
        }
        User user = userDao.findFirstByName(username);
        if(user == null){
            System.out.println("You are not authenticated");
            return null;
        }
        if(!Objects.equals(password, user.getPassword())){
            System.out.println("You are not authenticated");
            return null;
        }
        System.out.println("You are authenticated");
        return user;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findFirstByName(username);
    }
}