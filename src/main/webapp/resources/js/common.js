/**
 * Created by george on 17.02.2016.
 */
function filterShow() {
    $("#filter").removeClass('hidden-lg').removeClass('hidden-md').removeClass('hidden-sm').removeClass('hidden-xs');
    $("#filter").addClass('col-lg-3').addClass('col-md-3').addClass('col-sm-4').addClass('col-xs-6');

    $("#message").removeClass('col-lg-10').removeClass('col-md-10').removeClass('col-sm-12').removeClass('col-xs-12');
    $("#message").addClass('col-lg-7').addClass('col-md-7').addClass('col-sm-8').addClass('col-xs-6');

    $("#filterShowBtn").addClass("hidden");
    $("#filterHideBtn").removeClass("hidden");

}
function filterHide() {
    $("#filter").removeClass('col-lg-3').removeClass('col-md-3').removeClass('col-sm-4').removeClass('col-xs-6');
    $("#filter").addClass('hidden-lg').addClass('hidden-md').addClass('hidden-sm').addClass('hidden-xs');

    $("#message").removeClass('col-lg-7').removeClass('col-md-7').removeClass('col-sm-8').removeClass('col-xs-6');
    $("#message").addClass('col-lg-10').addClass('col-md-10').addClass('col-sm-12').addClass('col-xs-12');

    $("#filterShowBtn").removeClass("hidden");
    $("#filterHideBtn").addClass("hidden");
}
function joinShow() {
    $("#joinBlock").removeClass("hidden");
    $("#signInBlock").addClass("hidden");
}
function signInShow() {
    $("#signInBlock").removeClass("hidden");
    $("#joinBlock").addClass("hidden");
}
function reCaptchaSubmit(dat) {
    $("#reCaptchaData").val(dat);
}

function onlyMyMessageClick(data) {
    if ($("#onlyMyMessageCheckBox").is(':checked')) {
        $("#inputAuthor").prop('disabled', true).val($('#accountNameButton').html());
    } else {
        $("#inputAuthor").prop('disabled', false).val('');
    }
}

function addNewMessage() {
    var newMessage = {
        title: "", authorName: $('#accountNameButton').html(), idCategory: 1,
        created: "", updated: "", message: "", idMessage: 0
    };
    showModalWindow(newMessage);
}


function showModalWindow(data) {
    if (data.authorName == $('#accountNameButton').html()) {
        $('#saveMessage').removeClass('hidden');
        if (data.idMessage == 0) {
            $('#deleteMessage').addClass('hidden');
        }else{
            $('#deleteMessage').removeClass('hidden');
        }
        $("#messageTitle").removeAttr('disabled');
        $("#messageCategory").removeAttr('disabled');
        $("#messageBody").removeAttr('disabled');

    } else {
        $('#saveMessage').addClass('hidden');
        $('#deleteMessage').addClass('hidden');
        $("#messageTitle").attr('disabled', 'disabled');
        $("#messageCategory").attr('disabled', 'disabled');
        $("#messageBody").attr('disabled', 'disabled');
    }
    $('#messageIdMessage').html(data.idMessage);
    $('#messageTitle').val(data.title);
    $('#messageAuthor').html('Author : ' + data.authorName);
    $('#messageCategory').val(data.idCategory);
    $('#messageCreated').html('Created : ' + data.created);
    $('#messageUpdated').html('Updated : ' + data.updated);
    $('#messageBody').val(data.message);

    $('#modalMessage').modal('show');
}

function obtainMessage(data) {
    var tempId = $(data.target).parents('.board').attr('id');
    if (tempId == null) {
        tempId = $(data.target).attr('id');
    }
    var searchId = tempId.substring(3, tempId.length);
    $.ajax({
        url: global_context+'/ajax/getMessage',
        type: 'POST',
        datatype: 'json',
        data: {idMessage: searchId},
        success: function (response) {
            showModalWindow(response);
        }
    });
}

function useObtainedMessageData(data) {
    $('#currentMessagePage').html(data.page);
    var messages = data.ajaxMessageList;
    var children = $('.masonry').find('.board');
    $.each(children, function (index, val) {
        val.parentNode.removeChild(val);
    });

    var grid = document.querySelector('.masonry');
    var idMessage;
    for (var ind in messages) {
        var item = document.createElement('article');
        salvattore.appendElements(grid, [item]);
        idMessage = messages[ind].idMessage;
        var el = "<div class='board " + getRandomBoard() + "' id='idM" + idMessage + "'>" +
            "<div class='board_header'> <h4>" + messages[ind].title + "</h4> </div>" +
            "<div class='board_info'>" + messages[ind].categoryName + "</div>" +
            "<div class='board_info'>" + messages[ind].authorName + " : " + messages[ind].updated + "</div>" +
            "<div class='board_message'> <h6>" + messages[ind].message + "</h6> </div>" +
            "</div>";
        item.outerHTML = el;
        $(document).off("click", "#idM" + idMessage);
        $(document).on("click", "#idM" + idMessage, obtainMessage);
    }
}

function getRandomBoard() {
    var type = Math.floor(Math.random() * 4) + 1;
    if (type == 1) {
        return "board_blue";
    }
    if (type == 2) {
        return "board_sky";
    }
    if (type == 3) {
        return "board_green";
    }
    if (type == 4) {
        return "board_grey";
    }
    return "board_blue";
}

function getMessagePage(sendData) {
    $.ajax({
        url: global_context+'/ajax/getMessagePage',
        type: 'POST',
        datatype: 'json',
        data: {pageDirection: sendData},
        success: function (response) {
            useObtainedMessageData(response);
        }
    });
}
