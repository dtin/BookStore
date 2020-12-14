<%-- 
    Document   : adminPanel
    Created on : Dec 11, 2020, 3:09:09 PM
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
        <title>Admin Page - Book Store</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="productList" value="${requestScope.PRODUCT_LIST}" />
        <c:set var="err" value="${requestScope.ERROR}" />

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
                                       placeholder="Search Book..." value="">
                                <div class="col-sm-1"></div>
                                <button type="submit" name="btnAction" class="col-sm-2 btn btn-primary" value="Search">Search</button>
                                <div class="col-sm-3"></div>
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-2">
                        <c:url var="addNewBook" value="DispatchController">
                            <c:param name="btnAction" value="Add" />
                        </c:url>

                        <a class="btn btn-outline-dark"href="${addNewBook}">Add new book</a>
                        
                        <c:url var="discount" value="DispatchController">
                            <c:param name="btnAction" value="Discount" />
                        </c:url>
                        <a class="btn btn-outline-dark"href="${discount}">Discount</a>
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

        <!--Start of Title-->
        <div class="pt-5">
            <h4 class="book-title text-center text-uppercase pb-5">newest books</h4>
            <c:if test="${not empty err}">
                <h5 class="book-title text-center text-uppercase pb-5">${err.notFoundBook}</h4>
                </c:if>
        </div>
        <!--End of title-->

        <c:choose>
            <c:when test="${not empty productList}">
                <!-- Start of List of search items-->
                <div class="container pb-5">
                    <div class="row">
                        <c:forEach var="book" items="${productList}" varStatus="counter">
                            <div class="col-sm-12 mb-3">
                                <div class="card">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col-sm-1">
                                                <img class="search-image" src="assets/images/${book.productsDTO.image}" width="111" height="111"/>
                                            </div>
                                            <div class="col-sm-5">
                                                <p class="cart-book-name">
                                                    ${book.productsDTO.productName}
                                                </p>
                                                <p class="search-description">
                                                    ${book.productsDTO.description}
                                                </p>
                                            </div>
                                            <div class="col-sm-2 text-center">
                                                <p class="search-price" id="price-${counter.count}">
                                                    ${book.productsDTO.price}
                                                </p>
                                            </div>
                                            <div class="col-sm-2 text-center">
                                                <p class="search-author">
                                                    ${book.productsDTO.author}
                                                </p>
                                                <p class="search-category">
                                                    ${book.categoryName}
                                                </p>
                                            </div>
                                            <div class="col-sm-2">
                                                <c:url var="update" value="DispatchController">
                                                    <c:param name="btnAction" value="Update"></c:param>
                                                    <c:param name="productId" value="${book.productsDTO.productId}"></c:param>
                                                </c:url>
                                                <a class="btn btn-outline-success update-admin" href="${update}">Update</a>
                                                <a class="btn btn-outline-danger delete-admin" onclick="deleteBook('${book.productsDTO.productName}', '${book.productsDTO.productId}')">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
                <!--End of list of search items--> 
            </c:when>
        </c:choose>

        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

        <script>
        $(function () {
            for (var i = 1; i <= ${fn:length(productList)}; i++) {
                $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
            }
        });

        function deleteBook(bookName, bookId) {
            if (confirm("Are you sure you want to delete '" + bookName + "'?")) {
                window.location.href = "DispatchController?btnAction=Delete&productId=" + bookId;
            }
        }
        ;
        </script>
    </body>



</html>
