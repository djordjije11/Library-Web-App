package com.djordjije11.libraryappapi.service.authentication;

import com.djordjije11.libraryappapi.controller.request.authentication.AuthenticationRequest;
import com.djordjije11.libraryappapi.controller.response.authentication.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(AuthenticationRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
}
