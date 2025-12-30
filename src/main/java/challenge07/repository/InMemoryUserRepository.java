package challenge07.repository;

import challenge07.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository{
    private final List<User> users = new ArrayList<>();


    @Override
    public User save(User user) {
     users.add(user);
     return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
