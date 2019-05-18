$(document).ready(function () {
    $('#example').DataTable({
        dom: "<'row'<'col-sm-6'l><'col-sm-6'p>>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        "ajax": "rest/api/v1/locations",
        "orderCellsTop": true,
        "pageLength": 10,
        "lengthMenu": [[10, 25, 50, 100], [10, 25, 50, 100]],
        "bProcessing": false,
        "serverSide": true,
        "select": true,
        "columns": [
            {"data": "id"},
            {"data": "streetAddress"},
            {"data": "postalCode"},
            {"data": "city"},
            {"data": "stateProvince"},
            {"data": "countryId"}
        ]
    }).columns().every(function () {
        var that = this;
        var id = $(this.header()).attr("streetAddress") + "-search"
        $("#" + id).on('keyup change', function () {
            if (that.search() !== this.value) {
                that.search(this.value).draw();
            }
        });
    });
});

$(document).ready(function() {
    var table = $('#example').DataTable();

    $('#example tbody').on('click', 'tr', function () {
        var data = table.row( this ).data();
        window.location.replace('location?id=' + data['id']);
        console.log('You clicked on '+ data['id'] +'\'s row' );
    } );
} );
