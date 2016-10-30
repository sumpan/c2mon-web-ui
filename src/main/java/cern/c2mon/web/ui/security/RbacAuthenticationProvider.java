/*******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 *
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 *
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package cern.c2mon.web.ui.security;

import cern.c2mon.client.common.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Custom RBAC-based {@link AuthenticationProvider} implementation.
 *
 * @author Justin Lewis Salmon
 */
@Slf4j
@Component
public class RbacAuthenticationProvider implements AuthenticationProvider {

  private final static String APP_NAME = "c2mon-web";

  @Autowired
  private SessionService sessionService;

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    String username = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    // Don't attempt to login the user if they are already logged in.
    if (!sessionService.isUserLogged(username)) {
      if (!sessionService.login(APP_NAME, username, password)) {
        throw new BadCredentialsException("Invalid username/password");
      }
    } else {
      log.debug("Repeated login for user " + username);
    }

    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
  }

  @Override
  public boolean supports(final Class<? extends Object> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
