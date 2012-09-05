var $j = jQuery.noConflict();
$j(document).ready(function(){
    jQuery.validator.addMethod(
	"lettersonly",
	function(value, element) {
	return this.optional(element) || /^[A-Za-z \u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+$/i.test(value);
	}, 
	"Solo caracteres Alfabeticos");
    jQuery.validator.addMethod( 
	  "selectNone", 
	  function(value, element) { 
	    if (element.value == "") 
	    { 
	      return false; 
	    } 
	    else return true; 
	  }, 
	  "Seleccione Opci&oacute;n" 
	);
    jQuery.validator.addMethod( 
	  "identificadorValido", 
	  function(value, element) { 
	    if (element.value == "novalido") 
	    { 
	      return false; 
	    } 
	    else return true; 
	  }, 
	  "identificador no valido" 
	);
            
    $j("#nuevopaciente").validate({
        rules:{
            "primerApellido":{
                required:true,
                minlength:2,
                lettersonly:true
            },
            "segundoApellido":{
                //required:true,
                minlength:2,
                lettersonly:true
            },
            "primerNombre":{
                required:true,
                minlength:2,
                lettersonly:true
            },
            "segundoNombre":{
                //required:true,
                minlength:2,
                lettersonly:true
            },
            "extension":{
                //required: true,
		minlength: 6,
		maxlength: 8,
		number: true
            },
            "identificadorUnico":{
               identificadorValido: true 
            },
            "fechaNacimiento":{
                required: true
            },
            "sexo":{
                selectNone:true
            },
            "foto":{
                
            },
            "lugar.id":{
                //selectNone:true
            },    
            "ciudadnacimiento":{
                //required:true,
                minlength: 4,
                lettersonly:true
            },
            "anosaprobados":{
                number: true
            },
            "ocupacion.id":{
                //selectNone:true
            },
            "entidresid":{
                selectNone:true
            },
            "municresid":{
                selectNone:true
            },
            "direccion.id":{
                selectNone:true
            },
            "ciudad":{
               required:true,
               minlength:4,
               lettersonly:true 
            },
            "urbasector":{
               required:true,
               minlength:4
            },
            "avenidacalle":{
               required:true,
               minlength:4
            },
            "casaedif":{
               required:true,
               minlength:1
            },
            "ptoreferenica":{
               //required:true,
               minlength:10
            },
            "telfhabitacion":{
               //required: true,
               minlength: 7,
	       maxlength: 11,
	       number: true
            },
            "telfcelular":{
               minlength: 7,
	       maxlength: 11,
	       number: true
            },
            "nombremadre":{
               //required:true,
               minlength:10,
               lettersonly:true 
            },
            "nombrepadre":{
               //required:true,
               minlength:10,
               lettersonly:true 
            },
            "otradireccion":{
               minlength:10
            },
            "contactoemergencia":{
               minlength:10
            },
            "email":{
               email:true
            }
        },
        messages:{
            "primerApellido":{
                required:"Obligatorio",
                minlength:"M&iacute;nimo 2 caracteres",
                lettersonly:"Solo letras"
            },
            "segundoApellido":{
                required:"Obligatorio",
                minlength:"M&iacute;nimo 2 caracteres",
                lettersonly:"Solo letras"
            },
            "primerNombre":{
                required:"Obligatorio",
                minlength:"M&iacute;nimo 2 caracteres",
                lettersonly:"Solo letras"
            },
            "segundoNombre":{
                required:"Obligatorio",
                minlength:"M&iacute;nimo 2 caracteres",
                lettersonly:"Solo letras"
            },
            "extension":{
                //required: "Obligatorio", 
		minlength: "M&iacute;nimo 6 d&iacute;gitos",
		maxlength: "M&aacute;ximo 8 d&iacute;gitos",
                number: " Num&eacute;rico"
            },
            "sexo":{
                selectNone:"Obligatorio"
            },
            "foto":{
                accept:"Formato de Imagen Inv&aacute;lido"
            },
            "fechaNacimiento":{
                required: "Obligatorio"
            },
            "lugar.id":{
                selectNone:"Obligatorio"
            },
            "ciudadnacimiento":{
                required:"Obligatorio",
                minlength: "M&iacute;nimo 4 caracteres",
                lettersonly:"Solo letras"
            },
            "anosaprobados":{
                number: " Num&eacute;rico"
            },
            "ocupacion.id":{
                selectNone:"Obligatorio"
            },
            "entidresid":{
                selectNone:"Obligatorio"
            },
            "municresid":{
                //selectNone:"Obligatorio"
            },
            "direccion.id":{
                selectNone:"Obligatorio"
            },
            "ciudad":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 4 caracteres",
               lettersonly:"Solo letras" 
            },
            "urbasector":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 4 caracteres"
            },
            "avenidacalle":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 4 caracteres"
            },
            "casaedif":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 4 caracteres"
            },
            "ptoreferenica":{
               required:"Obligatorio",
               minlength:"M&iacute;nimo 10 caracteres"
            },
            "telfhabitacion":{
               required: "Obligatorio", 
	       minlength: "M&iacute;nimo 7 d&iacute;gitos",
	       maxlength: "M&aacute;ximo 11 d&iacute;gitos",
               number: " Num&eacute;rico"
            },
            "telfcelular":{
	       minlength: "M&iacute;nimo 7 d&iacute;gitos",
	       maxlength: "M&aacute;ximo 11 d&iacute;gitos",
               number: " Num&eacute;rico"
            },
            "nombremadre":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 10 caracteres",
               lettersonly:"Solo letras" 
            },
            "nombrepadre":{
               required:"Obligatorio",
               minlength: "M&iacute;nimo 10 caracteres",
               lettersonly:"Solo letras" 
            },
            "otradireccion":{
               minlength: "M&iacute;nimo 10 caracteres"
            },
            "contactoemergencia":{
               minlength: "M&iacute;nimo 10 caracteres"
            },
            "email":{
               email:"Formato de email inv&aacute;lido"
            }
        }
    });
    
    
})