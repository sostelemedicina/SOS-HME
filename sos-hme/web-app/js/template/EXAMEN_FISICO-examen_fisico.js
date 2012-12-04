/*
*Aplicando scrips por templates 
*
*By Armando Prieto
*armando.prieto@ciens.ucv.ve
*/

//Funccion para reemplezar caracteres escapables 
 function jq(myid) { 
   return '#' + myid.replace(/(:|\.)/g,'\\$1');
 }
 
 function valorGlasgow(scalaGlasgow){
 
 var valor ="";
 
	if(scalaGlasgow >= 15){
		
		valor = "Normal";
	
	}else if(scalaGlasgow>= 14 && scalaGlasgow <=15){
	
		valor = "Leve";
		
	}else if(scalaGlasgow>= 9 && scalaGlasgow <=13){
		
		valor = "Moderado";
	
	}else if(scalaGlasgow <=8){
	
		valor = "Severo";
		
	}
	
	return valor;
 
 }
 
 function cambiarGlasgow(scalaGlasgow){
 
 //Insertar el valor Glasgow
 
 //alert("El valor Glasgow es: "+ scalaGlasgow);
 
 if($("#valorGlasgow").length > 0){
        if(scalaGlasgow>0){
            $("#valorGlasgow").html('<p>Valor de la escala de Glasgow: '+scalaGlasgow+' , nivel: '+valorGlasgow(scalaGlasgow)+'</p>');
        }else{
            $("#valorGlasgow").remove();
        }
	
 }else{

	$("#at0007").append('<div id="valorGlasgow"><p>Valor de la escala de Glasgow: '+scalaGlasgow+' , nivel: '+valorGlasgow(scalaGlasgow)+'</p>');
 }
 
 }
 
 $(document).ready(function(){
  
var scalaGlasgow = 0;
var scalaGlasgowOcular = 0;
var scalaGlasgowVerbal = 0;
var scalaGlasgowMotora = 0;

			 $("#at0008").find("select").change(function(){
			 
					 if($(this).val() == "at0009"){
					 
						scalaGlasgowOcular = 1;
					 
					 }else if($(this).val() == "at0010"){
						
						scalaGlasgowOcular = 2;
					 
					 }else if($(this).val() == "at0011"){
					 
						scalaGlasgowOcular = 3;
					 
					 }else if($(this).val() == "at0012"){
					 
						scalaGlasgowOcular = 4;
					 
					 }else{
                                             
                                                scalaGlasgowOcular = 0;
                                         }
			 scalaGlasgow = scalaGlasgowOcular+scalaGlasgowVerbal+scalaGlasgowMotora;
			 cambiarGlasgow(scalaGlasgow);
			 });
			  $("#at0013").find("select").change(function(){
			 
					 if($(this).val() == "at0015"){
					 
						scalaGlasgowVerbal = 1;				 
										 
					 }else if($(this).val() == "at0016"){
					 
						scalaGlasgowVerbal = 2;
					 
					 }else if($(this).val() == "at0017"){
					 
						scalaGlasgowVerbal = 3;
					 
					 }else if($(this).val() == "at0018"){
					 
						scalaGlasgowVerbal = 4;
					 
					 }else if($(this).val() == "at0083"){
					 
						scalaGlasgowVerbal = 5;
					 
					 }else{
                                             
                                                scalaGlasgowVerbal = 0;
                                             
                                         }
			  scalaGlasgow = scalaGlasgowOcular+scalaGlasgowVerbal+scalaGlasgowMotora;
			  cambiarGlasgow(scalaGlasgow);
			 });
			 
			 $("#at0014").find("select").change(function(){
			 
					 if($(this).val() == "at0019"){
					 
						scalaGlasgowMotora = 1;
					 
					 }else if($(this).val() == "at0020"){
					 
						scalaGlasgowMotora = 2;
					 
					 }else if($(this).val() == "at0021"){
					 
						scalaGlasgowMotora = 3;
					 
					 }else if($(this).val() == "at0022"){
					 
						scalaGlasgowMotora = 4;
					 
					 }else if($(this).val() == "at0084"){
					 
						scalaGlasgowMotora = 5;
					 
					 }else if($(this).val() == "at0085"){
					 
						scalaGlasgowMotora = 6;
					 
					 }else{
                                             
                                                scalaGlasgowMotora = 0;
                                         }
			 
			  scalaGlasgow = scalaGlasgowOcular+scalaGlasgowVerbal+scalaGlasgowMotora;
			  cambiarGlasgow(scalaGlasgow);
			 });
			 
 
 
 
 });