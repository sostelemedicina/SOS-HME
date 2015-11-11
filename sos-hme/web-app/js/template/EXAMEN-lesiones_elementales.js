/**
 * Created with IntelliJ IDEA.
 * User: Rafael Benitez
 * Date: 08/06/13
 * Time: 04:27 PM
 * To change this template use File | Settings | File Templates.
 */
//--------------------Variables Globales----------------------------------//

var arreglo = [0,0,0];
var mode = 0;
var muestra;

//--------------------Variables Globales----------------------------------//


//------------------------Funciones---------------------------------------//
//------------------------------------------------------------------------//

//--- Funcion para cambiar la direccion de ubicacion de las imagenes------//
function ChangeDir(url) {
    var aux="";
    if (url!="") {
        aux = url.replace("C:/sos/SOS-HMD/sos-hme/web-app", "/sos");
    }else {
        aux = 'undefined';
    }
    return aux;
}
//------------------------------------------------------------------------//

//------- Funcion para identificar el url de la pagina que se muestra-----//
function UbicarURL() {
    //alert( document.URL);
    var url = document.URL;

    arreglo[0] = url.search("generarShow");
    arreglo[1] = url.search("generarTemplate");
    arreglo[2] = url.search("showRecord");
    if (arreglo[0]!=-1) {
        mode = url.search("mode=edit");
    }
}
//------------------------------------------------------------------------//

//---------Funcion para mostrar una imagen cargada desde un input---------//
function readURL(inputs) {
    var clase = $(inputs).attr("name");
    var element = $("input."+clase).parent().parent().find("img."+clase);
    if (inputs.files && inputs.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $(element).attr('src', e.target.result);
        };
        reader.readAsDataURL(inputs.files[0]);
    }
}
//------------------------------------------------------------------------//

//-------------Funcion para verificar valores vacios ---------------------//
function ValidarVacio (arreglo) {
    if ((arreglo[0].attr("src")=="")||(arreglo[0].attr("src")==undefined))
    {
        //alert("Campo imagen vacio");
        return true;
    }
    if (arreglo[1].val()=="")
    {
        //alert("Campo fecha vacio");
        return true;
    }
    if ((arreglo[2].val()==""))
    {
        //alert("Campo descripcion vacio");
        return true;
    }
    return false;
}
//------------------------------------------------------------------------//

//-------------Funcion para trabajar cuando se clona un elemento----------//
function ActionButton() {
    $('a.clone').click( function(){
        var node = $(this).parent().prev().parent().find('#at0135').last().children().last().children().first();
        var count = $(this).parent().prev().parent().find("#at0135").length;
        count = count-1;
        var nombre = "image-file-"+count;
        var classe = "image-file "+nombre;
        var span = $(node).children("span").eq(1);
        var div = $(span).find("div.image-container");
        var input = $(div).children("input");

        span.attr("class","content image-"+count);
        div.attr("class","image-container image-number-"+count);
        input.attr({
            "class":classe,
            "name":nombre,
            "value":""
        });
        node.find("img").attr({
            "src":"",
            "class":classe
        });
        $(".image-file-"+count).change(function(){
            readURL(this);
        });
        //alert("Cantidad de elementos at0136: "+count);
    });

    $("div.bottom_actions").children("input").click(function() {
        var nodo = $("#at0134").find("#at0135");
        var elemento=0;
        //console.log("Cantidad de elementos a guardar: " + $(nodo).length);
        $(nodo).each(function (i) {
            //console.log("iteracion " + i);
            var item = this;
            var child = $(item).children(".content");
            var imageText = $(child).find("#at0136").children(".content").find("img.image-file");
            var imageDate = $(child).find("#at0137").children(".content").find("input");
            if (mode==0)
            {
                var imageDesc = $(child).find("#at0138").children(".content").children().children().children();
                /*console.log("El src de la imagen: " + $(imageText).attr("src"));
                console.log("La fecha de la imagen: " + $(imageDate).val());
                console.log("La descripcion de la imagen a editar es: " + $(imageDesc).val());*/

            }else {
                var imageDesc = $(child).find("#at0138").children(".content").children();
                /*console.log("El src de la imagen: " + $(imageText).attr("src"));
                console.log("La fecha de la imagen: " + $(imageDate).val());
                console.log("La descripcion de la imagen: " + $(imageDesc).val());*/
            }

            muestra = ValidarVacio([$(imageText), $(imageDate), $(imageDesc)]);
            if (muestra) {
                alert("No puede haber un campo vacio para la imagen " + (i + 1));
                elemento=0;
                return false;
            } else {
                elemento=i+1;
            }
        });

        $('#contenido form').prepend('<input type="hidden" name="CampoVacio" value="'+elemento+'"/>');
    });
}
//------------------------------------------------------------------------//

