package com.main.servlet;

import com.main.tech.Accounts.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet  extends HttpServlet {

    AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        String stringFormat = "[a-zA-Z]+";
        String emailFormat = ".+@.+\\..+";
        boolean s = email.matches(emailFormat);
        if(!login.matches(stringFormat) || !pass.matches(stringFormat)){
            resp.getWriter().println("wrong login format");
            return;
        }
        if(login.length() < 3 || pass.length() < 3){
            resp.getWriter().println("wrong login length or pass");
            return;
        }
        if(!email.matches(emailFormat)){
            resp.getWriter().println("wrong email");
            return;
        }
        if(accountService.AddNewUser(login, pass, email)){
            resp.sendRedirect("/login/");
        }
        else {
            resp.getWriter().println("this login has been used");
            return;
        }
    }
}
