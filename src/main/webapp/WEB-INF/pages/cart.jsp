<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%--<jsp:useBean id="cart" type="java.util.ArrayList" scope="request"/>--%>
<tags:master pageTitle="Product List">
    <p>
            ${cart}
    </p>
    <p>
        Total quantity
        ${cart.totalPrice}
    </p>
    <p class="success">
        <c:if test="${not empty param.message}">
            <c:if  test="${empty error}">
                ${param.message}
            </c:if>
        </c:if>
    </p>
    <p class="error">
        <c:if test="${not empty errors}">
            ${errors}
        </c:if>
    </p>
    <form method="post" action="${pageContext.servletContext.contextPath}/cart">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description
                </td>
                <td class="price">Price
                </td>
                <td class="quantity">Quantity
                </td>

            </tr>
            </thead>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <img class="product-tile" src=${item.product.imageUrl}>
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/product/${item.product.id}">${item.product.description}</a>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/price_history/${item.product.id}">
                            <fmt:formatNumber value="${item.product.price}" type="currency"
                                              currencySymbol="${item.product.currency.symbol}"/>
                        </a>
                    </td>

                    <td class="quantity">
                        <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                        <c:set var="error" value="${errors[item.product.id]}"/>


                        <input name="quantity"  value="${not empty error ? param.quantity : item.quantity}" class="quantity" />
                        <input type="hidden" name="product_id" value="${item.product.id}">
                        <p class="error">
                            <c:if test="${not empty error}">
                                ${error}
                            </c:if>
                        </p>
                        <p class="success">
                            <c:if test="${empty error}">
                                Added successfully
                            </c:if>
                        </p>
                    </td>
                    <td>
                        <button form="deleteCartItem" formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button value="">Update</button>

    </form>

    <form id="deleteCartItem" action="post"></form>

</tags:master>