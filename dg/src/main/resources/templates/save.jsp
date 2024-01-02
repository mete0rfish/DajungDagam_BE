<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>save</title>
</head>
<body>
<form action="/trade/posts" method="post" enctype="multipart/form-data">
    title: <input type="text" name="title"> <br>
    contents: <textarea name="content" cols="30" rows="10"></textarea> <br>
    <input type="submit" value="글작성">
</form>
</body>

<%--<script>--%>

<%--    function test() {--%>
<%--        $.ajax({--%>
<%--            url: "<c:url value="/" />",--%>
<%--            type: "post",--%>
<%--            data: JSON.stringify(obj),--%>
<%--            dataType: "json",--%>
<%--            contentType: "application/json",--%>
<%--            success: function(data) {--%>
<%--                alert("성공");--%>
<%--            },--%>
<%--            error: function(errorThrown) {--%>
<%--                alert(errorThrown.statusText);--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>
<%--</script>--%>
</html>