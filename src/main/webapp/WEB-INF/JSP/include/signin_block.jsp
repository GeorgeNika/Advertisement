<div class="collapse" id="authorizationSpoiler">
    <div class="container">
        <div class="hidden" id="joinBlock">
            <form class="form-horizontal" role="form" id="joinForm">
                <div class="form-group">
                    <label for="inputJoinLogin" class="col-xs-2 col-xs-offset-1 control-label">Login</label>

                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputJoinLogin" placeholder="Login">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputJoinPassword" class="col-xs-2 col-xs-offset-1 control-label">Password</label>

                    <div class="col-xs-6">
                        <input type="password" class="form-control" id="inputJoinPassword" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputJoinConfirmPassword" class="col-xs-2 col-xs-offset-1 control-label">Confirm
                        password</label>

                    <div class="col-xs-6">
                        <input type="password" class="form-control" id="inputJoinConfirmPassword"
                               placeholder="Confirm password">
                    </div>
                </div>
                <div class="form-group">
                    <%--<input class="hidden" id="reCaptchaData" value="default value"/>--%>
                    <%--<div class="col-xs-offset-3 col-xs-2 g-recaptcha"--%>
                         <%--data-sitekey="${captchaPublic}"--%>
                         <%--data-callBack="reCaptchaSubmit">--%>
                    <%--</div>--%>
                </div>
                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-2">
                        <button type="submit" class="btn btn-default" id="joinSubmitButton">
                            Join <span class="fa fa-user-plus"></span>
                        </button>
                    </div>
                    <div class="col-xs-4">
                        <div class="pull-right">
                            <button type="button" class="btn btn-default" onclick="signInShow()">
                                Sign In <span class="fa fa-sign-in"></span>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div id="signInBlock">
            <form class="form-horizontal" role="form" id="signInForm">
                <div class="form-group">
                    <label for="inputLogin" class="col-xs-2 col-xs-offset-1 control-label">Login</label>

                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputLogin" placeholder="Login">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-xs-2 col-xs-offset-1 control-label">Password</label>

                    <div class="col-xs-6">
                        <input type="password" class="form-control" id="inputPassword" placeholder="Password">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-offset-3 col-xs-2">
                        <button type="submit" class="btn btn-default" id="signInSubmitButton">
                            Sign In <span class="fa fa-sign-in"></span>
                        </button>
                    </div>
                    <div class="col-xs-4">
                        <div class="pull-right">
                            <button type="button" class="btn btn-default" onclick="joinShow()">
                                Join <span class="fa fa-user-plus"></span>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
