<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sortList sort="description" order="asc"></tags:sortList>
                <tags:sortList sort="description" order="desc"></tags:sortList>
            </td>
            <td class="price">Price
                <tags:sortList sort="price" order="asc"></tags:sortList>
                <tags:sortList sort="price" order="desc"></tags:sortList>

            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src=${product.imageUrl}>
                </td>
                <td><a href="${pageContext.servletContext.contextPath}/product/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/price_history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
<p>
    Recently viewed
</p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="product" items="${view_history.getViewHistory()}">
            <tr>
                <td>
                    <img class="product-tile" src=${product.imageUrl}>
                </td>
                <td><a href="${pageContext.servletContext.contextPath}/product/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/priceHistory/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>