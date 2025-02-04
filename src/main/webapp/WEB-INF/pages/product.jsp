<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
            ${product.description}
    </p>
    <p>
            ${cart}
    </p>
    <p class="success">
            <c:if test="${not empty param.message}">
                <c:if  test="${empty error}">
                    ${param.message}
                </c:if>
            </c:if>
    </p>
    <p class="error">
        <c:if test="${not empty error}">
            ${error}
        </c:if>
    </p>
    <form method="post">
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
                <td>Quantity
                </td>
            </tr>
            </thead>

            <tr>
                <td>
                    <img class="product-tile" src=${product.imageUrl}>
                </td>
                <td> ${product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${product.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
                <td class="error">
                   <input min="1" name="quantity" value="${not empty param.error? param.quantity : 1}">
                    <button>Add to cart</button>
                    <c:if test="${not empty error}">
                        ${error}
                    </c:if>
                </td>
            </tr>

        </table>
    </form>

</tags:master>