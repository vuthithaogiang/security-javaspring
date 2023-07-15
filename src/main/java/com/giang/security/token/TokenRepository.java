package com.giang.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query(value = "select t.* from token as t inner join _user as u on t.user_id = u.id where u.id = ?1 " +
            "and (t.expired = false or t.revoked = false)" ,  nativeQuery = true)
    List<Token> findAllValidTokenByUser(Integer userId);


    Optional<Token> findByToken(String token);
}
