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
    <p class="error">
        <c:if test="${not empty errors}">
            <c:set var="error" value="${errors}"/>
            <c:forEach var="item" items="${cart.items}">
                ${errors[item.product.id]}
            </c:forEach>
        </c:if>
    </p>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
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
                        <c:set var="error" value="${errors[item.product.id]}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <c:set var="errors" value="${errors}"/>
        <p class="error">
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </p>

        <table>
            <tr>
                <td>First name</td>
                <td>
                    <input type="text" name="first_name" value="${not empty error? param['first_name']:order.firstName}"
                        <%--                           oninvalid="this.setCustomValidity('min 5, max 15')"--%>
                        <%--                           oninput="this.setCustomValidity('')"--%>
                        <%--                           pattern="[A-Za-zА-Яа-я]{3,20}"--%>
                           id="first_name" placeholder="Enter Name"
                        <%--                           required/>--%>/>
                    <c:set var="error" value="${errors['first_name']}"/>
                    <p class="error">
                        <c:if test="${not empty error}">
                            ${error}
                        </c:if>
                    </p>
                </td>
            </tr>
            <tr>
                <td>Last name</td>
                <td>
                    <input type="text" name="last_name" value="${not empty errors? param['last_name']:order.lastName}"
                           oninvalid="this.setCustomValidity('min 5, max 15')"
                           oninput="this.setCustomValidity('')"
                           pattern="[A-Za-zА-Яа-я]{3,20}"
                           id="last_name" placeholder="Enter Last Name" required/></td>
                <c:set var="error" value="${errors['last_name']}"/>
                <p class="error">
                    <c:if test="${not empty error}">
                        ${error}
                    </c:if>
                </p>
            </tr>
            <tr>
                <td>Delivery date</td>
                <td>
                    <input type="date" name="delivery_date"
                           value="${not empty errors? param['delivery_date']:order.deliveryDate}"
                           id="deliveryDate" placeholder="Enter Delivery Date" required/></td>
                <c:set var="error" value="${errors['delivery_date']}"/>
                <p class="error">
                    <c:if test="${not empty error}">
                        ${error}
                    </c:if>
                </p>
            </tr>
            <tr>
                <td>Address</td>
                <td>
                    <input type="text" name="address" value="${not empty errors? param['address'] : order.address}"
                           oninvalid="this.setCustomValidity('min 5, max 15')"
                           oninput="this.setCustomValidity('')"
                           pattern="[A-Za-z0-9][A-Za-z0-9_]{5,15}"
                           id="address" placeholder="Enter Address" required/></td>
                <c:set var="error" value="${errors['address']}"/>
                <p class="error">
                    <c:if test="${not empty error}">
                        ${error}
                    </c:if>
                </p>
            </tr>
            <tr>
                <td>
                    Payment method
                </td>
                <td>
                    <c:set var="error" value="${errors['payment_method']}"/>
                    <p class="error">
                        <c:if test="${not empty error}">
                            ${error}
                        </c:if>
                    </p>
                    <select name="payment_method" id="payment_method">
                        <c:if test="${not empty errors}">
                            <option value="${param['payment_method']}">${param['payment_method']}</option>
                        </c:if>
                        <c:forEach var="PaymentMethod" items="${payment_method}">
                            <option></option>
                            <option value="${PaymentMethod}">${PaymentMethod}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <p>
            <button>Create</button>
        </p>
    </form>

</tags:master>