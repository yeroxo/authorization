package com.main.servlet;

import com.main.tech.Accounts.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServlet extends HttpServlet {
    AccountService accountService = new AccountService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        accountService.Quit(req.getSession());
        resp.sendRedirect("/login/");
    }
}
