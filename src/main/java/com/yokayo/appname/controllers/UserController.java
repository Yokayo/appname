package com.yokayo.appname.controllers;

import javax.inject.Inject;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.yokayo.appname.entities.User;
import com.yokayo.appname.services.UserService;

@RestController
@RequestMapping("users")
public class UserController {
    
    @Inject
    private UserService userService;
    
    public UserController() {}
    
    private class SaveUserResponse {
        public SaveUserResponse(String id) {
            this.id = id;
        }
        public String id;
    }
    
    // добавить пользователя
    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public SaveUserResponse addUser(@RequestBody User user) throws Exception {
        userService.saveUser(user);
        return new SaveUserResponse(user.id);
    }
    
    // получить пользователя
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable String id) {
        User user = userService.getUser(id);
        checkUserNotNull(user);
        return user;
    }
    
    private class UploadFileResponse {
        public UploadFileResponse(String id) {
            this.id = id;
        }
        public String id;
    }
    
    // загрузить аватарку
    @RequestMapping(value = "file", method = RequestMethod.POST)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return new UploadFileResponse(userService.saveUserPicture(file));
    }
    
    private class ChangeStatusResponse {
        public ChangeStatusResponse() {}
        public String id;
        public User.Status oldStatus;
        public User.Status newStatus;
    }
    
    // изменить статус пользователя
    @RequestMapping(value = "{id}/status/{status:online|offline}", method = RequestMethod.POST, produces = "application/json")
    public ChangeStatusResponse changeStatus(@PathVariable String id, @PathVariable User.Status status) throws Exception {
        User user = userService.getUser(id);
        checkUserNotNull(user);
        ChangeStatusResponse bean = new ChangeStatusResponse();
        bean.id = id;
        bean.oldStatus = user.status;
        bean.newStatus = status;
        user.status = status;
        return bean;
    }
    
    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    
}