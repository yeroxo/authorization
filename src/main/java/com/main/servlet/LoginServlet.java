package com.main.servlet;

import com.database.dataSet.UserProfile;
import com.main.tech.Accounts.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        UserProfile authProfile = new UserProfile(login, pass, email);


        if (accountService.AuthorizateUser(authProfile, req.getSession())){
            resp.sendRedirect("/files");
        }
        else{
            resp.getWriter().println("Wrong login or password");
            return;
        }
    }
}
