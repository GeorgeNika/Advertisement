/**
 * Created by george on 17.02.2016.
 */
function advertisement(global_context, global_user, global_is_signin) {
    var app = {

        initialize: function () {
            this.setUpListeners();
            if (global_is_signin) {
                this.signInSuccess(global_user);
            }
            getMessagePage('start');
        },

        setUpListeners: function () {
            $('#signInForm').on('submit', app.submitSignInForm);
            $('#joinForm').on('submit', app.submitJoinForm);
            $('#filterForm').on('submit', app.submitFilterForm);
            $('#messageForm').on('submit', app.saveMessage);
            $('form').on('keydown', 'input, textarea', app.removeError);

            $('#signOutButton').click(app.signOut);
            $('#deleteMessage').click(app.deleteMessage);
            $('#closeModalTopButton').click(app.closeModalWindow);
            $('#closeModalBottomButton').click(app.closeModalWindow);

            $('#filterShowBtn').click(app.filterShow);
        },

        submitSignInForm: function (e) {
            var form = $(this);
            e.preventDefault();
            if (app.validateForm(form, true) === false) {
                return false;
            }
            var login = $("#inputLogin").val();
            $("#signInSubmitButton").attr('disabled', 'disabled');

            $.ajax({
                url: global_context+'/ajax/signInAction',
                type: 'POST',
                datatype: 'json',
                data: {login: login, password: $("#inputPassword").val()},
                success: function (response) {
                    if (response === global_ok_response) {
                        $("#signInSubmitButton").removeAttr('disabled');
                        app.signInSuccess(login);
                    } else {
                        $("#signInSubmitButton").tooltip({
                            trigger: 'manual',
                            placement: 'top',
                            title: response.substring(0, 100)
                        }).tooltip('show');
                        setTimeout(app.clearSignInToolTip, 3000);
                    }
                }
            });
        },
        submitJoinForm: function (e) {
            var form = $(this);
            e.preventDefault();
            if (app.validateForm(form, true) === false) {
                return false;
            }

            if ($('#inputJoinPassword').val() != $('#inputJoinConfirmPassword').val()) {
                $('#inputJoinConfirmPassword').parents('.form-group').addClass('has-error').removeClass('has-success');
                $('#inputJoinConfirmPassword').tooltip({
                    trigger: 'manual',
                    placement: 'bottom',
                    title: 'passwords must be equal'
                }).tooltip('show');
                return false;
            }


            var login = $("#inputJoinLogin").val();
            $("#joinSubmitButton").attr('disabled', 'disabled');

            $.ajax({
                url: global_context+'/ajax/joinAction',
                type: 'POST',
                datatype: 'json',
                data: {
                    login: login, password: $("#inputJoinPassword").val(),
                    reCaptchaData: $("#reCaptchaData").val()
                },
                success: function (response) {
                    if (response === global_ok_response) {
                        $("#joinSubmitButton").removeAttr('disabled');
                        app.joinSuccess();
                        app.signInSuccess(login);
                    } else {
                        $("#joinSubmitButton").tooltip({
                            trigger: 'manual',
                            placement: 'top',
                            title: response.substring(0, 100)
                        }).tooltip('show');
                        setTimeout(app.clearJoinToolTip, 3000);
                    }
                }
            });
        },
        submitFilterForm: function (e) {
            var form = $(this);
            e.preventDefault();

            if (app.validateForm(form, false) === false) {
                return false;
            }

            var checkedCategory = $("#categoryCheckBoxGroup input[type = checkbox]:checked").map(function () {
                return this.value;
            }).get();

            $.ajax({
                url: global_context+'/ajax/setFilter',
                type: 'POST',
                datatype: 'json',
                data: {
                    onlyMyMessage: $("#onlyMyMessageCheckBox").is(':checked'),
                    authorName: $("#inputAuthor").val(),
                    partOfTitle: $("#inputTitle").val(),
                    partOfMessage: $("#inputMessage").val(),
                    checkedCategory: checkedCategory.toString()
                },
                success: function (response) {
                    if (response === global_ok_response) {
                        $('#inputTitle').parents('.form-group').removeClass('has-success');
                        $('#inputMessage').parents('.form-group').removeClass('has-success');
                        $('#inputAuthor').parents('.form-group').removeClass('has-success');
                        getMessagePage('start');
                    } else {
                        $("#filterSubmitButton").tooltip({
                            trigger: 'manual',
                            placement: 'top',
                            title: response.substring(0, 100)
                        }).tooltip('show');
                        setTimeout(app.clearFilterToolTip, 3000);
                    }
                }
            });
        },

        saveMessage: function (e) {
            var form = $(this);
            e.preventDefault();

            var input = $('#messageTitle'),
                valid = true;
            var formGroup = input.parents('.form-group');
            if ((input.val().length < 10) || (input.val().length > 30)) {
                formGroup.addClass('has-error').removeClass('has-success');
                input.tooltip({
                    trigger: 'manual',
                    placement: 'bottom',
                    title: 'title size must be between 10 and 30 signs'
                }).tooltip('show');
                valid = false;
            } else {
                formGroup.addClass('has-success').removeClass('has-error');
            }

            input = $('#messageBody');
            formGroup = input.parents('.form-group');
            if ((input.val().length < 20) || (input.val().length > 400)) {
                formGroup.addClass('has-error').removeClass('has-success');
                input.tooltip({
                    trigger: 'manual',
                    placement: 'bottom',
                    title: 'title size must be between 20 and 400 signs'
                }).tooltip('show');
                valid = false;
            } else {
                formGroup.addClass('has-success').removeClass('has-error');
            }

            if (!valid) {
                return false;
            }

            $("#saveMessage").attr('disabled', 'disabled');

            $.ajax({
                url: global_context+'/ajax/saveMessage',
                type: 'POST',
                datatype: 'json',
                data: {
                    idMessage: $('#messageIdMessage').html(),
                    category: $('#messageCategory').val(),
                    title: $('#messageTitle').val(),
                    messageBody: $('#messageBody').val()
                },
                success: function (response) {
                    if (response === global_ok_response) {
                        $("#saveMessage").removeAttr('disabled');
                        getMessagePage('start');
                        app.closeModalWindow();
                    } else {
                        $("#saveMessage").tooltip({
                            trigger: 'manual',
                            placement: 'top',
                            title: response.substring(0, 100)
                        }).tooltip('show');
                        setTimeout(app.clearSaveToolTip, 3000);
                    }
                }
            });
        },
        deleteMessage: function () {
            $("#deleteMessage").attr('disabled', 'disabled');
            $.ajax({
                url: global_context+'/ajax/deleteMessage',
                type: 'POST',
                datatype: 'json',
                data: {idMessage: $('#messageIdMessage').html()},
                success: function (response) {
                    if (response === global_ok_response) {
                        $("#deleteMessage").removeAttr('disabled');
                        getMessagePage('start');
                        app.closeModalWindow();
                    } else {
                        $("#deleteMessage").tooltip({
                            trigger: 'manual',
                            placement: 'top',
                            title: response.substring(0, 100)
                        }).tooltip('show');
                        setTimeout(app.clearDeleteToolTip, 3000);
                    }
                }
            });
        },

        signOut: function () {
            $.ajax({
                url: global_context+'/ajax/signOutAction',
                type: 'POST',
                datatype: 'json',
                data: {},
                success: function (response) {
                    if (response === global_ok_response) {
                        app.signOutSuccess();
                    } else {
                        $("#signOutButton").tooltip({
                            trigger: 'manual',
                            placement: 'buttom',
                            title: response.substring(0, 40)
                        }).tooltip('show');
                        setTimeout(app.clearSignOutToolTip, 3000);
                    }
                }
            });
        },

        validateForm: function (form, required) {
            var inputs = form.find("input:text, input:password, textarea"),
                valid = true;
            $.each(inputs, function (index, val) {
                    var input = $(val),
                        value = input.val(),
                        formGroup = input.parents('.form-group'),
                        label = formGroup.find('label').text().toLowerCase(),
                        textError = label + ' must be more then 3 signs';

                    var validValue = true;
                    if ((value.length <= 3)) {
                        validValue = false;
                    }
                    if ((value.trim().length == 0) && (!required)) {
                        validValue = true;
                    }

                    if (!validValue) {
                        formGroup.addClass('has-error').removeClass('has-success');
                        input.tooltip({
                            trigger: 'manual',
                            placement: 'bottom',
                            title: textError
                        }).tooltip('show');
                        valid = false;
                    } else {
                        formGroup.addClass('has-success').removeClass('has-error');
                    }
                }
            );
            return valid;
        },

        removeError: function () {
            $(this).tooltip('destroy').parents('.form-group').removeClass('has-error');
        },

        clearSignInToolTip: function () {
            $('#signInSubmitButton').tooltip('destroy').removeAttr('disabled');
        },

        clearSignOutToolTip: function () {
            $('#signOutButton').tooltip('destroy');
        },

        clearJoinToolTip: function () {
            $('#joinSubmitButton').tooltip('destroy').removeAttr('disabled');
        },

        clearFilterToolTip: function () {
            $('#filterSubmitButton').tooltip('destroy');
        },

        clearSaveToolTip: function () {
            $('#saveMessage').tooltip('destroy').removeAttr('disabled');
        },

        clearDeleteToolTip: function () {
            $('#deleteMessage').tooltip('destroy').removeAttr('disabled');
        },

        joinSuccess: function () {
            $('#inputJoinLogin').val('').parents('.form-group').removeClass('has-success');
            $('#inputJoinPassword').val('').parents('.form-group').removeClass('has-success');
            $('#inputJoinConfirmPassword').val('').parents('.form-group').removeClass('has-success');
            grecaptcha.reset();
        },

        signInSuccess: function (login) {
            $('#inputLogin').val('').parents('.form-group').removeClass('has-success');
            $('#inputPassword').val('').parents('.form-group').removeClass('has-success');
            $('#authorizationSpoiler').removeClass('in');
            $('#signInButton').addClass('hidden');
            $('#signOutButton').removeClass('hidden');
            $('#accountNameButton').removeClass('hidden').html(login);
            $('#onlyMyMessageCheckBox').prop("disabled", false);
            $('#addMessageButton').removeClass('hidden');
        },

        signOutSuccess: function () {
            $('#signInButton').removeClass('hidden');
            $('#signOutButton').addClass('hidden');
            $('#accountNameButton').addClass('hidden').html("");
            $('#onlyMyMessageCheckBox').prop("disabled", true).prop("checked", false);
            $("#inputAuthor").prop('disabled', false);
            $('#addMessageButton').addClass('hidden');
        },

        closeModalWindow: function () {
            $('#modalMessage').modal('hide');

            $('#messageIdMessage').html();
            $('#messageTitle').tooltip('destroy').val();
            $('#messageTitle').parents('.form-group').removeClass('has-success').removeClass('has-error');
            $('#messageAuthor').html();
            $('#messageCategory').val();
            $('#messageCreated').html();
            $('#messageUpdated').html();
            $('#messageBody').tooltip('destroy').val();
            $('#messageBody').parents('.form-group').removeClass('has-success').removeClass('has-error');
        }

    }
    app.initialize();

}