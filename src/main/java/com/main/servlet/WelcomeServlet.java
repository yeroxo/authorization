package com.main.servlet;

import com.main.tech.Accounts.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    AccountService accountService = new AccountService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (accountService.CheckSessionId(req.getSession())){
            resp.sendRedirect("/files");
        }
        else{
            req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
        }
    }
}
