const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function enable(elem, id) {
    alert('yes');
    const enabled = elem.is(":checked");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: 'enabled=' + enabled
    }).done(function () {
        elem.closest('tr').attr('user-enabled', enabled);
        updateTable();
        successNoty( enabled ? "Enabled" : "Disabled");
    }).fail(function () {
        $(elem).prop('checked', !enabled);
    });
}