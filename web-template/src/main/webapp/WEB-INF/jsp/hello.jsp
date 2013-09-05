<%--
  Created by IntelliJ IDEA.
  User: huaiwang
  Date: 8/19/13
  Time: 9:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello jsp</title>
    <style type="text/css">
        .row div {
            background: #f0f0f0;
        }

        .row-fluid div {
            background: #f0f0f0;
        }
    </style>
</head>
<body>
    hello spring mvc @${time}

    <a href="javascript:;" class="btn">Hello Button</a>

    <div class="row">
        <div class="span2">span1</div>
        <div class="span10">span1</div>
    </div>


    <div class="row-fluid">
        <div class="span4">...</div>
        <div class="span4">...</div>
        <div class="span4">...</div>
    </div>
</body>
</html>