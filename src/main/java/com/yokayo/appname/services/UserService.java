package com.yokayo.appname.services;

import java.util.UUID;
import java.io.File;
import javax.inject.Inject;
import org.springframework.web.multipart.MultipartFile;
import com.yokayo.appname.entities.User;
import com.yokayo.appname.dao.UserDao;

public class UserService {
    
    @Inject
    private UserDao userDao;
    
    private final String ROOT_PATH = System.getProperty("catalina.base").replace("\\", "/") + "/webapps/test";
    private final String RESOURCES_FOLDER = "/res/";
    private final String ALLOWED_AVATAR_EXTENSION = "JPG";
    
    public UserService() {}
    
    // сохранить пользователя + присвоить идентификатор
    public String saveUser(User user) throws Exception {
        if (userDao.getByUsername(user.username) != null) {
            throw new IllegalArgumentException("User with username " + user.username + " already exists");
        }
        user.id = UUID.randomUUID().toString();
        userDao.save(user);
        return user.id;
    }
    
    // получить пользователя по идентификатору
    public User getUser(String id) {
        return userDao.get(id);
    }
    
    // сохранить аватарку
    public String saveUserPicture(MultipartFile file) throws Exception {
        String[] spl = file.getOriginalFilename().split("\\.");
        String fileExtension = spl[spl.length-1];
        if (!fileExtension.toUpperCase().equals(ALLOWED_AVATAR_EXTENSION)) {
            throw new IllegalArgumentException("Incorrect file format, only " + ALLOWED_AVATAR_EXTENSION + " images are allowed");
        }
        String outputFolderPath = ROOT_PATH + RESOURCES_FOLDER;
        File folder = new File(outputFolderPath);
        folder.mkdirs();
        String fileName = System.currentTimeMillis() + "." + fileExtension;
        file.transferTo(new File(outputFolderPath + fileName));
        return fileName;
    }
    
}