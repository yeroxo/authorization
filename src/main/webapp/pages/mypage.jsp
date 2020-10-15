<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Файлы</title>
</head>
<body>
<div>${dateNow}</div>
<h2>${login}</h2>
<h2>
    <a href="/back/account/out/">
        Выход
    </a>
</h2>
<h1>${path}</h1>

<j:if test="${path.getParent() != null && parentPath != defaultPath}">
    <tr>
        <td>
            <a href="/files?path=${path.getParent().toString().replace("\\","/")}">
                <img src="https://icons.iconarchive.com/icons/custom-icon-design/flatastic-10/16/Open-file-icon.png">
                <span>Вверх</span>
            </a>
        </td>
    </tr>
</j:if>

<table>
    <tr>
        <th>Файл</th>
        <th>Размер</th>
        <th>Дата</th>
    </tr>

    <j:forEach items="${files}" var="file">
        <j:if test="${!file.isHidden()}">
            <tr>
                <td>
                    <a href="/files?path=${file.getAbsolutePath()}">
                        <j:if test="${file.isDirectory()}">
                            <img src="https://icons.iconarchive.com/icons/cornmanthe3rd/plex/16/System-documents-icon.png">
                        </j:if>
                        <j:if test="${file.isFile()}">
                            <img src="https://icons.iconarchive.com/icons/iconsmind/outline/16/File-icon.png">
                        </j:if>
                        <span>${file.getName()}</span>
                    </a>
                </td>
                <j:if test="${file.isFile()}">
                    <td>${file.getSize()}</td>
                    <td>${file.getTimeCreated()}</td>
                </j:if>
            </tr>
        </j:if>
    </j:forEach>
</table>

</body>
</html>