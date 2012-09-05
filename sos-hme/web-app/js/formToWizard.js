/* Created by jankoatwarpspeed.com */
var $j = jQuery.noConflict();
(function($j) {
    $j.fn.formToWizard = function(options) {
        options = $j.extend({  
            submitButton: "" 
        }, options); 
        
        var element = this;

        var steps = $j(element).find("fieldset");
        var count = steps.size();
        var submmitButtonName = "#" + options.submitButton;
        $j(submmitButtonName).hide();

        // 2
        $j(element).before("<ul id='steps'></ul>");

        steps.each(function(i) {
            $j(this).wrap("<div id='step" + i + "'></div>");
            $j(this).append("<p id='step" + i + "commands'></p>");

            // 2
            var name = $j(this).find("legend").html();
            $j("#steps").append("<li id='stepDesc" + i + "'>Paso " + (i + 1) + "<span>" + name + "</span></li>");

            if (i == 0) {
                createNextButton(i);
                selectStep(i);
            }
            else if (i == count - 1) {
                $j("#step" + i).hide();
                createPrevButton(i);
            }
            else {
                $j("#step" + i).hide();
                createPrevButton(i);
                createNextButton(i);
            }
        });

        function createPrevButton(i) {
            var stepName = "step" + i;
            $j("#" + stepName + "commands").append("<a href='#' id='" + stepName + "Prev' class='prev'>< Anterior</a>");

            $j("#" + stepName + "Prev").bind("click", function(e) {
                $j("#" + stepName).hide();
                $j("#step" + (i - 1)).show();
                $j(submmitButtonName).hide();
                selectStep(i - 1);
            });
        }

        function createNextButton(i) {
            var stepName = "step" + i;
            $j("#" + stepName + "commands").append("<a href='#' id='" + stepName + "Next' class='next'>Siguiente ></a>");

            $j("#" + stepName + "Next").bind("click", function(e) {
                
                if(checkValid(stepName)){
                    $j("#" + stepName).hide();
                    $j("#step" + (i + 1)).show();
                    if (i + 2 == count)
                        $j(submmitButtonName).show();
                    selectStep(i + 1);
                }
                
            });
        }

        function selectStep(i) {
            $j("#steps li").removeClass("current");
            $j("#stepDesc" + i).addClass("current");
        }
        
        function checkValid(paso){
        //alert(jQuery("#primerApellido").val())
        //if(jQuery("#primerApellido"))
        var idsStep0 = new Array("#primerApellido","#segundoApellido","#primerNombre","#segundoNombre","#extension","#fechaNacimiento","#sexo","#identificadorUnico");
        var idsStep1 = new Array("#ciudadnacimiento");
        var idsStep2 = new Array("#anosaprobados"); 
        var idsStep3 = new Array("#entidresid","#ciudad","#urbasector","#avenidacalle","#casaedif","#ptoreferenica"); 
        var idsStep4 = new Array("#telfhabitacion","#telfcelular","#email","nombremadre","#nombrepadre","#otradireccion","#contactoemergencia");
        
        
        if(paso=="step0"){
            var size0 = idsStep0.length;
            var i0 = 0;
            for(i0=0; i0<=size0-1;i0++){
                if(jQuery("#nuevopaciente").validate().element(idsStep0[i0])==false){
                return false;
                }
            }
        }
        if(paso=="step1"){
            var size1 = idsStep1.length;
            var i1 = 0;
            for(i1=0; i1<=size1-1;i1++){
                if(jQuery("#nuevopaciente").validate().element(idsStep1[i1])==false){
                return false;
                }
            }
        }
        if(paso=="step2"){
            var size2 = idsStep2.length;
            var i2 = 0;
            for(i2=0; i2<=size2-1;i2++){
                if(jQuery("#nuevopaciente").validate().element(idsStep2[i2])==false){
                return false;
                }
            }
        }
        if(paso=="step3"){
            var size3 = idsStep3.length;
            var i3 = 0;
            for(i3=0; i3<=size3-1;i3++){
                if(jQuery("#nuevopaciente").validate().element(idsStep3[i3])==false){
                return false;
                }
            }
        }
        if(paso=="step4"){
            var size4 = idsStep4.length;
            var i4 = 0;
            for(i4=0; i4<=size4-1;i4++){
                if(jQuery("#nuevopaciente").validate().element(idsStep4[i4])==false){
                return false;
                }
            }
        }
        
        
        return true;
      }

    }
})(jQuery); 


