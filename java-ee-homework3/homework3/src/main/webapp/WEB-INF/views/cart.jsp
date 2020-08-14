<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<c:set var="amountOfOrderProducts" scope="request" value="${requestScope.amountOfOrderProducts}" />
<c:set var="totalPrice" scope="request" value="${requestScope.totalPrice}" />

<div id="intro">
  <div id="intro_wrap">
    <div class="container_12">
      <div id="breadcrumbs" class="grid_12">
      </div>
    </div>
  </div>
</div>

<div id="content" class="container_12">

  <div id="shopping_cart" class="grid_12">

    <c:url value="/cart/checkout" var="productPostUrl"/>
    <form id="cart" class="clearfix" action="${productPostUrl}" method="post">
      <table class="s_table_1" width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <th width="60">Image</th>
          <th width="320">Name</th>
          <th>Quantity</th>
          <th>Unit Price</th>
          <th>Total</th>
        </tr>

        <c:forEach var="product" items="${requestScope.orderProducts}">
          <tr class="even">
            <c:set var="prod" scope="request" value="${product.f}" />
            <c:set var="amount" scope="request" value="${product.s}" />
            <td valign="middle"><a href="/homework3/product/<c:out value="${prod.id}"/>"><img src="/homework3/<c:out value="${prod.img}"/>" width="60" height="60" alt="<c:out value="${prod.name}"/>" /></a></td>
            <td valign="middle"><a href="/homework3/product/<c:out value="${prod.id}"/>"><strong><c:out value="${prod.name}"/></strong></a></td>
            <td valign="middle"><c:out value="${amount}"/></td>
            <td valign="middle"><c:out value="${prod.price}"/><span class="s_currency s_after"> $</span></td>
            <td valign="middle"><c:out value="${prod.price * amount}"/><span class="s_currency s_after"> $</span></td>
          </tr>
        </c:forEach>
      </table>

      <br />

      <c:set var="cartPrice" scope="request" value="${requestScope.totalPrice}" />
      <p class="s_total s_secondary_color last"><strong>Total:</strong> <c:out value="${cartPrice.totalPrice}"/><span class="s_currency s_after"> $</span></p>

      <div class="clear"></div>
      <br />

      <button class="s_button_1 s_main_color_bgr" type="submit"><span class="s_text">Checkout</span></button>
    </form>

  </div>

  <div class="clear"></div>
  <br />
  <br />

</div>

<jsp:include page="../layouts/footer.jsp" />