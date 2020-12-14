<%-- 
    Document   : updateBook
    Created on : Dec 11, 2020, 9:05:38 PM
    Author     : Tin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book - Book Store</title>

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
        <c:set var="isNew" value="${requestScope.IS_NEW}" />
        <c:set var="product" value="${requestScope.PRODUCT}" />
        <c:set var="categoryList" value="${requestScope.CATEGORY_LIST}" />

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
            <c:choose>
                <c:when test="${isNew}">
                    <h4 class="book-title text-center text-uppercase pb-5">create new book</h4>
                </c:when>
                <c:otherwise>
                    <h4 class="book-title text-center text-uppercase pb-5">update book</h4>
                </c:otherwise>
            </c:choose>
        </div>
        <!--End of title-->

        <!--Start of Update section-->
        <form action="DispatchController" method="POST" enctype="multipart/form-data">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label>Product Name</label>
                            <input type="text" class="form-control" name="productName" value="${product.productName}" required>
                            <c:if test="${not empty err.emptyProductName}">
                                <p class="update-validation-error">
                                    ${err.emptyProductName}
                                </p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Category</label>
                            <select class="form-control" name="categoryId" id="categoryId" required>
                                <c:forEach var="category" items="${categoryList}">
                                    <option value="${category.categoryId}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${not empty err.emptyCategoryId}">
                                <p class="update-validation-error">
                                    ${err.emptyCategoryId}
                                </p>
                            </c:if>
                            <c:if test="${not empty err.notValidCategoryId}">
                                <p class="update-validation-error">
                                    ${err.notValidCategoryId}
                                </p>
                            </c:if>
                        </div>
                        <div class="custom-file">
                            <c:choose>
                                <c:when test="${isNew}">
                                    <input type="file" name="image" accept="image/*" class="custom-file-input" id="customFile" required>
                                </c:when>

                                <c:otherwise>
                                    <input type="file" name="image" accept="image/*" class="custom-file-input" id="customFile">
                                </c:otherwise>
                            </c:choose>
                            <label class="custom-file-label" for="customFile">Picture...</label>
                            <c:if test="${not empty err.emptyImage}">
                                <p class="update-validation-error">
                                    ${err.emptyImage}
                                </p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Description</label>
                            <textarea class="form-control" rows="6" name="description" required>${product.description}</textarea>
                            <c:if test="${not empty err.emptyDescription}">
                                <p class="update-validation-error">
                                    ${err.emptyDescription}
                                </p>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label>Price</label>
                            <input type="number" class="form-control" name="price" value="${product.price}"required>
                            <c:if test="${not empty err.emptyPrice}">
                                <p class="update-validation-error">
                                    ${err.emptyPrice}
                                </p>
                            </c:if>
                            <c:if test="${not empty err.notValidPrice}">
                                <p class="update-validation-error">
                                    ${err.notValidPrice}
                                </p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Author</label>
                            <input type="text" class="form-control" name="author" value="${product.author}" required>
                            <c:if test="${not empty err.emptyAuthor}">
                                <p class="update-validation-error">
                                    ${err.emptyAuthor}
                                </p>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Quantity</label>
                            <input type="number" class="form-control" name="quantity" value="${product.quantity}" required>
                            <c:if test="${not empty err.emptyQuantity}">
                                <p class="update-validation-error">
                                    ${err.emptyQuantity}
                                </p>
                            </c:if>
                            <c:if test="${not empty err.notValidQuantity}">
                                <p class="update-validation-error">
                                    ${err.notValidQuantity}
                                </p>
                            </c:if>
                        </div>
                        <c:choose>
                            <c:when test="${isNew}">
                                <c:if test="${not empty err.failToAdd}">
                                    <p color="red">${err.failToAdd}</p>
                                </c:if>
                                <input class="btn btn-outline-success float-right" type="submit" value="Create Book" name="btnAction" />

                            </c:when>
                            <c:otherwise>
                                <div class="form-group">
                                    <label>Created At</label>
                                    <input type="date" class="form-control" name="createdAt" value="${product.createdAt}" required>
                                    <c:if test="${not empty err.notValidQuantity}">
                                        <p class="update-validation-error">
                                            ${err.notValidQuantity}
                                        </p>
                                    </c:if>
                                </div>

                                <input type="hidden" name="productId" value="${product.productId}" />
                                <input class="btn btn-outline-success float-right" type="submit" value="Update Book" name="btnAction" />
                            </c:otherwise>
                        </c:choose>
                    </div>
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
