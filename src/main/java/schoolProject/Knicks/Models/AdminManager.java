package schoolProject.Knicks.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminManager {

    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_PASSWORD = "admin123";

    @Autowired
    public AdminRepo adminRepo;

    public void seedDefaultAdmin() {
        if (adminRepo.count() > 0) {
            return;
        }
        adminRepo.save(Admin.builder()
                .username(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD)
                .build());
    }

    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return adminRepo.findByUsername(username.trim())
                .map(admin -> admin.getPassword().equals(password))
                .orElse(false);
    }
}
