<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <table>
        <thead>
        <tr>
            <td>Start Date</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="dateAndPrice" items="${product.priceHistory.entrySet()}">
            <tr>
                <td class="date">
                        ${dateAndPrice.getKey()}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${dateAndPrice.getValue()}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>