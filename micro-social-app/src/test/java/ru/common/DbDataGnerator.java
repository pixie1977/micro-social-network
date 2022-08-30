package ru.common;

import ru.common.micro.social.dao.dto.User;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DbDataGnerator {
    public static void main(String[] args) {
//        String connectionUrl = "jdbc:mysql://92.255.76.33:6603/testdb";
        String connectionUrl = "jdbc:mysql://89.223.122.234:6603/testdb";
        String sql = "INSERT INTO USER_TABLE(id, login, password, firstName, lastName, city, gender, personal, age, roles)VALUES(?,?,?,?,?,?,?,?,?,?)";

        List<String> userList = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get("D:\\DEVELOPMENT\\PROJECTS\\Heroku\\micro-social-net\\micro-social-app\\src\\test\\resources\\test-names.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> userList.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int res = 0;
        try (Connection conn = DriverManager.getConnection(
                connectionUrl,
                "root",
                "pass"
        )) {

            for (int i = 0; i < userList.size(); i++) {


                System.out.println(i + " " + userList.get(i));
                String[] splitNames = userList.get(i).split(" ");

                User user = new User();
                user.setId(User.idGenerator());
                user.setRoles("ROLE_USER");
                user.setLogin("login_" + i);
                user.setPassword(User.PASSWORD_ENCODER.encode("p"));
                user.setPersonal("Personal_" + i);
                user.setCity("city_" + i);
                user.setAge(i % 100);
                user.setFirstName(splitNames[0]);
                user.setLastName(splitNames[1]);
                user.setGender("M");

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setLong(1, user.getId());
                pst.setString(2, user.getLogin());
                pst.setString(3, user.getPassword());
                pst.setString(4, user.getFirstName());
                pst.setString(5, user.getLastName());
                pst.setString(6, user.getCity());
                pst.setString(7, user.getGender());
                pst.setString(8, user.getPersonal());
                pst.setInt(9, user.getAge());
                pst.setString(10, user.getRoles());

                res = pst.executeUpdate();

                if (i % 100 == 0) {
                    conn.prepareStatement("COMMIT").execute();
                }
                pst.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
