/**
 * Created with IntelliJ IDEA.
 * User: Enrique Talavera
 * Date: 02/07/15
 * Time: 08:11 PM
 * To change this template use File | Settings | File Templates.
 */
//---------------- Funcion para eleminar uma opcion seleccionada ----------------//

//---------------------------------------------------------------------------//

//---------------- Funcion para validar un dedo seleccionado ----------------//
function ActionButton() {
    var id1 = "openEHR-EHR-EVALUATION.historia_dermatologica.v1/data[at0001]/items[at0002]/items[at0030]/items[at0162]/items[at0233]/items[at0332]/value";
    var id2 = "openEHR-EHR-EVALUATION.historia_dermatologica.v1/data[at0001]/items[at0002]/items[at0030]/items[at0031]/items[at0234]/items[at0122]/value";
    var id3 = "openEHR-EHR-EVALUATION.historia_dermatologica.v1/data[at0001]/items[at0002]/items[at0030]/items[at0109]/items[at0236]/items[at0146]/value";
    var id4 = "openEHR-EHR-EVALUATION.historia_dermatologica.v1/data[at0001]/items[at0002]/items[at0030]/items[at0163]/items[at0235]/items[at0208]/value";



    var opcionA = $('select[name="'+id1+'"]');
    var opcionB = $('select[name="'+id2+'"]');
    var opcionC = $('select[name="'+id3+'"]');
    var opcionD = $('select[name="'+id4+'"]');
    /*
    *   Acción para mano Derecha
    */
    $(opcionA).change( function () {
        var elem = this;
        //console.warn("Le dio click a la opcion: "+$(elem).val()+" de la lista");
        //console.log("esta opción esta en la posición: "+elem.selectedIndex);
        var elementos = $('select[name="'+id1+'"]');
        var marca=elem.selectedIndex;
        $(elementos).each(function(i) {
            var item = this;
            var x = item.selectedIndex;
            if (x!=marca) {
                $(item).children().eq(marca).remove();
            }
        });
    });

    /*
     *   Acción para mano Izquierda
     */
    $(opcionB).change( function () {
        var elem = this;
        //console.warn("Le dio click a la opcion: "+$(elem).val()+" de la lista");
        //console.log("esta opción esta en la posición: "+elem.selectedIndex);
        var elementos = $('select[name="'+id2+'"]');
        var marca=elem.selectedIndex;
        $(elementos).each(function(i) {
            var item = this;
            var x = item.selectedIndex;
            if (x!=marca) {
                $(item).children().eq(marca).remove();
            }
        });
    } );

    /*
     *   Acción para pie Derecho
     */
    $(opcionC).change( function () {
        var elem = this;
        //console.warn("Le dio click a la opcion: "+$(elem).val()+" de la lista");
        //console.log("esta opción esta en la posición: "+elem.selectedIndex);
        var elementos = $('select[name="'+id3+'"]');
        var marca=elem.selectedIndex;
        $(elementos).each(function(i) {
            var item = this;
            var x = item.selectedIndex;
            if (x!=marca) {
                $(item).children().eq(marca).remove();
            }
        });
    } );

    /*
     *   Acción para pie Izquierdo
     */
    $(opcionD).change( function () {
        var elem = this;
        //console.warn("Le dio click a la opcion: "+$(elem).val()+" de la lista");
        //console.log("esta opción esta en la posición: "+elem.selectedIndex);
        var elementos = $('select[name="'+id4+'"]');
        var marca=elem.selectedIndex;
        $(elementos).each(function(i) {
            var item = this;
            var x = item.selectedIndex;
            if (x!=marca) {
                $(item).children().eq(marca).remove();
            }
        });
    } );
}
//---------------------------------------------------------------------------//

$(document).ready(function() {
    ActionButton();
});