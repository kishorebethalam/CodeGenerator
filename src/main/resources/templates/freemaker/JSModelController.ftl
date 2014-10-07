function getBaseUrl(){
	return 'http://localhost:${userPreferences.appWebPort}'; 
}
/*Utility method to configure the urls for sync operations. */
function mySyncFunction(method, model, options){
  if(method=="read"){
    options.url = model.url + '/dto/' + model.get('id'); 
  }else if(method=='create'){
  	options.url = model.url + '/add'; 
  }
  else if(method=='update'){
  	options.url = model.url + '/update'; 
  }
  else if(method=='delete'){
  	options.url = model.url + '/' + model.get('id'); 
  }
  return Backbone.sync(method, model, options);
}

var ModelController = function() {
    return this;
};

<#list classDefinitions as classDefinition>
/* JS Object to represent ${classDefinition.className} */
var ${classDefinition.className}Model = Backbone.Model.extend({
	defaults: {
		id: null,
		<#list classDefinition.columns as columnData>
		<#assign dtoFields=classDefinition.dtoDefinition.dtoColumns>
		${columnData.fieldIdentifier}: null<#if columnData_has_next>,<#else><#if dtoFields?size!=0>,</#if></#if>
		</#list>
		<#list dtoFields as columnData>
		${columnData.fieldIdentifier}: null<#if columnData_has_next>,</#if>
		</#list>
	},
	"sync": mySyncFunction,
	initialize: function () {
	},
	url: getBaseUrl() + '/${classDefinition.className?uncap_first}'
});

/* JS Object to represent ${classDefinition.className}Collection */

var ${classDefinition.className}Collection = Backbone.Collection.extend({
    model: ${classDefinition.className}Model,
    url: getBaseUrl() + '/${classDefinition.className?uncap_first}/dto/all'
});
</#list>
