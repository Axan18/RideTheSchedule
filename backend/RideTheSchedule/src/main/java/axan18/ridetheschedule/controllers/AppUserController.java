package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.services.AppUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AppUserController {

    private final AppUserServices appUserServices;

    public static final String USER_PATH = "/api/v1/auth";
    public static final String USER_PATH_ID = USER_PATH+"/{userId}";




}
