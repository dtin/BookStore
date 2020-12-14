<%-- 
    Document   : homePage
    Created on : Aug 12, 2020, 5:17:43 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage - Book Store</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="booksList" value="${requestScope.BOOKS_LIST}" />

        <!-- Start of header -->
        <div class="header sticky-top fixed-top">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2">
                        <c:choose>
                            <c:when test="${empty userFullName}">
                                <p class="welcome">Hello Guest!</p>                                
                            </c:when>
                            <c:otherwise>
                                <p class="welcome">Hello ${userFullName}!</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-1">
                        <a class="home float-left" href="DispatchController"><i class="fas fa-home"></i></a>
                    </div>
                    <div class="col-sm-6">
                        <form action="DispatchController" method="GET">
                            <div class="form-row align-item-center">
                                <div class="col-sm-1"></div>
                                <input type="text" class="col-sm-5 search-text" name="txtSearch"
                                       placeholder="Search Book..." value="${searchValue}">
                                <div class="col-sm-1"></div>
                                <button type="submit" name="btnAction" class="col-sm-2 btn btn-primary" value="Search">Search</button>
                                <div class="col-sm-3"></div>
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-1">
                        <c:url var="cart" value="DispatchController">
                            <c:param name="btnAction" value="Cart" />
                        </c:url>
                        <a href="${cart}" class="view-cart-icon float-left">
                            <i class="fas fa-shopping-cart"></i>
                        </a>
                    </div>
                    <div class="col-sm-1">
                        <c:url var="history" value="DispatchController">
                            <c:param name="btnAction" value="History" />
                        </c:url>
                        <a href="${history}" class="view-cart-icon float-left">
                            <i class="fas fa-history"></i>
                        </a>
                    </div>

                    <div class="col-sm-1">
                        <c:choose>    
                            <c:when test="${empty userFullName}">
                                <c:url var="login" value="DispatchController">
                                    <c:param name="btnAction" value="Login" />
                                </c:url>
                                <a href="${login}" class="sign-out-icon float-left">
                                    <i class="fas fa-sign-in-alt"></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <c:url var="logout" value="DispatchController">
                                    <c:param name="btnAction" value="Logout" />
                                </c:url>
                                <a href="${logout}" class="sign-out-icon float-left">
                                    <i class="fas fa-sign-out-alt"></i>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of Header -->

        <div class="pt-5">
            <h4 class="book-title text-center text-uppercase pb-5">new arrivals</h4>
        </div>

        <!-- Start of List of books-->
        <div class="container pb-5">
            <div class="row">
                <c:choose>
                    <c:when test="${not empty booksList}">
                        <c:forEach var="book" items="${booksList}" varStatus="counter">
                            <div class="col-sm-4 pb-3">
                                <div class="card text-center" style="width: 18rem;">
                                    <img class="card-img-top" src="assets/images/${book.image}" alt="${book.productName}">
                                    <div class="card-body">
                                        <h5 class="card-title">${book.productName}</h5>
                                        <p class="card-text" id="price-${counter.count}">${book.price}</p>
                                        <c:url var="addToCart" value="DispatchController">
                                            <c:param name="btnAction" value="Add To Cart" />
                                            <c:param name="productId" value="${book.productId}" />
                                        </c:url>
                                        <a href="${addToCart}" class="btn btn-outline-success">Add to cart</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-4"></div>
                                <div class="col-sm-4">
                                    <h5 class="text-center">System not have any book...</h5>
                                </div>
                                <div class="col-sm-4"></div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
        <!--End of list of books-->    

        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
        <script>
            $(function () {
                for (var i = 1; i <= ${fn:length(booksList)}; i++) {
                    $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
                }
            });
        </script>
    </body>

</html>