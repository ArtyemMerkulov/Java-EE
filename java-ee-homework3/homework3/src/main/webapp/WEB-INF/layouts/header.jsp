<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
    <head>

        <title>My Shop on Java</title>

        <meta name="description" content="My Shop on Java" />

        <link rel="stylesheet" href="/homework3/resources/css/960.css" media="all" />
        <link rel="stylesheet" href="/homework3/resources/css/screen.css" media="screen"/>
        <link rel="stylesheet" href="/homework3/resources/css/color.css" media="screen"/>
        <!--[if lt IE 9]>
        <link rel="stylesheet" href="/homework3/resources/css/ie.css" media="screen" />
        <![endif]-->

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.11/jquery-ui.min.js"></script>
        <script type="text/javascript" src="/homework3/resources/js/shoppica.js"></script>
    </head>

    <body class="s_layout_fixed">

        <div id="wrapper">

        <div id="header" class="container_12">
            <div class="grid_12">

                <a id="site_logo" href="/homework3">Shoppica store - Premium e-Commerce Theme</a>

                <div id="system_navigation" class="s_nav">
                    <ul class="s_list_1 clearfix">
                        <li><a href="/homework3">Home</a></li>
                        <li><a href="/homework3/contacts">Contacts</a></li>
                    </ul>
                </div>

                <div id="categories" class="s_nav">
                    <ul>
                        <c:forEach var="category" items="${requestScope.categories}">
                            <li><a href="/homework3/category/<c:out value="${category.name}"/>"><c:out value="${category.name}"/></a> </li>
                        </c:forEach>
                    </ul>
                </div>

                <div id="cart_menu" class="s_nav">
                    <a href="/homework3/cart"><span class="s_icon"></span> <small class="s_text">Cart</small><span class="s_grand_total s_main_color">$0.00</span></a>
                </div>
            </div>
        </div>