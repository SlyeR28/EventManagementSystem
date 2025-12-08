package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ChangePasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ForgetPasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ResetPasswordRequest;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

    void changePassword(Long userId ,ChangePasswordRequest changePasswordRequest);

    void forgetPassword( ForgetPasswordRequest forgetPasswordRequest);

    void resetPassword(ResetPasswordRequest request);


}
