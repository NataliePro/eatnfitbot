package ru.natapro.eatnfitbot.telegram.dao;

import java.util.List;
import ru.natapro.eatnfitbot.telegram.domain.Result;
import ru.natapro.eatnfitbot.telegram.domain.User;

public interface UserDAO {

    User findById(long id);
    void save(User user);
    void update(User user);
    void delete(User user);
    Result findResultById(int id);
    void saveResult(Result result);
    List<User> findAll();

}
