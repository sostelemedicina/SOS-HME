/*
*Aplicando scrips por templates 
*
*By Armando Prieto
*armando.prieto@ciens.ucv.ve
*/

 $(document).ready(function(){
 
 
 
$("#at0069").children('.label').after("<p id='completa' style='float: right; cursor:pointer;'>Inmunizaci&oacute;n completa</p>");

 
 $('body').on('click','#completa',function(){
 
$("#at0069").find('.DV_BOOLEAN').find('select').each(function(){
	//Cambio todos los valores negando el valor actual

		if($(this).val() == "true"){
				$(this).val("false");
				}else{
				$(this).val("true");
		}		

});
 
 
 });
 
 
 
 });


