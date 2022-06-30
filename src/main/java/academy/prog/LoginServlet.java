package academy.prog;

import jakarta.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    static final String LOGIN = "Admin";
    static final String PASS = "Java123456";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        int age = 0;

        try {
            age = Integer.parseInt(request.getParameter("age"));
        } catch (NumberFormatException e) {
            session.setAttribute("fault", "Enter numbers in the Age box");
            response.sendRedirect("index.jsp");
            return;
        }

        if(age < 18){
            session.setAttribute("fault", "Your age is under 18");
            response.sendRedirect("index.jsp");
            return;
        }

        if(!password.matches("\\A(?=\\S*?[0-9@#$%^&+=])(?=\\S*?[a-z])(?=\\S*?[A-Z])\\S{10,}\\z")){
            session.setAttribute("fault", "Must be at least 10 characters, one lowercase and one uppercase");
            response.sendRedirect("index.jsp");
            return;
        }


        if (LOGIN.equals(login) && PASS.equals(password)) {
            session = request.getSession(true);
            session.setAttribute("user_login", login);
            response.sendRedirect("index.jsp");
            return;
        }
        session.setAttribute("fault", "Check if the username or password is correct");
        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null)){
            session.removeAttribute("user_login");
            session.removeAttribute("fault");
        }

        response.sendRedirect("index.jsp");
    }
}
