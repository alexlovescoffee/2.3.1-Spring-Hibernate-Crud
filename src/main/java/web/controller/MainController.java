package web.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Address;
import web.model.User;
import web.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/* ToDo
 Если в полях формы, какое то значение поля Entity не установлено выдает ошибку
 Настроить charset=UTF-8 для всех методов
 Связать объект из Thymeleaf формы c приходящим в контроллер объектом через @ModelAttribute?
* */

@Controller
@RequestMapping("/users")
public class MainController {
    @Autowired
    private UserService service;

    @GetMapping
    public String mainPage(ModelMap model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("add")
    @ResponseBody
    public void addUser(/*@ModelAttribute */ final User user) {
        service.addUser(user);
    }

    @PostMapping("update")
    @ResponseBody
    public void updateUser(final User user, HttpServletResponse response) throws IOException {
        if (service.getUser(user.getId()) == null) {
            response.setStatus(404);
            response.getWriter().println("User with id=" + user.getId() + " doesn't exist");
        } else
            service.updateUser(user);
    }

    @PostMapping("delete")
    @ResponseBody
    public void deleteUser(@RequestParam(name = "id") int id, HttpServletResponse response) throws IOException {
        if (service.getUser(id) == null) {
            response.setStatus(404);
            response.getWriter().println("User with id=" + id + " doesn't exist");
        } else
            service.deleteUser(id);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    @ResponseBody
    public String getUsers() {
        List<User> users = service.getAllUsers();
        JsonArray jsonArray = new JsonArray();
        for (User user : users) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", user.getId());
            jsonObject.addProperty("name", user.getName());
            jsonObject.addProperty("age", user.getAge());
            Address address = user.getAddress();
            if (address == null) {
                jsonObject.addProperty("city",  "");
                jsonObject.addProperty("street",  "");
                jsonObject.addProperty("building",  "");
            } else {
                jsonObject.addProperty("city", address.getCity());
                jsonObject.addProperty("street",  address.getStreet());
                jsonObject.addProperty("building",  address.getBuilding());
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }


//    @ModelAttribute
//    public void addAttributes(Model model) {
//        model.addAttribute("usr", new User());
//    }


//    private String httpServletRequestToString(HttpServletRequest request) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("Request Method = [" + request.getMethod() + "], ");
//        sb.append("Request URL Path = [" + request.getRequestURL() + "], ");
//
//        String headers = Collections.list(request.getHeaderNames()).stream()
//                        .map(headerName -> headerName + " : " + Collections.list(request.getHeaders(headerName)) )
//                        .collect(Collectors.joining(", "));
//
//        if (headers.isEmpty())
//            sb.append("Request headers: NONE,");
//        else
//            sb.append("Request headers: ["+headers+"],");
//
//        String parameters =
//                Collections.list(request.getParameterNames()).stream()
//                        .map(p -> p + " : " + Arrays.asList( request.getParameterValues(p)) )
//                        .collect(Collectors.joining(", "));
//
//        if (parameters.isEmpty())
//            sb.append("Request parameters: NONE.");
//        else
//            sb.append("Request parameters: [" + parameters + "].");
//
//        return sb.toString();
//    }
}
