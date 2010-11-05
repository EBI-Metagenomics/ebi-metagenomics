<html>
<head><title>Example :: Spring Application</title></head>
<body>
<h1>Example - Spring Application</h1>

<p>Hello world</p>

<p>
    Your browser is: <%= request.getHeader("User-Agent") %><br>
    Your IP address is: <%= request.getRemoteAddr() %><br>
</p>
</body>
</html>