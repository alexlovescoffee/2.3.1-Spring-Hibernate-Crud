package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao repository;

    @Override
    @Transactional
    public void addUser(User user) {
        repository.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        repository.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        repository.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return repository.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public void mergeAddress(User user) {
        repository.mergeAddress(user);
    }
}
