package com.fiuba.diner.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.fiuba.diner.helper.EncryptionHelper;
import com.fiuba.diner.model.Role;
import com.fiuba.diner.model.User;
import com.fiuba.diner.service.UserService;

@Repository("userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!authentication.isAuthenticated()) {
			logger.info("Autenticando usuario {}", authentication.getPrincipal().toString());
			String username = authentication.getPrincipal().toString();
			String password = authentication.getCredentials().toString();
			User user = this.userService.getByName(username);
			if (user != null && password != null) {

				String hashedPassword = EncryptionHelper.generateHash(password);

				if (hashedPassword.equals(user.getPassword())) {
					if (user.isActive()) {
						logger.info("Usuario autenticado");
						return new UsernamePasswordAuthenticationToken(username, password, this.getAuthorities(user.getRoles()));
					} else {
						logger.info("Usuario inhabilitado.");
						throw new BadCredentialsException("Usuario inhabilitado. Consulte con el Administrador del Sistema.");
					}
				} else {
					logger.info("Usuario o contrase�a incorrectos.");
					throw new BadCredentialsException("Usuario o contrase�a incorrectos");
				}

			} else {
				logger.info("Usuario o contrase�a incorrectos.");
				throw new BadCredentialsException("Usuario o contrase�a incorrectos");
			}
		}
		return authentication;
	}

	public Collection<GrantedAuthority> getAuthorities(List<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(role);
		}
		return authorities;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
