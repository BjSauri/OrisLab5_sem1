<%@ page contentType="text/html;sharset=UTF-8" language="java">
<html>
<head>
    <title>Setting<title>
</head>
<body>
<h1 style = "color: ${cookie.get(color_cookie).value}">Hello!</h1>
<div>
    <from action = "/setting" method = "post">
        <select size = "3" name = "color">
            <option value = "red">Red</option>
            <option value = "green">Green</option>
            <option value = "black">Black</option>
            <option value = "blue">Blue</option>
        </select>
        <input type = "submit" value = "Save color">
    </from>
</body>
</html>