<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <p>
        Subtotal price
            ${order.subtotal}
    </p>
    <p>
        Delivery price
            ${order.deliveryCost}
    </p>
    <p>
        Total price = Subtotal price + Delivery price</p>
    <p>
            ${order.totalPrice} = ${order.subtotal} + ${order.deliveryCost}
    </p>
    <p>
        Total quantity
            ${order.totalQuantity}
    </p>
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
            <c:forEach var="item" items="${order.items}">
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
                            ${item.quantity}
                    </td>
                </tr>
            </c:forEach>
        </table>

        <table>
            <tr>
                <td>First name</td>
                <td>
                    ${order.firstName}
                    </p>
                </td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>
                        ${order.lastName}
                </td>
                </p>
            </tr>
            <tr>
                <td>Delivery date</td>
                <td>
                        ${order.deliveryDate}
                </td>
                </p>
            </tr>
            <tr>
                <td>Address</td>
                <td>
                        ${order.address}
                </td>
            </tr>
            <tr>
                <td>
                    Payment method
                </td>
                <td>
                        ${order.paymentMethod}
                </td>
            </tr>
        </table>



</tags:master>