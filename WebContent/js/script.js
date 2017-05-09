$(document).ready(function () {
    $('.modal').modal({
        dismissible: true, // Modal can be dismissed by clicking outside of the modal
        opacity: .5,
        complete: function() {
            $(this).find('.preloader-wrapper').addClass('hide');
            $('#userEditTemplate').find('select').material_select('destroy');
        },
        ready: function () {
        }
    });

    populate();
    
    $(document).on("click", ".edit-user", function (e) {
        var row = $(this).parent().parent();
        template = $('#userEditTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {
            "oprId": row.children(".id").text(),
            "oprName":row.children(".name").text(),
            "ini": row.children(".ini").text(),
            "cpr": row.children(".cpr").text(),
            "admin": row.children().find(".admin").is(':checked'),
            "role": row.children(".role").text()
        });
        console.log({
            "oprId": row.children(".id").text(),
            "oprName":row.children(".name").text(),
            "ini": row.children(".ini").text(),
            "cpr": row.children(".cpr").text(),
            "admin": row.children().find(".admin").is(':checked'),
            "role": row.children(".role").text()
        });
        $('#userEdit').html(rendered).promise().done(function () {
            var select_box = $('#userEditModal').find('#user_role');
            var option = 'option[value='+select_box.attr('data-selected')+']';
            select_box.find(option).attr("selected", true);
            console.log(option);
            $('select').material_select();
            $('#userEditModal').modal('open');
        });
    });

    $(document).on("click", '.add-user', function (e) {
        template = $('#userEditTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {"role": "None", "admin": false});
        $('#userEdit').html(rendered).promise().done(function () {
            var select_box = $('#userEditModal').find('#user_role');
            var option = 'option[value='+select_box.attr('data-selected')+']';
            select_box.find(option).attr("selected", true);
            console.log(option);
            $('select').material_select();
        });
        $('#userEditModal').modal('open');
        Materialize.updateTextFields();
    });

    $(document).on("click", '.delete-user', function (e) {
        var row = $(this).parent().parent();
        $.ajax({
            type: "DELETE",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({}),
            url: "./api/v1/operator/"+row.children(".id").text(),
            success: function( response ) {
                if(response){
                    Materialize.toast("User with ID: "+ row.children(".id").text() +" was deleted!", 4000);
                    row.fadeOut("slow", function () {
                        row.remove();
                    });
                }else{
                    Materialize.toast("Unable to delete user!", 4000);
                }
            },
            error: function ( msg ) {
                Materialize.toast("Unable to delete user!", 4000);
            }
        });
    });
    
    $(document).on('click', '.dropdown-item', function (e) {
        console.log($(this).text());
        $(this).parent().parent().parent().find('a.dropdown-button').html($(this).text());
    });

    $('#loginForm').on('submit', function (e) {
        e.preventDefault();
        $.ajax({
            type: "POST",
            data: JSON.stringify({
                "oprId": 1,
                "oprName": "Viktor Poulsen",
                "ini": "vmp",
                "cpr": "0101892077",
                "password": "qweqwe",
                "admin": 1,
                "role": "pharmacist"
            }),
            processData: false,
            contentType: "application/json",
            url: "./api/v1/login/",
            success: function( msg ) {
                console.log("Response: " + msg);
                $('#loginForm').hide();
                $('main').removeClass('valign-wrapper');
                $('#userAdministration').show();
            },
            error: function ( msg ) {
                Materialize.toast("Invalid login!", 4000);
                console.log(msg);
            }
        });
    });

    $(document).on('click', '.modal-save', function (e) {
        e.preventDefault();
        $('#userEditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            processData: false,
            data: JSON.stringify({
                "oprId": $('#userEdit').find("#user_id").val(),
                "oprName": $('#userEdit').find("#user_name").val(),
                "ini": $('#userEdit').find("#user_ini").val(),
                "cpr": $('#userEdit').find("#user_cpr").val(),
                "admin": $('#userEdit').find().find(".admin").is(':checked'),
                "role": $('#userEdit').find("#user_role").text()
            }),
            url: "./api/v1/operator/",
            success: function( msg ) {
                console.log("Response: " + msg);
                populate();
                $('#usereditmodal').modal('close');
            },
            error: function ( msg ) {
                Materialize.toast("Unable to update user!", 4000);
                $('#userEditModal').modal('close');
            }
        });
    });

    $('.logout').on('click', function (e) {
        e.preventDefault();
        $('#userAdministration').hide();
        $('#loginForm').show();
        $('main').addClass('valign-wrapper');
    })
});

function populate() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        processData: false,
        url: "./api/v1/operator/",
        success: function( response ) {
            console.log(response);
            var template = $('#userTableRow').html();
            var data = {};
            data['users'] = response;
            Mustache.parse(template);   // optional, speeds up future uses
            var rendered = Mustache.render(template, data);

            $('#userListRows').html(rendered).promise().done(function () {
                $("input:checkbox:not(:checked)").each(function () {
                    $(this).prop("indeterminate", true);
                });
            });
        },
        error: function ( msg ) {
            console.log(msg);
            Materialize.toast("Unable to delete user!", 4000);
        }
    });
}