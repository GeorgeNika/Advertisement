<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]><html lang="ru" class="lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]><html lang="ru" class="lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]><html lang="ru" class="lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="ru">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Advertisement</title>

    <link rel="shortcut icon" href="${context}/favicon.ico" type="image/x-icon">

    <link href="${context}/resources/css/fonts.css" rel="stylesheet">
    <link href="${context}/resources/css/main.css" rel="stylesheet">
    <link href="${context}/resources/css/media.css" rel="stylesheet">

    <!-- Libs -->
    <link href="${context}/resources/libs/bootstrap3/css/bootstrap.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="navbar navbar-header navbar-fixed-top">
    <div class="container-fluid">
        <%@ include file="include/top_menu.jsp" %>
    </div>
</div>
<%@ include file="include/signin_block.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-1 visible-lg side-col whole-height"></div>
        <div class="col-lg-10 col-md-12 col-sm-12 col-xs-12">
            <div class="container-fluid">
                <div class="row">
                    <div class="hidden-lg hidden-md hidden-sm hidden-xs whole-height" id="filter">
                        <%@ include file="include/left_side.jsp" %>
                    </div>
                    <div class="col-lg-10 col-md-10 col-sm-12 col-xs-12 whole-height" id="message">
                        <div class="container-fluid masonry" data-columns>

                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 hidden-sm hidden-xs whole-height">
                        <%@ include file="include/right_side.jsp" %>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-1 visible-lg side-col  whole-height"></div>
    </div>
</div>
<%@ include file="include/pagination_block.jsp" %>
<%@ include file="include/modal_window.jsp" %>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${context}/resources/libs/bootstrap3/js/bootstrap.js"></script>
<script src="${context}/resources/libs/salvattore/salvattore.js"></script>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script src="${context}/resources/js/common.js"></script>
<script src="${context}/resources/js/main.js"></script>

<script>
    $(document).ready(function () {
        global_context = '${context}';
        global_ok_response = '${okResponse}';
        advertisement('${context}', '${sessionScope.get('user')}', ${sessionScope.get('isUserSignIn')});
    });
</script>
</body>
</html>