<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<c:set var="product" scope="request" value="${requestScope.product}"/>
<div id="intro">
    <div id="intro_wrap">
        <div class="container_12">
            <div id="breadcrumbs" class="grid_12">
                <a href="/homework3">Home</a>
                &gt;
                <a href="/homework3/category/<c:out value="${requestScope.categoryName}"/>"><c:out value="${product.categoryName}"/></a>
            </div>
            <h1><c:out value="${product.categoryName}"/></h1>
        </div>
    </div>
</div>

<div id="content" class="product_view container_16">
    <div id="product" class="grid_12">
        <div id="product_images" class="grid_6 alpha">
            <a id="product_image_preview" rel="prettyPhoto[gallery]" href="/homework3/<c:out value="${product.img}"/>"><img id="image" src="/homework3/<c:out value="${product.img}"/>" title="<c:out value="${product.name}"/>" alt="<c:out value="${product.name}"/>" style="width: 340px;"/> </a>
        </div>

        <div id="product_info" class="grid_6 omega">
            <p class="s_price"><span class="s_currency s_before">$</span><c:out value="${product.price}"/></p>

            <div class="clearfix">
                </br>
            </div>

            <dl class="clearfix">
                <dt>Name</dt>
                <dd><c:out value="${product.name}"/></dd>
                <dt>Availability</dt>
                <dd>In Stock</dd>
                <dt>Average Rating</dt>
                <dd>
                    <p class="s_rating s_rating_5"><span style="width: 100%;" class="s_percent"></span></p>
                </dd>
            </dl>

            <div class="clearfix">
                </br>
                </br>
            </div>
            <div class="clear">
                </br>
                </br>
            </div>

            <div id="product_buy" class="clearfix">
                <label for="product_buy_quantity">Qty:</label>
                <input id="product_buy_quantity" type="text" size="2" />
                <a id="add_to_cart" class="s_main_color_bgr" href="/homework3/cart/add?id=<c:out value="${product.id}"/>"><span class="s_text"><span class="s_icon"></span> Add to Cart</span></a>
            </div>
        </div>

        <div class="clear"></div>

        <div class="s_tabs grid_12 alpha omega">
            <ul class="s_tabs_nav clearfix">
                <li><a href="#product_description">Description</a></li>
            </ul>

            <div class="s_tab_box">
                <div id="product_description">
                    <div class="cpt_product_description ">
                        <c:out value="${product.description}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />