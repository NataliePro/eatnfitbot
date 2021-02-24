package ru.natapro.eatnfitbot.telegram.service;

import java.util.List;
import ru.natapro.eatnfitbot.telegram.dao.UserDAO;
import ru.natapro.eatnfitbot.telegram.dao.UserDAOImpl;
import ru.natapro.eatnfitbot.telegram.domain.Result;
import ru.natapro.eatnfitbot.telegram.domain.User;

public class UserService {
    private UserDAO usersDao = new UserDAOImpl();

    public UserService() {
    }

    public User findUser(long id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void saveUserResult(Result result) {
        usersDao.saveResult(result);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public Result findResultById(int id) {
        return usersDao.findResultById(id);
    }
}
