<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>Advertisement</title>

  <link rel="shortcut icon" href="${context}/favicon.ico" type="image/x-icon">

  <link href="${context}/resources/css/fonts.css" rel="stylesheet">
  <link href="${context}/resources/css/main.css" rel="stylesheet">
</head>
<body>
<div class="whole-height">
  <div >
    <img src="${context}/resources/images/button_error.png" alt="error" class="round_button"
         onclick="window.location.href='${context}/advertisement'">
  </div>
  <div class="space10_div"></div>
  <div class="space10_div">
    ${errorMessage}
  </div>
</div>
</body>
</html>
