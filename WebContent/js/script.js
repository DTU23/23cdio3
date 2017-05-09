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
    var template = $('#userTableRow').html();
    var data = {
        "users":[
            {"id": 1, "name": "Viktor", "ini": "VP", "cpr": "234567890", "admin": false, "role": "Pharmacist"},
            {"id": 2, "name": "Christian", "ini": "CN", "cpr": "12345676543", "admin": false, "role": "Operator"},
            {"id": 3, "name": "Frederik", "ini": "FV", "cpr": "932845893245", "admin": false, "role": "Foreman"},
            {"id": 4, "name": "Mads", "ini": "MP", "cpr": "34253452241", "admin": true, "role": "None"}
        ]
    };
    Mustache.parse(template);   // optional, speeds up future uses
    var rendered = Mustache.render(template, data);
    $('#userListRows').html(rendered).promise().done(function () {
        $("input:checkbox:not(:checked)").each(function () {
            $(this).prop("indeterminate", true);
        });
    });
    
    $('.edit-user').on("click", function (e) {
        var row = $(this).parent().parent();
        template = $('#userEditTemplate').html();
        Mustache.parse(template);   // optional, speeds up future uses
        var rendered = Mustache.render(template, {
            "id": row.children(".id").text(),
            "name":row.children(".name").text(),
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
        });
        $('#userEditModal').modal('open');
    });

    $('.add-user').on("click", function (e) {
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

    $('.delete-user').on("click", function (e) {
        var row = $(this).parent().parent();
        $.ajax({
            type: "DELETE",
            url: "./api/v1/operator/"+row.children(".id").text(),
            success: function( msg ) {
                console.log("Response: " + msg);
                row.fadeOut("slow", function () {
                    row.remove();
                });
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
        $('#loginForm').hide();
        $('main').removeClass('valign-wrapper');
        $('#userAdministration').show();
    });

    $(document).on('click', '.modal-save', function (e) {
        e.preventDefault();
        $('#userEditModal').find('.preloader-wrapper').removeClass('hide');
        $.ajax({
            type: "POST",
            data: {
                "id": $('#userEdit').find("#user_id").val(),
                "name": $('#userEdit').find("#user_name").val(),
                "ini": $('#userEdit').find("#user_ini").val(),
                "cpr": $('#userEdit').find("#user_cpr").val(),
                "admin": $('#userEdit').find().find(".admin").is(':checked'),
                "role": $('#userEdit').find("#user_role").text()
            },
            url: "./api/v1/operator/",
            success: function( msg ) {
                console.log("Response: " + msg);
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