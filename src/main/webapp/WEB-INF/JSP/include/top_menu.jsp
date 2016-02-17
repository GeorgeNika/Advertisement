<div class="col-xs-5">
    <div class="navbar-header pull-left">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#work_menu">
            <span class="sr-only">filter</span>
            <span class="fa fa-filter fa-2x"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse navbar-left" id="work_menu">
        <ul class="nav navbar-nav">
            <li>
                <button class="btn btn-default" id="filterShowBtn" onclick="filterShow()">
                    Filter Show <span class="fa fa-filter"></span><span class="fa fa-plus"></span>
                </button>
            </li>
        </ul>
        <ul class="nav navbar-nav">
            <li>
                <button class="btn btn-default hidden" id="filterHideBtn" onclick="filterHide()">
                    Filter Hide <span class="fa fa-filter"></span><span class="fa fa-minus"></span>
                </button>
            </li>
        </ul>
    </div>
</div>
<div class="col-xs-2">
    <button class="btn btn-success hidden" type="button" id="addMessageButton" onclick="addNewMessage()">
        Add Message
    </button>
</div>
<div class="col-xs-5">
    <div class="navbar-header navbar-right">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#signInMenu">
            <span class="sr-only">sign in</span>
            <span class="fa fa-user fa-2x"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse pull-right" id="signInMenu">
        <ul class="nav navbar-nav">
            <li>
                <button class="btn btn-default" data-toggle="collapse" id="signInButton" href="#authorizationSpoiler">
                    Sign In <span class="fa fa-sign-in"></span> / Join <span class="fa fa-user-plus"> </span>
                </button>
            </li>
            <li>
                <button class="btn btn-default disabled hidden" id="accountNameButton">

                </button>
            </li>
            <li>
                <button class="btn btn-default hidden" id="signOutButton">
                    Sign Out <span class="fa fa-sign-out"></span>
                </button>
            </li>
        </ul>
    </div>
</div>
