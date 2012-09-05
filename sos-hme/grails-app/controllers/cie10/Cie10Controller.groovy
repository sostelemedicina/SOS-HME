package cie10

import tablasMaestras.Cie10Trauma

class Cie10Controller {

    def index = {


        def offset 
        def max 

        if(params.offset){
            offset = Integer.parseInt(params.offset)
            
        }else{
            offset = 0
        }

        if(params.max){
            max = Integer.parseInt(params.max)
            
        }else{
            max = 10
        }
        
        def partes = params.text.split(" ") // saco palabras por espacios

        //println partes

         def v = Cie10Trauma.createCriteria()

        def _codigos = v.list(max: max, offset: offset){

            //like('nombre', '%'+ params.text +'%') // Ok si uso el texto completo
            and {
                partes.each { parte ->
                    like('nombre', '%'+ parte +'%')
                }
            }
           // order 'nombre', 'desc'
        }
        def total = _codigos.totalCount

       

        _codigos.each { _codigo ->

            
              partes.each { parte ->

                // El texto en la base esta en upper
                _codigo.nombre = _codigo.nombre.toUpperCase()
                _codigo.nombre = _codigo.nombre.replaceAll(parte.toUpperCase(), '<b class="highlight">'+parte.toUpperCase()+'</b>')

            }
              //Hago dicard() para que no se realice un guardado automatico!!!
              _codigo.discard()
        }
        
        


        //Armando: Se codifica el return en JSON para ser atajado con JQUERY

//        render(contentType: "text/json") {
//         codigos = array {
//            _codigos.each { _codigo ->
//
//              // a negrita los textos de entrada en el texto de salida
//              def _nombre = _codigo.nombre
//              partes.each { parte ->
//
//                // El texto en la base esta en upper
//                _nombre = _nombre.replaceAll(parte.toUpperCase(), '<b class="highlight">'+parte.toUpperCase()+'</b>')
//              }
//              //println "nombre: "+_nombre
//
//             codigo (
//                id: _codigo.id,
//                grupo: _codigo.grupo,
//                subgrupo: _codigo.subgrupo,
//                codigo: _codigo.codigo,
//
//
//                //nombre: _codigo.nombre
//                nombre: _nombre
//              )
//
//            }
//          }
//        }




    render(template: "/hce/cie10", model :[codigos: _codigos, text: params.text,total: total])
    
    }
}
