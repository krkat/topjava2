const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "desc"
                ]
            ]
        })
    );
});

function filter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: $('#filterForm').serialize(),
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        }
    });
}

function clearFilter() {
    $('#filterForm').find(":input").val("");
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl,
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        }
    });
}