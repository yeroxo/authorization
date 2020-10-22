package com.main.servlet;


import com.database.dataSet.FileModel;
import com.main.tech.Accounts.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/files")
public class MainServlet extends HttpServlet {
    AccountService accountService = new AccountService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessionId = req.getSession();
        String login;

        if(!accountService.CheckSessionId(sessionId)){
            resp.getWriter().println("sessionID error");
            return;
        }
        else{
            login = accountService.getLoginBySessionId(sessionId);
        }
        String DEFAULT_PATH = "C:\\Users" + "\\" + login;

        String getPath = req.getParameter("path");
        getPath = getPath == null || getPath.length() == 0 ? DEFAULT_PATH : getPath;

        req.setAttribute("defaultPath", DEFAULT_PATH);
        req.setAttribute("login",login);
        Path path = Paths.get(getPath);
        FileModel file = new FileModel(path.toFile());
        path.getParent();

        if (path.toString().contains("C:\\Users\\"+login)) {
            if (file.isDirectory()) {
                req.setAttribute("dateNow", new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(new Date()));
                req.setAttribute("path", path);
                req.setAttribute("parentPath", path.getParent().toString() + "\\" + login);
                req.setAttribute("files", scan(path));
                req.getRequestDispatcher("/pages/mypage.jsp").forward(req, resp);
            }

            if (file.isFile()) {
                resp.setHeader("Content-Type", "application/octet-stream");
                resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            }
        }
        else {
            req.getRequestDispatcher("/pages/error.jsp").forward(req, resp);
        }
    }

    protected List<FileModel> scan(Path path) throws IOException {
        return Files.list(path)
                .map(Path::toFile)
                .sorted(this::comparator)
                .map(this::fileFormatter)
                .collect(Collectors.toList());
    }

    protected int comparator(File a, File b) {
        return a.isDirectory() && b.isDirectory() || a.isFile() && b.isFile()
                ? a.compareTo(b)
                : Boolean.compare(a.isFile(), b.isFile());
    }

    protected FileModel fileFormatter(File a){
        return new FileModel(a);
    }
}

