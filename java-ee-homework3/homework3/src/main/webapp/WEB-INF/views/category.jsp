<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<div id="intro">
  <div id="intro_wrap">
    <div class="container_12">
      <div id="breadcrumbs" class="grid_12">
        <a href="/homework3">Home</a>
        &gt;
        <a href="/homewrok3/category/"><c:out value="${requestScope.categoryName}"/></a>
      </div>
      <h1><c:out value="${requestScope.categoryName}"/></h1>
    </div>
  </div>
</div>

<div id="content" class="container_12">
  <div id="category" class="grid_9">
    <div class="clear"></div>

    <div class="s_listing s_grid_view clearfix">
      <c:set var="counter" scope="request" value="${0}" />
      <c:forEach var="product" items="${requestScope.categoryProducts}">

        <div class="s_item grid_3"> <a class="s_thumb" href="/homework3/product/<c:out value="${product.id}"/>"><img src="/homework3/<c:out value="${product.img}"/>" title="<c:out value="${product.name}"/>" alt="<c:out value="${product.name}"/>" /></a>
          <h3><a href="product1.jsp"><c:out value="${product.name}"/></a></h3>
          <p class="s_model"><c:out value="${product.description}"/></p>
          <p class="s_price"><span class="s_currency s_before">$</span><c:out value="${product.price}"/></p>
          <p class="s_rating s_rating_5"><span style="width: 60%;" class="s_percent"></span></p>
          <a class="s_button_add_to_cart" href="/homework3/product/<c:out value="${product.id}"/>"><span class="s_icon_16"><span class="s_icon"></span>Add to Cart</span></a>
        </div>

        <c:if test="${counter != 3}">
          <c:set var="counter" scope="request" value="${counter + 1}" />
        </c:if>
        <c:if test="${counter == 3}">
          <c:set var="counter" scope="request" value="${0}" />
          <div class="clear"></div>
        </c:if>
      </c:forEach>
    </div>

    <div class="clear"></div>
  </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
