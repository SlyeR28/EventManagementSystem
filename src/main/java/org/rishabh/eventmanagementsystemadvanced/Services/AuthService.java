package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);



}
