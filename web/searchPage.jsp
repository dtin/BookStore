<%-- 
    Document   : searchPage
    Created on : Dec 8, 2020, 10:02:11 PM
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
        <title>Search Page - Book Store</title>

        <!--Bootstrap CSS-->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <!-- Main CSS -->
        <link rel="stylesheet" href="assets/css/main.css"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </head>

    <body>
        <c:set var="userFullName" value="${requestScope.FULL_NAME}" />
        <c:set var="err" value="${requestScope.ERROR}" />
        <c:set var="searchValue" value="${requestScope.SEARCH_VALUE}" />
        <c:set var="productList" value="${requestScope.PRODUCT_LIST}" />
        <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />
        <c:set var="minPrice" value="${requestScope.MIN_PRICE}" />
        <c:set var="maxPrice" value="${requestScope.MAX_PRICE}" />
        <c:set var="oldMinPrice" value="${requestScope.OLD_MIN_PRICE}" />
        <c:set var="oldMaxPrice" value="${requestScope.OLD_MAX_PRICE}" />
        <c:set var="categoryId" value="${requestScope.CATEGORY_ID}" />
        <c:set var="isAdmin" value="${requestScope.IS_ADMIN}" />

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


                    <c:choose>
                        <c:when test="${isAdmin}">
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
                        </c:when>
                        <c:when test="${!isAdmin}">
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
                        </c:when>
                    </c:choose>

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
            <h4 class="book-title text-center text-uppercase pb-5">search result for "${searchValue}"</h4>
        </div>
        <!--End of title-->

        <!--Start of filter-->
        <form action="DispatchController" method="GET">
            <div class="pt-3 pb-3">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-1">
                            <h4 class="text-uppercase">Filter: </h4>
                        </div>

                        <div class="col-sm-1">
                            <p class="mt-1">Category: </p>
                        </div>

                        <div class="col-sm-2">
                            <select class="custom-select" name="categoryId" id="categoryId">
                                <option value="0" selected>All</option>
                                <c:forEach var="category" items="${categoryList}">
                                    <option value="${category.categoryId}">${category.categoryName}</option>
                                </c:forEach> 
                            </select>
                        </div>

                        <div class="col-sm-4">
                            <p class="d-sm-inline-block">Price Select: </p> 
                            <input class="ml-3 mr-3 price-value" type="text" name="priceFrom" id="priceFrom">
                            <p class="d-sm-inline-block">-</p> 
                            <input class="ml-3 price-value" type="text" name="priceTo" id="priceTo">
                            <input type="hidden" name="oldMinPrice" value="${oldMinPrice == null ? minPrice : oldMinPrice}" />
                            <input type="hidden" name="oldMaxPrice" value="${oldMaxPrice == null ? maxPrice : oldMaxPrice}" />
                        </div>

                        <div class="col-sm-3">
                            <div id="price-slider" class="mt-2"></div>
                        </div>

                        <div class="col-sm-1">
                            <input class="btn btn-outline-dark" type="submit" value="Filter" name="btnAction" />
                            <input type="hidden" name="txtSearch" value="${searchValue}" />
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <!--End of filter-->

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
                                                <c:choose>
                                                    <c:when test="${isAdmin}">
                                                        <c:url var="update" value="DispatchController">
                                                            <c:param name="btnAction" value="Update"></c:param>
                                                            <c:param name="productId" value="${book.productsDTO.productId}"></c:param>
                                                        </c:url>
                                                        <a class="btn btn-outline-success update-admin" href="${update}">Update</a>
                                                        <a class="btn btn-outline-danger delete-admin" onclick="deleteBook('${book.productsDTO.productName}', '${book.productsDTO.productId}')">Delete</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:url var="addToCart" value="DispatchController">
                                                            <c:param name="btnAction" value="Add To Cart" />
                                                            <c:param name="productId" value="${book.productsDTO.productId}" />
                                                            <c:param name="pageBefore" value="Search" />
                                                            <c:param name="searchValue" value="${searchValue}" />
                                                        </c:url>
                                                        <a href="${addToCart}" class="search-buy btn btn-outline-success text-uppercase">Buy now</a>
                                                    </c:otherwise>
                                                </c:choose>
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

            <c:when test="${not empty err}">
                <div class="container pt-5">
                    <div class="row">
                        <div class="col-sm-12">
                            <h4 class="text-center text-uppercase" style="color: red;">${err.minValueNumberic}</h4>
                            <h4 class="text-center text-uppercase" style="color: red;">${err.maxValueNumberic}</h4>
                            <h4 class="text-center text-uppercase" style="color: red;">${err.categoryNumberic}</h4>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="container pt-5">
                    <div class="row">
                        <div class="col-sm-12">
                            <h4 class="text-center text-uppercase">No result...</h4>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>



        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    </body>

    <script>
                                                            $(function () {
                                                                $("#categoryId").val(${categoryId == null ? 0 : categoryId});

                                                                $("#price-slider").slider({
                                                                    range: true,
                                                                    min: ${oldMinPrice == null ? minPrice : oldMinPrice},
                                                                    max: ${oldMaxPrice == null ? maxPrice : oldMaxPrice},
                                                                    values: [${minPrice}, ${maxPrice}],
                                                                    slide: function (event, ui) {
                                                                        $("#priceFrom").val(ui.values[0]);
                                                                        $("#priceTo").val(ui.values[1]);
                                                                    }
                                                                });

                                                                $("#priceFrom").val($("#price-slider").slider("values", 0));
                                                                $("#priceTo").val($("#price-slider").slider("values", 1));

                                                                for (var i = 1; i <= ${fn:length(productList)}; i++) {
                                                                    $('#price-' + i).text($('#price-' + i).text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1.") + " đ");
                                                                }

                                                            });

        <c:if test="${isAdmin}">
                                                            function deleteBook(bookName, bookId) {
                                                                if (confirm("Are you sure you want to delete '" + bookName + "'?")) {
                                                                    window.location.href = "DispatchController?btnAction=Delete&productId=" + bookId;
                                                                }
                                                            }
                                                            ;
        </c:if>
    </script>

</html>
