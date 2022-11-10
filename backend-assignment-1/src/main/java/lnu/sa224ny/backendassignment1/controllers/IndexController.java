package lnu.sa224ny.backendassignment1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class IndexController {
    @RequestMapping("/api")
    public String home() {
        return "Hello from assignment 1 api!";
    }
}
