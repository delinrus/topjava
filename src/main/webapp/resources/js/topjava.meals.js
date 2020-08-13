var mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return  date.replace("T", " ").substring(0, 16);
                        }
                        return date;
                    }

                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });


    $('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onClose:function(dp,$input){
            $('#endDate').datetimepicker('setOptions',{minDate: $input.val()})
        }
    });

    $('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onClose:function(dp,$input){
            $('#startDate').datetimepicker('setOptions',{maxDate: $input.val()})
        }
    });

    $('#startTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
        onClose:function(dp,$input){
            $('#endTime').datetimepicker('setOptions',{minTime: $input.val()})
        }
    });

    $('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
        onClose:function(dp,$input){
            $('#startTime').datetimepicker('setOptions',{maxTime: $input.val()})
        }
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });

});