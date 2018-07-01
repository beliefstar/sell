<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sell - Register</title>
    <link rel="stylesheet" href="/sell/login/css/reset.css" />
    <link rel="stylesheet" href="/sell/login/css/style.css" media="screen" type="text/css" />
</head>
<body>
<div id="window" style="">
    <div class="page page-front">
        <div class="page-content">
            <div class="input-row">
                <h3>注册</h3>
            </div>
            <div class="input-row">
                <label class="label fadeIn">用户名</label>
                <input id="username" type="text" data-fyll="用户名" class="input fadeIn delay1" />
            </div>
            <div class="input-row">
                <label class="label fadeIn delay2">密码</label>
                <input id="password" type="password" data-fyll="密码" class="input fadeIn delay3" />
            </div>
            <div class="input-row perspective">
                <button id="submit" class="button load-btn fadeIn delay4"> <span class="default"><i class="ion-arrow-right-b"></i>登录</span>
                    <div class="load-state">
                        <div class="ball"></div>
                        <div class="ball"></div>
                        <div class="ball"></div>
                    </div> </button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/sell/login/js/jquery.js"></script>
<script type="text/javascript" src="/sell/login/js/fyll.js"></script>
<script type="text/javascript" src="/sell/js/message-box.js"></script>
<script>
    jQuery(document).ready(function($) {

        // If firefox
        if(navigator.userAgent.toLowerCase().match(/firefox/)) {
            $('.browser-warning').removeClass('hidden');
            setTimeout(function() {
                $('.browser-warning').addClass('hidden');
            }, 6*1000);
        }

        $('#window').attr('style', '');

        $('#submit').click(function () {
            var username = $("#username").val();
            var pwd = $("#password").val();

            if (username.trim() === "" || pwd.trim() === "") {
                MessageBox.tip("帐号或密码为空");
                return;
            }

            $('#submit').addClass('loading');
            $.ajax({
                url: "/sell/seller/info/register",
                type: "POST",
                dataType: "json",
                data: {
                    username: username,
                    password: pwd
                },
                success: function (res) {
                    setTimeout(function () {
                        $('#submit').addClass('done').closest('#window').addClass('flip');
                        if (res.data === "ok") {
                            location.href = "${appServer}/sell/seller/info/login";
                        } else {
                            MessageBox.alert("操作出错," + res.msg, "提示");
                        }
                    }, 1000);
                }
            });
        });
    });
</script>
</body>
</html>