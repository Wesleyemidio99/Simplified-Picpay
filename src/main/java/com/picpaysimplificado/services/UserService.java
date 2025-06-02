package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDto;
import com.picpaysimplificado.exception.ModifiedExceptions;
import com.picpaysimplificado.mapper.UserMapper;
import com.picpaysimplificado.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void validateTransactions(User sender, BigDecimal amount) throws java.lang.Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Usuário do tipo logista não pode realizar transações.");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente para realizar a transação.");
        }
    }

    public User findById(Long id) throws java.lang.Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User createUser(UserDto user) throws java.lang.Exception {
        return userRepository.save(userMapper.toEntity(user));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
