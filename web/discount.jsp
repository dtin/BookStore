<%-- 
    Document   : discount
    Created on : Dec 12, 2020, 1:29:19 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Discount - Book Store</title>

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
            <h4 class="book-title text-center text-uppercase pb-5">Create discount</h4>
        </div>
        <!--End of title-->

        <!--Start of Update section-->
        <form action="DispatchController" method="POST" enctype="multipart/form-data">
            <div class="container">
                <div class="row">
                    <div class="col-sm-3"></div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label>Discount code</label>
                            <input type="text" class="form-control" name="discountCode" value="" required>
                            <c:if test="${not empty err.outOfRangeDiscount}">
                                <p class="update-validation-error">
                                    ${err.outOfRangeDiscount}
                                </p>
                            </c:if>

                            <c:if test="${not empty err.duplicateDiscount}">
                                <p class="update-validation-error">
                                    ${err.duplicateDiscount}
                                </p>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label>Discount Percent (%)</label>
                            <input type="number" class="form-control" name="percent" value="" required>
                            <c:if test="${not empty err.outOfRangePercent}">
                                <p class="update-validation-error">
                                    ${err.outOfRangePercent}
                                </p>
                            </c:if>
                            <c:if test="${not empty err.notValidPercent}">
                                <p class="update-validation-error">
                                    ${err.notValidPercent}
                                </p>
                            </c:if>
                        </div>

                        <input class="btn btn-outline-success float-right" type="submit" value="Add Discount" name="btnAction" />

                    </div>
                    <div class="col-sm-3"></div>
                </div>
            </div>
        </form>
        <!--End of Update section-->

        <!--Bootstrap JS-->
        <script src="assets/js/jquery-3.5.1.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/fontawesome.min.js"></script>
        <script>
            $(function () {
                $("#categoryId").val(${product.categoryId != null ? product.categoryId : ""});
            });
        </script>
    </body>
</html>