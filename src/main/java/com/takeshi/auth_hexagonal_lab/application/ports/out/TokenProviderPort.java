package com.takeshi.auth_hexagonal_lab.application.ports.out;

import com.takeshi.auth_hexagonal_lab.domain.model.User;

public interface TokenProviderPort {
     String generateToken(User user);
}