//-------------Funcion mostrar cantidad de imagenes en el label----------//
/* esta funcion se llama diferente al EXAMEN-lesiones_especiales.js
 * por problemas de ambiguedad
 */
function CantidadImage (elem) {
    if (elem.text()!="") {
        var count = elem.length;
        var aux = $('#at0134').children("span.label").children();
        $('#at0134').children("span.label").text("Imágenes: "+count);
        $('#at0134').children("span.label").append(aux);
    }
}
//------------------------------------------------------------------------//

//----------------------------Fin Funciones-------------------------------//

$(document).ready(function() {
    UbicarURL();
    ActionButton();

    // Handler for .ready() called.
    //alert($('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]'));
    $('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]').css({"width":'250px',"height":'22px'});
    //$('textarea[name="openEHR-EHR-EVALUATION.lesiones.v1/data[at0001]/items[at0002]/items[at0131]/items[at0152]/items[at0151]/value"]').attr("disabled","disabled");
    // Para agregar la clase "image-cluster" al elemento textarea
    $('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]').parent().addClass('image-cluster');

    // Para Ocultar el textarea del campo foto
    $('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]').hide();

    // Para obtener la cantidad de imagenes que tiene el campo foto
    var image_count =$('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]').length;

    //-----------------------------------------------------------------------------//
    //        Accion para recorrer cada imagen cargada en el arquetipo
    //-----------------------------------------------------------------------------//
    //Para obtener todos los objetos imagen que contiene el campo foto
    var images=$('textarea[name="openEHR-EHR-EVALUATION.lesiones_elementales.v1/data[at0001]/items[at0002]/items[at0134]/items[at0135]/items[at0136]/value"]');
    //var element = $("#at0136").find("span").children("span.content image-cluster");
    CantidadImage(images);
    var i=0;
    $(images).each(function()
    {
        // ME estoy moviendo entre las imagenes ya guardadas
        var item = this;
        var nombreClase = "image-file image-file-"+i;
        var dir = ChangeDir($(item).val());
        var parent = "";

        // Si se esta editando el arquetipo en la vista GenerarTemplate y GenerarShow?mode=edit
        if (arreglo[1]>0 || mode>0)
        {
            //parent = $(item).parent().parent().parent();
            parent = $(item).parent();
            //$(parent).addClass('image-'+i);
            // Se agrega un input
            $(parent).prepend('<div class="image-container image-number-'+i+'"><input name="image-file-'+i+'" type="file" class="image-file image-file-'+i+'"/></div>');
            // Se agrega un campo imagen
            $(parent).append('<img/>');

            if (dir!='' && dir !='undefined'){
                $(parent).children("img").attr({
                    "class":nombreClase,
                    "alt":"Imagen Dermatologica",
                    "src":dir
                });
            }else{

                $(parent).children("img").attr({
                    "class":nombreClase,
                    "alt":"Imagen Dermatologica"
                });
            }

        }else {
            // Aqui se muestra la informacion del arquetipo en la vista ShowRecord y GenerarShow
            // Aqui se agrega el elemento imagen para mostrarla en la plantilla
            parent = $(item).parent();
            if(dir!='undefined'){
                $(parent).prepend('<img />');
                $(parent).children("img").attr({
                    "class":nombreClase,
                    "alt":"Imagen Dermatologica",
                    "src":dir
                });

            } else {
                alert ("A ver cuando pasa esto...");
                $(parent).prepend('<img class="image-file-'+i+'" src="'+dir+'" alt="Imagen Dermatologica/>');
                //$(this).hide();
            }
        }

        i++;

    });
    //-----------------------------------------------------------------------------//
    //       FIN Accion para recorrer cada imagen cargada en el arquetipo
    //-----------------------------------------------------------------------------//

    //-----------------------------------------------------------------------------//
    //        Accion para cargar la imagen desde el input
    //-----------------------------------------------------------------------------//
    $(".image-file").change(function(){
        //alert("ENTRO EN EL CHANGE PARA LA CLASE: "+$(this).attr("class"));
        readURL(this);
    });
    //-----------------------------------------------------------------------------//
    //        FIN Accion para cargar la imagen desde el input
    //-----------------------------------------------------------------------------//


    //alert ("otro valor a ver: "+$(item).val());


});