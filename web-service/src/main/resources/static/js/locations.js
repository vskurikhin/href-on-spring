$(document).ready(function () {
    $('#example').DataTable({
        "ajax": "rest/api/v1/locations",
        "dom": 'T clear Brtip',
        "orderCellsTop": true,
        "pageLength": 10,
        "lengthMenu": [[5, 10, 25, 50, 100], [5, 10, 25, 50, 100]],
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
        alert( 'You clicked on '+ data['id'] +'\'s row' );
        console.log(data)
    } );
} );
