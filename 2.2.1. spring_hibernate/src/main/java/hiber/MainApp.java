package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru").setNoCar(new Car("Velik", 1)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru").setNoCar(new Car("LADA", 6)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru").setNoCar(new Car("BMW", 5)));


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getNoCar() != null) {
                System.out.println("Car model = " + user.getNoCar().getModel());
                System.out.println("Car series = " + user.getNoCar().getSeries());
                System.out.println();
            }
            System.out.println();
        }

        List<User> users_by_car = userService.getUserByCar("BMW", 5);
        users_by_car.stream()
                .map(user -> "Есть такой автомобиль у First Name: " + user.getFirstName() + ", Last Name: " + user.getLastName())
                .forEach(System.out::println);
        context.close();
    }
}
