<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    注意!
                </h4> <strong>${message}</strong> <a href="#" class="alert-link"><span id="se">3</span>秒后自动跳转</a>
            </div>
        </div>
    </div>
</div>
<script>

    var se = 3;
    var seElem = document.getElementById("se");

    var timer = setInterval(function () {
        se--;
        seElem.innerHTML = se;
        if (se <= 0) {
            window.location.href = "${url}";
        }
    }, 1000);

</script>
</body>
</html>