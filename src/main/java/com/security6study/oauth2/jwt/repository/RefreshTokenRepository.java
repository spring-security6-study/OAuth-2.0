package com.security6study.oauth2.jwt.repository;

import com.security6study.oauth2.jwt.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
