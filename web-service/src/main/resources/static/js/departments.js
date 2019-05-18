$(document).ready(function () {
    $('#departments').DataTable({
        dom: "<'row'<'col-sm-6'l><'col-sm-6'p>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        "ajax": "rest/api/v1/departments",
        "orderCellsTop": true,
        "pageLength": 10,
        "lengthMenu": [[10, 25, 50, 100], [10, 25, 50, 100]],
        "bProcessing": false,
        "serverSide": true,
        "select": true,
        "columns": [
            {"data": "id"},
            {"data": "departmentName"},
            {"data": "managerId"},
            {"data": "locationId"}
        ]
    }).columns().every(function () {
        var that = this;
        var id = $(this.header()).attr("departmentName") + "-search"
        $("#" + id).on('keyup change', function () {
            if (that.search() !== this.value) {
                that.search(this.value).draw();
            }
        });
    });
});

$(document).ready(function() {
    var table = $('#departments').DataTable();

    $('#departments tbody').on('click', 'tr', function () {
        var data = table.row( this ).data();
        window.location.replace('department?id=' + data['id']);
        console.log('You clicked on '+ data['id'] +'\'s row' );
    } );
} );
