$(document).ready(function () {
    $('#employees').DataTable({
        dom: "<'row'<'col-sm-6'l><'col-sm-6'p>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        "ajax": "rest/api/v1/employees",
        "orderCellsTop": true,
        "pageLength": 10,
        "lengthMenu": [[10, 25, 50, 100], [10, 25, 50, 100]],
        "bProcessing": false,
        "serverSide": true,
        "select": true,
        "columns": [
            {"data": "id"},
            {"data": "firstName"},
            {"data": "lastName"},
            {"data": "email"},
            {"data": "phoneNumber"},
            {"data": "hireDate"},
            {"data": "jobId"},
            {"data": "salary"},
            {"data": "commissionPct"},
            {"data": "managerId"},
            {"data": "departmentId"}
        ]
    }).columns().every(function () {
        var that = this;
        var id = $(this.header()).attr("firstName") + "-search"
        $("#" + id).on('keyup change', function () {
            if (that.search() !== this.value) {
                that.search(this.value).draw();
            }
        });
    });
});

$(document).ready(function() {
    var table = $('#employees').DataTable();

    $('#employees tbody').on('click', 'tr', function () {
        var data = table.row( this ).data();
        window.location.replace('employee?id=' + data['id']);
        console.log('You clicked on '+ data['id'] +'\'s row' );
    } );
} );
