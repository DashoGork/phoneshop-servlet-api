<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%--<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>--%>
<tags:master pageTitle="AdvancedSearch">
    <p>
        Advanced Search
    </p>

    <form method="post" action="${pageContext.servletContext.contextPath}/advancedSearch">
        Description
        <input name="query"  value="${param.query}">
        <p class="error">
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </p>
        Search Type
            <select name="matching_method" id="matching_method">
                <c:forEach var="PaymentMethod" items="${matching_method}">
                    <option value="${PaymentMethod}">${PaymentMethod}</option>
                </c:forEach>
            </select>


        Min Price<input name="min_price" type="number" oninput="this.setCustomValidity('')"
                        pattern="[0-9]{1,15}" value="${param.min}">
        <c:set var="error" value="${errors['min_price']}"/>
        <p class="error">
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </p>
        Max Price<input name="max_price" type="number" oninvalid="this.setCustomValidity('min 1, max 15')"
                        oninput="this.setCustomValidity('')"
                        pattern="[0-9]{1,15}">
        <c:set var="error" value="${errors['max_price']}"/>
        <p class="error">
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </p>

        <button formaction="${pageContext.servletContext.contextPath}/advancedSearch" formmethod="post">Search</button>
    </form>
    <c:if test="${not empty products}">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
            </td>
            <td>Quantity</td>
            <td class="price">Price
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
    </c:if>
</tags:master>