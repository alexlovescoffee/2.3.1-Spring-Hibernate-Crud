$(document).ready(function() {
    $('#add_form').submit(function (e) {
        e.preventDefault();
        $.post("/users/add", {
            name: $("#add_name").val(),
            age: $("#add_age").val(),
            'address.city': $("#add_city").val(),
            'address.street': $("#add_street").val(),
            'address.building': $("#add_building").val()
        }).done(function() {
            reloadUsersTable();
        });
    });

    $('#update_form').submit(function (e) {
        e.preventDefault();
        $.post("/users/update", {
            id: $("#update_id").val(),
            name: $("#update_name").val(),
            age: $("#update_age").val(),
            'address.city': $("#update_city").val(),
            'address.street': $("#update_street").val(),
            'address.building': $("#update_building").val()
        }).done(function() {
            reloadUsersTable();
        });
    });

    $('#delete_form').submit(function (e) {
        e.preventDefault();
        $.post("/users/delete?id=" + $("#delete_id").val()).
        done(function() {
            reloadUsersTable();
        });
    });
});

function reloadUsersTable() {
    $.get("/users/get").done(function(data) {
        let innerBody = '';
        data.forEach(function (user, i) {
            innerBody +=
                '<tr>' +
                    '<td>' + user.id + '</td>' +
                    '<td>' + user.name + '</td>' +
                    '<td>' + user.age + '</td>' +
                    '<td>' + user.city + '</td>' +
                    '<td>' + user.street + '</td>' +
                    '<td>' + user.building + '</td>' +
                '</tr>'
        });
        document.querySelector('.users-table tbody').innerHTML = innerBody;
    });
}