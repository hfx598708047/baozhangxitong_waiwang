function houseSearch(OPTYPENUM,RECYEAR,RECNUM) {
    var idCard = $("#idCard").val();
    var data = {
        OPTYPENUM:OPTYPENUM,
        RECYEAR:RECYEAR,
        RECNUM:RECNUM,
        idCard:idCard
    };
    $.ajax({
        url:'/support/house_search',
        type:'POST',
        //contentType: 'application/json; charset=UTF-8',
        async:false,
        //dataType:'json',
        data:data,
        success: function (response) {

        }
    })
}