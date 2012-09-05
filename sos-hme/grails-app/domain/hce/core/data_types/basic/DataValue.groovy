package hce.core.data_types.basic

//import hce.core.support.definition.BasicDefinitions
//import hce.core.datastructure.itemstructure.representation.Element

class DataValue { //extends BasicDefinitions { // Abstract

    // Serves as a common ancestor of all data value types in openEHR models

   static mapping = {
      table 'data_value'
   }
   
   static transients = ["className"]
   String getClassName()
   {
       return this.getClass().getSimpleName()
   }
}
